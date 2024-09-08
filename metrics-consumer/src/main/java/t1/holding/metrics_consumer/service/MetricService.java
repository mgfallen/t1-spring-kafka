package t1.holding.metrics_consumer.service;

import org.springframework.stereotype.Service;
import t1.holding.metrics_consumer.dto.MetricDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricService {

    private final List<MetricDto> metrics = new ArrayList<>();

    public void processMetric(MetricDto metricDto) {
        metrics.add(metricDto);
    }

    public List<MetricDto> getAllMetrics() {
        return new ArrayList<>(metrics);
    }

    public MetricDto getMetricById(Long id) {
        return metrics.stream()
                .filter(metric -> metric.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}

