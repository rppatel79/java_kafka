package org.rp.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProducerTest {
    @Test
    void sendMessages_whenCalled_thenMessagesSentToKafkaTopic() {
        var mockProducer = mock(Producer.class);

        // Act
        for (int i = 0; i < 10; i++) {
            var record = new ProducerRecord<>("test-topic", "key-" + i, "value-" + i);
            mockProducer.send(record);
        }

        // Assert
        for (int i = 0; i < 10; i++) {
            verify(mockProducer).send(new ProducerRecord<>("test-topic", "key-" + i, "value-" + i));
        }
    }
}