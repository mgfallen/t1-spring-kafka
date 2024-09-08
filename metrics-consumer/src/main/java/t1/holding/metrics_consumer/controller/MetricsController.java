package t1.holding.metrics_consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.holding.metrics_consumer.dto.MetricDto;
import t1.holding.metrics_consumer.service.MetricService;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private final MetricService metricService;

    public MetricsController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping
    public List<MetricDto> getAllMetrics() {
        return metricService.getAllMetrics();
    }

    @GetMapping("/{id}")
    public MetricDto getMetricById(@PathVariable Long id) {
        return metricService.getMetricById(id);
    }
}
