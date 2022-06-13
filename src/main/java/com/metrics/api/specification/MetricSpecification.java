package com.metrics.api.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.metrics.api.domain.Metric;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MetricSpecification implements Specification<Metric> {

    private String name;

    @Override
    public Predicate toPredicate(Root<Metric> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> andCriteria = new ArrayList<>();
       
        if(!StringUtils.isEmpty(name)) {
            andCriteria.add(criteriaBuilder.equal(root.get("name"), name));
        }

        if (andCriteria.size() > 0) {
            return criteriaBuilder.and(andCriteria.toArray(new Predicate[andCriteria.size()]));
        }

        return criteriaBuilder.conjunction();

    }

}