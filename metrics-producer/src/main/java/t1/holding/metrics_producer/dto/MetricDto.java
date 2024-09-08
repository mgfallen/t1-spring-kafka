package t1.holding.metrics_producer.dto;

import lombok.Data;

@Data
public class MetricDto {
    private String name;
    private double value;
    private String timestamp;
}
