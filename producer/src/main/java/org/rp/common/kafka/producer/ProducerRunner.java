package org.rp.common.kafka.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.rp.common.kafka.Properties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProducerRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        var props = Properties.getProducerProperties(System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS","localhost:9092"));
        var producer = new Producer<String,String>(props);
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("test-topic", "key-" + i, "value-" + i));
            System.out.println("Sent record: key = key-" + i + ", value = value-" + i);
        }
        producer.close();
    }
}