package be.about.coding.codequality.metric.analyse;

public interface AnalysisListener {

    void receive(Character next);

    Object getMetrics();

    String getMetricName();
}
