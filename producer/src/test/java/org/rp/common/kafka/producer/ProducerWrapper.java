package org.rp.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerWrapper<K,V> implements ProducerWrapperInterface<K,V>
{
    private Producer<K,V> producer;
    public ProducerWrapper(){}
    public ProducerWrapper(Producer<K, V> producer) {
        this.producer = producer;
    }

    @Override
    public void send(ProducerRecord<K, V> record) {
        producer.send(record);
    }
}
