package com.metrics.api.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataCalculationDTO {

    private List<String> minutes;
    private List<String> hours;
    private List<String> days;
    private List<String> months;
    private List<String> years;
    private List<BigDecimal> sums;
    private List<BigDecimal> avgs;

    public List<String> getMinutes() {
        return minutes;
    }

    public void setMinutes(List<String> minutes) {
        this.minutes = minutes;
    }

    public List<String> getHours() {
        return hours;
    }

    public void setHours(List<String> hours) {
        this.hours = hours;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public List<BigDecimal> getSums() {
        return sums;
    }

    public void setSums(List<BigDecimal> sums) {
        this.sums = sums;
    }

    public List<BigDecimal> getAvgs() {
        return avgs;
    }

    public void setAvgs(List<BigDecimal> avgs) {
        this.avgs = avgs;
    }
}