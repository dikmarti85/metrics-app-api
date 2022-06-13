package com.metrics.api.repository;

import java.util.List;
import java.util.Optional;

import com.metrics.api.domain.Metric;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MetricRepository extends PagingAndSortingRepository<Metric, String>, JpaSpecificationExecutor<Metric> {

}
