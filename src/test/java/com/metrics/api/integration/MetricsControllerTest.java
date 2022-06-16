package com.metrics.api.integration;

import com.metrics.api.domain.Metric;
import com.metrics.api.domain.MetricValue;
import com.metrics.api.repository.MetricRepository;
import com.metrics.api.repository.MetricValueRepository;
import com.metrics.api.service.MetricService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MetricsControllerTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private MetricValueRepository metricValueRepository;
    @Autowired
    private MetricService metricService;

    @BeforeEach
    public void init() {

    }

    @Test
    public void testGetAllMetrics() {

        RestAssured
                .given().
                contentType("application/json").
                get(createURL("/metrics/")).
                then().
                statusCode(200);

    }

    @Test
    public void testGetMetricsByName() {

        RestAssured
                .given().
                contentType("application/json").
                get(createURL("/metrics/?name=metrics_test")).
                then().
                statusCode(200);

    }

    @Test
    public void testPostMetric() {
        Metric metric = new Metric();
        metric.setName("metric_test"+ Timestamp.valueOf(LocalDateTime.now()).getTime());

        RestAssured
                .given().
                contentType("application/json").
                body(metric).
                post(createURL("/metrics/")).
                then().
                statusCode(200);

    }

    @Test
    public void testPostMetricNotValidName() {
        Metric metric = new Metric();

        RestAssured
                .given().
                contentType("application/json").
                body(metric).
                post(createURL("/metrics/")).
                then().
                statusCode(400);

    }

    @Test
    public void testPostMetricValue() {

        Metric metric = metricRepository.findById(1L).get();

        MetricValue metricValue = new MetricValue();
        metricValue.setValue(1L);
        metricValue.setTime(Timestamp.valueOf(LocalDateTime.now()));
        metricValue.setMetric(metric);

        RestAssured
                .given().
                contentType("application/json").
                body(metricValue).
                post(createURL("/metrics/value")).
                then().
                statusCode(200);

    }

    @Test
    public void testPostMetricValueNotValue() {

        Metric metric = metricRepository.findById(1L).get();

        MetricValue metricValue = new MetricValue();
        metricValue.setTime(Timestamp.valueOf(LocalDateTime.now()));
        metricValue.setMetric(metric);

        RestAssured
                .given().
                contentType("application/json").
                body(metric).
                post(createURL("/metrics/value")).
                then().
                statusCode(400);

    }

    @Test
    public void testPostMetricValueNotTime() {

        Metric metric = metricRepository.findById(1L).get();

        MetricValue metricValue = new MetricValue();
        metricValue.setValue(1L);
        metricValue.setMetric(metric);

        RestAssured
                .given().
                contentType("application/json").
                body(metric).
                post(createURL("/metrics/value")).
                then().
                statusCode(400);

    }

    protected String createURL(String uri) {
        return "http://localhost:" + port + uri;
    }
}
