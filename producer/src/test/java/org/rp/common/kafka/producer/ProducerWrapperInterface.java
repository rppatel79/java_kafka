package org.rp.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

public interface ProducerWrapperInterface<K,V>{
    void send(ProducerRecord<K, V> record);
}
