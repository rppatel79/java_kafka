package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collection;

public interface ConsumerWrapperInterface<K, V> {
    void subscribe(String topic);
    void subscribe(Collection<String> topics);
    ConsumerRecords<K, V> poll(Duration timeout);
    void close();
}