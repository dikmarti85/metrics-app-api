package com.metrics.api.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.metrics.api.domain.MetricValue;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MetricValueRepository extends PagingAndSortingRepository<MetricValue, Long>, JpaSpecificationExecutor<MetricValue> {
    @Query(value = "select SUM(value) as sum,AVG(value) as avg, YEAR(time) as year,MONTH(time) as month " +
            "from metric_value mv " +
            "left join metric m on mv.metric_id = m.id  " +
            " where m.name = :metricName and time >= :dateFrom AND time <= :currentDate " +
            " group by YEAR(time),MONTH(time) order by YEAR(time),MONTH(time)", nativeQuery = true)
    List<ArrayList> calculateMetricByYearMonth(@Param("dateFrom") Timestamp dateFrom,
                                               @Param("currentDate") Timestamp currentDate,
                                               @Param("metricName") String metricName);

    @Query(value = "select SUM(value) as sum,AVG(value) as avg, YEAR(time) as year,MONTH(time) as month,DAY(time) as day " +
            "from metric_value mv " +
            "left join metric m on mv.metric_id = m.id  " +
            " where m.name = :metricName and time >= :dateFrom AND time <= :currentDate " +
            " group by YEAR(time),MONTH(time),DAY(time) order by YEAR(time),MONTH(time),DAY(time)", nativeQuery = true)
    List<ArrayList> calculateMetricByYearMonthDay(@Param("dateFrom") Timestamp dateFrom,
                                                  @Param("currentDate") Timestamp currentDate,
                                                  @Param("metricName") String metricName);

    @Query(value = "select SUM(value) as sum,AVG(value) as avg, YEAR(time) as year,MONTH(time) as month,DAY(time) as day,HOUR(time) as hour " +
            "from metric_value mv " +
            "left join metric m on mv.metric_id = m.id  " +
            " where m.name = :metricName and time >= :dateFrom AND time <= :currentDate " +
            " group by YEAR(time),MONTH(time),DAY(time),HOUR(time) order by YEAR(time),MONTH(time),DAY(time),HOUR(time)", nativeQuery = true)
    List<ArrayList> calculateMetricByYearMonthDayHour(@Param("dateFrom") Timestamp dateFrom,
                                                      @Param("currentDate") Timestamp currentDate,
                                                      @Param("metricName") String metricName);

    @Query(value = "select SUM(value) as sum,AVG(value) as avg,YEAR(time) as year,MONTH(time) as month,DAY(time) as day,HOUR(time) as hour,MINUTE(time) as minute " +
            "from metric_value mv " +
            "left join metric m on mv.metric_id = m.id  " +
            " where m.name = :metricName and time >= :dateFrom AND time <= :currentDate " +
            " group by YEAR(time),MONTH(time),DAY(time),HOUR(time),MINUTE(time) order by YEAR(time),MONTH(time),DAY(time),HOUR(time),MINUTE(time)", nativeQuery = true)
    List<ArrayList> calculateMetricByYearMonthDayHourMinute(@Param("dateFrom") Timestamp dateFrom,
                                                            @Param("currentDate") Timestamp currentDate,
                                                            @Param("metricName") String metricName);


}
