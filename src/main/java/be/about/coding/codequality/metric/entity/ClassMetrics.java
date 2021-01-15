package be.about.coding.codequality.check.entity;

import java.util.HashMap;
import java.util.Map;

public class ClassMetrics {

    private Map<String, Object> metrics = new HashMap<>();

    public void addMetric(String metricName, Object metric) {
        this.metrics.put(metricName, metric);
    }

}
