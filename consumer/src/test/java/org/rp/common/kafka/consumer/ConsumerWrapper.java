package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class ConsumerWrapper<K, V> implements ConsumerWrapperInterface<K, V>{
    private Consumer<K, V> consumer;

    public ConsumerWrapper(){}

    public ConsumerWrapper(Consumer<K, V> consumer) {
        this.consumer = consumer;
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void subscribe(Collection<String> var1)
    {
        consumer.subscribe(var1);
    }


    public ConsumerRecords<K, V> poll(Duration timeout) {
        return consumer.poll(timeout);
    }

    public void close() {
        consumer.close();
    }
}