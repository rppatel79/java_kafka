package org.rp.common.kafka.consumer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

@Component
public class ConsumerRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        var props = org.rp.common.kafka.Properties.getConsumerProperties(System.getenv().getOrDefault("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092"));

        try (var consumer = new Consumer<String, String>(props)) {
            consumer.subscribe(Collections.singletonList("test-topic"));

            System.out.println("Consumer started. Listening to topic: test-topic");
            while (true) {
                var records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> System.out.printf("Consumed record: key = %s, value = %s%n", record.key(), record.value()));
            }
        } catch (Exception e) {
            System.err.println("Error occurred while consuming messages: " + e.getMessage());
            e.printStackTrace();
        }
    }
}