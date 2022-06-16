package com.metrics.api.service;

import com.metrics.api.domain.Metric;
import com.metrics.api.domain.MetricValue;
import com.metrics.api.dto.MetadataCalculationDTO;
import com.metrics.api.dto.MetricCalculationParamDTO;
import com.metrics.api.exceptions.BadRequestException;
import com.metrics.api.repository.MetricRepository;
import com.metrics.api.repository.MetricValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetricService {

    private static final String MONTH = "MONTH";
    private static final String DAY = "DAY";
    private static final String HOUR = "HOUR";
    private static final String MIN = "MIN";
    private static final String ERROR_PARAM_TIME = "Bad request: param time type from empty";
    private static final String ERROR_PARAM_DATE_FROM = "Bad request: param date from empty";


    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private MetricValueRepository metricValueRepository;

    public Metric saveMetric(Metric metric) {
        return metricRepository.save(metric);
    }

    public MetricValue saveMetricValue(MetricValue metricValue) {
        return metricValueRepository.save(metricValue);
    }

    public MetadataCalculationDTO calculateOperation(MetricCalculationParamDTO calculationParamDTO) {

        MetadataCalculationDTO result = new MetadataCalculationDTO();
        List<ArrayList> metrics = new ArrayList<>();
        List<String> minutes = new ArrayList<>();
        List<String> hours = new ArrayList<>();
        List<String> days = new ArrayList<>();
        List<String> months = new ArrayList<>();
        List<String> years = new ArrayList<>();

        if (calculationParamDTO.getDateFrom() == null) {
            throw new BadRequestException(ERROR_PARAM_DATE_FROM, null, HttpStatus.BAD_REQUEST);
        }

        if (calculationParamDTO.getTimeType() == null) {
            throw new BadRequestException(ERROR_PARAM_TIME, null, HttpStatus.BAD_REQUEST);
        }

        Timestamp dateEndCalc = calculationParamDTO.getDateEnd() == null ? Timestamp.valueOf(LocalDateTime.now()) : calculationParamDTO.getDateEnd();

        switch (calculationParamDTO.getTimeType()) {
            case MONTH:
                metrics = metricValueRepository.calculateMetricByYearMonth(calculationParamDTO.getDateFrom(), dateEndCalc, calculationParamDTO.getMetricName());
                for (List m : metrics) {
                    years.add(String.valueOf(m.get(2)));
                    months.add(String.valueOf(m.get(3)));
                }
                break;
            case DAY:
                metrics = metricValueRepository.calculateMetricByYearMonthDay(calculationParamDTO.getDateFrom(), dateEndCalc, calculationParamDTO.getMetricName());
                for (List m : metrics) {
                    months.add(String.valueOf(m.get(3)));
                    days.add(String.valueOf(m.get(4)));
                }
                break;
            case HOUR:
                metrics = metricValueRepository.calculateMetricByYearMonthDayHour(calculationParamDTO.getDateFrom(), dateEndCalc, calculationParamDTO.getMetricName());
                for (List m : metrics) {
                    days.add(String.valueOf(m.get(4)));
                    hours.add(String.valueOf(m.get(5)));
                }
                break;
            case MIN:
                metrics = metricValueRepository.calculateMetricByYearMonthDayHourMinute(calculationParamDTO.getDateFrom(), dateEndCalc, calculationParamDTO.getMetricName());
                for (List m : metrics) {
                    minutes.add(String.valueOf(m.get(6)));
                }
                break;
        }

        List<BigDecimal> sums = metrics.stream().map(m -> (BigDecimal) m.get(0)).collect(Collectors.toList());
        result.setSums(sums);
        List<BigDecimal> avgs = metrics.stream().map(m -> (BigDecimal) m.get(1)).collect(Collectors.toList());
        result.setAvgs(avgs);
        result.setMinutes(minutes);
        result.setHours(hours);
        result.setDays(days);
        result.setMonths(months);
        result.setYears(years);

        return result;
    }

}