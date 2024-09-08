package t1.holding.metrics_consumer.dto;


import lombok.Data;

@Data
public class MetricDto {
    private Long id;
    private String name;
    private Double value;
    private long timestamp;
}
