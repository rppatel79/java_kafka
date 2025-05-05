package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumerTest {

    @Mock
    private ConsumerWrapperInterface<String, String> consumerWrapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void pollMessages_whenCalled_thenMessagesPolledFromKafkaTopic() {
        // Mock behavior
        ConsumerRecords<String, String> mockRecords = new ConsumerRecords<>(Collections.emptyMap());
        when(consumerWrapper.poll(any(Duration.class))).thenReturn(mockRecords);

        // Test logic
        consumerWrapper.subscribe("test-topic");
        ConsumerRecords<String, String> records = consumerWrapper.poll(Duration.ofMillis(100));

        // Assertions
        assertNotNull(records);
        verify(consumerWrapper).poll(any(Duration.class));
    }
}