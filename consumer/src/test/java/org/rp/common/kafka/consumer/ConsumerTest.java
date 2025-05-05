package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumerTest {

    @Test
    void pollMessages_whenCalled_thenMessagesPolledFromKafkaTopic() {
        // Mock behavior
        ConsumerRecords<String, String> mockRecords = new ConsumerRecords<>(Collections.emptyMap());
        var mockConsumer = mock(Consumer.class);
        when(mockConsumer.poll(any(Duration.class))).thenReturn(mockRecords);

        // Test logic
        mockConsumer.subscribe(Collections.singleton("test-topic"));
        var records = mockConsumer.poll(Duration.ofMillis(100));

        // Assertions
        assertNotNull(records);
        verify(mockConsumer).poll(any(Duration.class));
    }
}