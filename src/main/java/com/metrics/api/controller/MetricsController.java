package com.metrics.api.controller;

import com.metrics.api.domain.Metric;
import com.metrics.api.domain.MetricValue;
import com.metrics.api.dto.MetadataCalculationDTO;
import com.metrics.api.dto.MetricCalculationParamDTO;
import com.metrics.api.repository.MetricRepository;
import com.metrics.api.service.MetricService;
import com.metrics.api.specification.MetricSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "https://metrics-fe.uc.r.appspot.com/")
@RestController
@RequestMapping("/metrics")
class MetricsController {

    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private MetricService metricService;

    @GetMapping("/")
    public Object list(@RequestParam(required = false) String name,
                       @PageableDefault(value = 1000) Pageable page) {

        Specification<Metric> spec = Specification.where(new MetricSpecification(name));
        return metricRepository.findAll(spec, page);
    }

    @PostMapping("/")
    public Object saveMetric(@Valid @RequestBody Metric metric) {
        return metricService.saveMetric(metric);
    }

    @PostMapping("/value")
    public Object saveMetricValue(@Valid @RequestBody MetricValue metricValue) {
        return metricService.saveMetricValue(metricValue);
    }

    @PostMapping("/calc")
    public MetadataCalculationDTO calculate(@Valid @RequestBody MetricCalculationParamDTO calculationParamDTO) {
        return metricService.calculateOperation(calculationParamDTO);
    }

}