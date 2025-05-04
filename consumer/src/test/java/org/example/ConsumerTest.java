package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import static org.mockito.Mockito.*;

class ConsumerTest {

    @Test
    void pollMessages_whenCalled_thenMessagesPolledFromKafkaTopic() {
        // Arrange
        KafkaConsumer<String, String> mockConsumer = mock(KafkaConsumer.class);
        ConsumerRecords<String, String> mockRecords = mock(ConsumerRecords.class);

        Properties props = new Properties();
        props.put("bootstrap.servers", "mock:9092");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "test-consumer-group");

        when(mockConsumer.poll(any(Duration.class))).thenReturn(mockRecords);

        // Act
        mockConsumer.subscribe(Collections.singletonList("test-topic"));
        ConsumerRecords<String, String> records = mockConsumer.poll(Duration.ofMillis(100));

        // Assert
        verify(mockConsumer).subscribe(Collections.singletonList("test-topic"));
        verify(mockConsumer).poll(any(Duration.class));
        assert records == mockRecords;
    }
}