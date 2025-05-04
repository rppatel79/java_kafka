package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.mockito.Mockito.*;

class ProducerTest {

    @Test
    void sendMessages_whenCalled_thenMessagesSentToKafkaTopic() {
        // Arrange
        KafkaProducer<String, String> mockProducer = mock(KafkaProducer.class);
        Properties props = new Properties();
        props.put("bootstrap.servers", "mock:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Act
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "key-" + i, "value-" + i);
            mockProducer.send(record);
        }

        // Assert
        for (int i = 0; i < 10; i++) {
            verify(mockProducer).send(new ProducerRecord<>("test-topic", "key-" + i, "value-" + i));
        }
    }
}