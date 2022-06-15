package com.metrics.api.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class MetricTest {

    Metric metric;

    @BeforeEach
    void init() {
        metric = new Metric();
    }

    @Test
    void validId() {
        metric.setId(1L);
        metric.setName("metric_test");
        assertEquals(metric.getId(), 1L);
    }

    @Test
    void validNotEqualId() {
        metric.setId(1L);
        metric.setName("metric_test");
        assertNotEquals(metric.getId(), 2L);
    }

    @Test
    void validEqualName() {
        metric.setId(1L);
        metric.setName("metric_test");
        assertEquals(metric.getName(), "metric_test");
    }

    @Test
    void validEqualNotName() {
        metric.setId(1L);
        metric.setName("metric_test");
        assertNotEquals(metric.getName(), "metric_test1");
    }

    @Test
    void validDate() {
        metric.setId(1L);
        metric.setName("metric_test");
        assertTrue(metric.getDateCreated().getTime() <= Timestamp.valueOf(LocalDateTime.now()).getTime());
    }

    @Test
    void validDateNotNull() {
        metric.setId(1L);
        metric.setName("metric_test");
        assertTrue(metric.getDateCreated().getTime() <= Timestamp.valueOf(LocalDateTime.now()).getTime());
    }

}
