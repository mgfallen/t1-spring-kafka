package t1.holding.metrics_consumer.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import t1.holding.metrics_consumer.dto.MetricDto;
import t1.holding.metrics_consumer.service.MetricService;

@Service
public class KafkaMetricsConsumer {
    private final MetricService metricService;

    public KafkaMetricsConsumer(MetricService metricService) {
        this.metricService = metricService;
    }

    @KafkaListener(topics = "metrics-topic", groupId = "metrics-consumer-group")
    public void listen(MetricDto metricDto) {
        metricService.processMetric(metricDto);
    }

}
