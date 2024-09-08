package t1.holding.metrics_producer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import t1.holding.metrics_producer.dto.MetricDto;
import t1.holding.metrics_producer.kafka.KafkaMetricProducer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "metrics-topic" })
class KafkaMetricsProducerTest {

    @Autowired
    private KafkaTemplate<String, MetricDto> kafkaTemplate;

    @Autowired
    private KafkaMetricProducer kafkaMetricsProducer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, MetricDto> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, MetricDto> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(),
                        new JsonDeserializer<>(MetricDto.class, false));
        consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "metrics-topic");
    }

    @Test
    void testSendMetric() {
        MetricDto metricDto = new MetricDto();
        metricDto.setName("CPU Usage");
        metricDto.setValue(85.0);
        metricDto.setTimestamp("2024-09-09T10:00:00");

        kafkaMetricsProducer.send(metricDto);

        ConsumerRecord<String, MetricDto> record = KafkaTestUtils.getSingleRecord(consumer, "metrics-topic");

        assertEquals("CPU Usage", record.value().getName());
        assertEquals(85.0, record.value().getValue());
        assertEquals("2024-09-09T10:00:00", record.value().getTimestamp());
    }
}

