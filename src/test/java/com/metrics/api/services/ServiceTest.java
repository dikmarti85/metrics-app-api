package com.metrics.api.services;

import com.metrics.api.domain.Metric;
import com.metrics.api.domain.MetricValue;
import com.metrics.api.dto.MetadataCalculationDTO;
import com.metrics.api.dto.MetricCalculationParamDTO;
import com.metrics.api.exceptions.BadRequestException;
import com.metrics.api.repository.MetricRepository;
import com.metrics.api.repository.MetricValueRepository;
import com.metrics.api.service.MetricService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {

    @InjectMocks
    MetricService metricService;

    @Mock
    MetricRepository metricRepository;

    @Mock
    MetricValueRepository metricValueRepository;

    @Test
    public void givenMetricSaveMetric() {

        Metric metric = new Metric();
        metric.setId(1L);
        metric.setName("metric_test");
        Mockito.lenient().when(metricRepository.save(Mockito.any())).thenReturn(metric);

        Metric metricSaved = metricService.saveMetric(metric);
        assertEquals(metricSaved.getId(), 1L);
    }

    @Test
    public void givenMetricValueSaveMetricValue() {

        MetricValue metricValue = new MetricValue();
        metricValue.setId(1L);
        metricValue.setValue(12L);
        metricValue.setTime(Timestamp.valueOf(LocalDateTime.now()));

        Mockito.lenient().when(metricValueRepository.save(Mockito.any())).thenReturn(metricValue);

        MetricValue metricValueSaved = metricService.saveMetricValue(metricValue);
        assertEquals(metricValueSaved.getValue(), 12L);
    }

    @Test
    public void givenRangeCalcAvgMin() {
        MetricCalculationParamDTO calculationParamDTO = new MetricCalculationParamDTO();
        calculationParamDTO.setDateFrom(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setDateEnd(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setMetricName("metric_test");
        calculationParamDTO.setTimeType("MIN");

        List<ArrayList> metrics = new ArrayList<>();
        ArrayList row = buildMetrics(69L, 23.5, "2022", "05", "01", "05", "13");
        metrics.add(row);
        row = buildMetrics(69L, 23.5, "2022", "06", "06", "08", "11");
        metrics.add(row);

        Mockito.lenient().when(metricValueRepository.calculateMetricByYearMonthDayHourMinute(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(metrics);
        MetadataCalculationDTO metadataCalculationDTO = metricService.calculateOperation(calculationParamDTO);
        assertEquals(metadataCalculationDTO.getMinutes().size(), 2L);
        assertEquals(metadataCalculationDTO.getHours().size(), 0L);
    }

    @Test
    public void givenRangeCalcAvgHour() {
        MetricCalculationParamDTO calculationParamDTO = new MetricCalculationParamDTO();
        calculationParamDTO.setDateFrom(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setMetricName("metric_test");
        calculationParamDTO.setTimeType("HOUR");

        List<ArrayList> metrics = new ArrayList<>();
        ArrayList row = buildMetrics(69L, 23.5, "2022", "05", "01", "05", "");
        metrics.add(row);
        row = buildMetrics(69L, 23.5, "2022", "06", "08", "08", "");
        metrics.add(row);
        row = buildMetrics(69L, 23.5, "2022", "07", "20", "10", "");
        metrics.add(row);

        Mockito.lenient().when(metricValueRepository.calculateMetricByYearMonthDayHour(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(metrics);
        MetadataCalculationDTO metadataCalculationDTO = metricService.calculateOperation(calculationParamDTO);
        assertEquals(metadataCalculationDTO.getHours().size(), 3L);
        assertEquals(metadataCalculationDTO.getMinutes().size(), 0L);
    }

    @Test
    public void givenRangeCalcAvgDay() {
        MetricCalculationParamDTO calculationParamDTO = new MetricCalculationParamDTO();
        calculationParamDTO.setDateFrom(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setMetricName("metric_test");
        calculationParamDTO.setTimeType("DAY");

        List<ArrayList> metrics = new ArrayList<>();
        ArrayList row = buildMetrics(126L, 13.5, "2022", "03", "04", "", "");
        metrics.add(row);

        Mockito.lenient().when(metricValueRepository.calculateMetricByYearMonthDay(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(metrics);
        MetadataCalculationDTO metadataCalculationDTO = metricService.calculateOperation(calculationParamDTO);
        assertEquals(metadataCalculationDTO.getHours().size(), 0L);
        assertEquals(metadataCalculationDTO.getDays().size(), 1L);
    }

    @Test
    public void givenRangeCalcAvgMonth() {
        MetricCalculationParamDTO calculationParamDTO = new MetricCalculationParamDTO();
        calculationParamDTO.setDateFrom(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setMetricName("metric_test");
        calculationParamDTO.setTimeType("MONTH");

        List<ArrayList> metrics = new ArrayList<>();
        ArrayList row = buildMetrics(126L, 13.5, "2022", "03", "", "", "");
        metrics.add(row);
        row = buildMetrics(426L, 23.5, "2022", "02", "", "", "");
        metrics.add(row);
        row = buildMetrics(356L, 35.5, "2022", "01", "", "", "");
        metrics.add(row);
        row = buildMetrics(686L, 73.5, "2022", "07", "", "", "");
        metrics.add(row);

        Mockito.lenient().when(metricValueRepository.calculateMetricByYearMonth(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(metrics);
        MetadataCalculationDTO metadataCalculationDTO = metricService.calculateOperation(calculationParamDTO);
        assertEquals(metadataCalculationDTO.getDays().size(), 0L);
        assertEquals(metadataCalculationDTO.getMonths().size(), 4L);
    }


    @Test
    public void givenRangeCalcAvgNoTimeBadRequestException() {
        MetricCalculationParamDTO calculationParamDTO = new MetricCalculationParamDTO();
        calculationParamDTO.setDateEnd(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setMetricName("metric_test");
        calculationParamDTO.setDateFrom(Timestamp.valueOf(LocalDateTime.now()));

        assertThrows(BadRequestException.class, () -> metricService.calculateOperation(calculationParamDTO));
    }

    @Test
    public void givenRangeCalcAvgNoDateFromBadRequestException() {
        MetricCalculationParamDTO calculationParamDTO = new MetricCalculationParamDTO();
        calculationParamDTO.setDateEnd(Timestamp.valueOf(LocalDateTime.now()));
        calculationParamDTO.setMetricName("metric_test");
        calculationParamDTO.setTimeType("MIN");

        assertThrows(BadRequestException.class, () -> metricService.calculateOperation(calculationParamDTO));
    }

    public ArrayList buildMetrics(Long sum, Double avg, String year, String month, String day, String hour, String minute){

        ArrayList row = new ArrayList<>();
        row.add(new BigDecimal(sum));
        row.add(new BigDecimal(avg));
        row.add(year);
        row.add(month);
        row.add(day);
        row.add(hour);
        row.add(minute);

        return row;
    }


}
