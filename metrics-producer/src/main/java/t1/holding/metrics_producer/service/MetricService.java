package t1.holding.metrics_producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t1.holding.metrics_producer.dto.MetricDto;
import t1.holding.metrics_producer.kafka.KafkaMetricProducer;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class MetricService {

    private final KafkaMetricProducer kafkaMetricProducer;

    @Autowired
    public MetricService(KafkaMetricProducer kafkaMetricProducer) {
        this.kafkaMetricProducer = kafkaMetricProducer;
    }

    public void sendApplicationMetrics(Set<String> selectedMetrics) {
        Map<String, MetricDto> allMetrics = collectAllMetrics();

        if (selectedMetrics == null || selectedMetrics.isEmpty()) {
            allMetrics.values().forEach(kafkaMetricProducer::send);
        } else {
            selectedMetrics.forEach(metricName -> {
                MetricDto metricDto = allMetrics.get(metricName);
                if (metricDto != null) {
                    kafkaMetricProducer.send(metricDto);
                }
            });
        }
    }

    private Map<String, MetricDto> collectAllMetrics() {
        Map<String, MetricDto> metrics = new HashMap<>();
        metrics.put("CPU Usage", collectCpuUsage());
        metrics.put("Heap Memory Usage", collectMemoryUsage());

        return metrics;
    }

    private MetricDto collectCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getSystemLoadAverage();

        MetricDto cpuMetric = new MetricDto();
        cpuMetric.setName("CPU Usage");
        cpuMetric.setValue(cpuLoad);
        cpuMetric.setTimestamp(LocalDateTime.now().toString());

        return cpuMetric;
    }

    private MetricDto collectMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long usedMemory = heapMemoryUsage.getUsed();

        MetricDto memoryMetric = new MetricDto();
        memoryMetric.setName("Heap Memory Usage");
        memoryMetric.setValue((double) usedMemory / (1024 * 1024));
        memoryMetric.setTimestamp(LocalDateTime.now().toString());

        return memoryMetric;
    }
}
