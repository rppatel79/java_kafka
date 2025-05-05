package org.rp.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProducerTest {

    @Mock
    private ProducerWrapperInterface<String, String> mockProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessages_whenCalled_thenMessagesSentToKafkaTopic() {
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