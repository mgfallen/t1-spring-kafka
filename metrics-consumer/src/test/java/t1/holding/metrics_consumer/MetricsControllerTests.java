package t1.holding.metrics_consumer;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import t1.holding.metrics_consumer.controller.MetricsController;
import t1.holding.metrics_consumer.dto.MetricDto;
import t1.holding.metrics_consumer.service.MetricService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricsController.class)
public class MetricsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MetricService metricService;

    @InjectMocks
    private MetricsController metricController;

    @Test
    void testGetAllMetrics() throws Exception {
        mockMvc.perform(get("/metrics"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMetricById() throws Exception {
        MetricDto metricDto = new MetricDto();
        metricDto.setId(1L);
        metricDto.setName("CPU Usage");
        metricDto.setValue(80.0);
        metricDto.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());

        when(metricService.getMetricById(1L)).thenReturn(metricDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/metrics/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"CPU Usage\",\"value\":80.0,\"timestamp\":\"" + metricDto.getTimestamp() + "\"}"));
    }
}

