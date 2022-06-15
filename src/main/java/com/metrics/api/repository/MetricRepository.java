package com.metrics.api.repository;

import com.metrics.api.domain.Metric;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MetricRepository extends PagingAndSortingRepository<Metric, String>, JpaSpecificationExecutor<Metric> {

    Optional<Metric> findById(Long id);

}
