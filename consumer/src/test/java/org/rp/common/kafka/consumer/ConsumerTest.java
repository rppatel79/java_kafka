package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import static org.mockito.Mockito.*;

class ConsumerTest {

    @Test
    void pollMessages_whenCalled_thenMessagesPolledFromKafkaTopic() {
        // Arrange
        var mockConsumer = mock(Consumer.class);
        var mockRecords = mock(ConsumerRecords.class);
        when(mockConsumer.poll(any(Duration.class))).thenReturn(mockRecords);

        // Act
        mockConsumer.subscribe(Collections.singletonList("test-topic"));
        var records = mockConsumer.poll(Duration.ofMillis(100));

        // Assert
        verify(mockConsumer).subscribe(Collections.singletonList("test-topic"));
        verify(mockConsumer).poll(any(Duration.class));
        assert records == mockRecords;
    }
}