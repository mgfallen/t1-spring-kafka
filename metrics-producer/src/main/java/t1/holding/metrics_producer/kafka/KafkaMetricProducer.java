package t1.holding.metrics_producer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import t1.holding.metrics_producer.dto.MetricDto;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;

@Component
public class KafkaMetricProducer {

    private static final String DEFAULT_TOPIC = "metrics-topic";
    private final KafkaTemplate<String, MetricDto> kafkaTemplate;

    @Autowired
    public KafkaMetricProducer(KafkaTemplate<String, MetricDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(MetricDto metricDto) {
        send(DEFAULT_TOPIC, metricDto);
    }

    public void send(String topic, MetricDto metricDto) {
        kafkaTemplate.send(topic, metricDto);
    }

    public void send(String topic, String key, MetricDto metricDto) {
        kafkaTemplate.send(topic, key, metricDto);
    }

    public void sendMultiple(String topic, MetricDto... metrics) {
        for (MetricDto metric : metrics) {
            kafkaTemplate.send(topic, metric);
        }
    }
}
