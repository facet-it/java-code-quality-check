package be.about.coding.codequality.metric.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ClassMetrics {

    private Map<String, Object> metrics = new HashMap<>();

    public void addMetric(String metricName, Object metric) {
        this.metrics.put(metricName, metric);
    }

}
