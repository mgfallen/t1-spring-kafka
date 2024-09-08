package t1.holding.metrics_consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import t1.holding.metrics_consumer.dto.MetricDto;
import t1.holding.metrics_consumer.service.MetricService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MetricsServiceTests {

    private MetricService metricService;

    @BeforeEach
    void setUp() {
        metricService = new MetricService();
    }

    @Test
    void testProcessMetric() {
        MetricDto metricDto = new MetricDto();
        metricDto.setId(1L);
        metricDto.setName("CPU Usage");
        metricDto.setValue(80.0);
        metricDto.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

        metricService.processMetric(metricDto);

        List<MetricDto> metrics = metricService.getAllMetrics();
        assertEquals(1, metrics.size());
        assertEquals(metricDto, metrics.getFirst());
    }

    @Test
    void testGetMetricById() {
        MetricDto metricDto = new MetricDto();
        metricDto.setId(1L);
        metricDto.setName("Memory Usage");
        metricDto.setValue(70.0);
        metricDto.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

        metricService.processMetric(metricDto);

        MetricDto retrievedMetric = metricService.getMetricById(1L);
        assertNotNull(retrievedMetric);
        assertEquals(metricDto, retrievedMetric);
    }
}

