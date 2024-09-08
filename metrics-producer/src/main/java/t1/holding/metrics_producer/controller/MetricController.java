package t1.holding.metrics_producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import t1.holding.metrics_producer.dto.MetricDto;
import t1.holding.metrics_producer.service.MetricService;

import java.util.Set;

@RestController
@RequestMapping("/metrics")
public class MetricController {
    private final MetricService metricService;

    @Autowired
    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping
    public String sendAllMetrics() {
        metricService.sendApplicationMetrics(null);
        return "All metrics sent to Kafka";
    }

    @PostMapping("/select")
    public String sendSelectedMetrics(@RequestParam Set<String> metricNames) {
        metricService.sendApplicationMetrics(metricNames);
        return "Selected metrics sent to Kafka";
    }



}
