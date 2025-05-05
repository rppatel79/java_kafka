package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {Main.class})
@EmbeddedKafka(partitions = 2,
        topics = {"test-topic", "partition-test-topic"},
        brokerProperties = {"listeners=PLAINTEXT://localhost:0", "port=0"})
public class KafkaConsumerIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerIntegrationTest.class);
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void shouldConsumeSingleMessageSuccessfully() {
        // Arrange
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        producerProps.put("key.serializer", StringSerializer.class);
        producerProps.put("value.serializer", StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);
        try (var producer = producerFactory.createProducer()) {

            String key = "test-key";
            String value = "test-value";
            //Act
            producer.send(new ProducerRecord<>("test-topic", key, value));
            producer.flush();

            // Consumer setup
            Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
            consumerProps.put("key.deserializer", StringDeserializer.class);
            consumerProps.put("value.deserializer", StringDeserializer.class);

            try (var consumer = new Consumer<String, String>(consumerProps)) {
                consumer.subscribe(Collections.singletonList("test-topic"));

                // Poll and validate the message
                var records = consumer.poll(Duration.ofSeconds(1));

                // Assert
                assertEquals(1, records.count());
                var record = records.iterator().next();
                assertEquals(key, record.key());
                assertEquals(value, record.value());
            }

        }
    }

    @Test
    public void shouldConsumeMessagesInOrderBasedOnKeys() {
        // Arrange
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        producerProps.put("key.serializer", StringSerializer.class);
        producerProps.put("value.serializer", StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);

        // Consumer setup
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
        consumerProps.put("key.deserializer", StringDeserializer.class);
        consumerProps.put("value.deserializer", StringDeserializer.class);

        try (var producer = producerFactory.createProducer()) {
            try (var consumer = new Consumer<String, String>(consumerProps)) {
                consumer.subscribe(Collections.singletonList("test-topic"));

                //Act
                String key = "test-key1";
                for (var i = 0; i < 10; i++) {
                    producer.send(new ProducerRecord<>("partition-test-topic", key, String.valueOf(i)));
                }
                key = "test-key2";
                for (var i = 0; i < 100; i++) {
                    producer.send(new ProducerRecord<>("partition-test-topic", key, String.valueOf(i)));
                }
                producer.flush();

                // Poll and validate the message
                var records = consumer.poll(Duration.ofSeconds(1));

                // validate the message order
                int expectedValue = 0;
                for (ConsumerRecord<String, String> record : records) {
                    if (record.key().equals("test-key1") && expectedValue < 10) {
                        assertEquals(String.valueOf(expectedValue), record.value());
                        expectedValue++;
                    } else if (record.key().equals("test-key2") && expectedValue >= 10) {
                        assertEquals(String.valueOf(expectedValue - 10), record.value());
                        expectedValue++;
                    }
                }
            }
        }
    }

    @Test
    public void shouldDistributeMessagesAcrossMultipleConsumers() {
        // Arrange
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        producerProps.put("key.serializer", StringSerializer.class);
        producerProps.put("value.serializer", StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);

        // Consumer setup for two consumers
        Map<String, Object> consumerProps1 = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
        consumerProps1.put("key.deserializer", StringDeserializer.class);
        consumerProps1.put("value.deserializer", StringDeserializer.class);

        Map<String, Object> consumerProps2 = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
        consumerProps2.put("key.deserializer", StringDeserializer.class);
        consumerProps2.put("value.deserializer", StringDeserializer.class);

        try (var producer = producerFactory.createProducer();
             var consumer1 = new Consumer<String, String>(consumerProps1);
             var consumer2 = new Consumer<String, String>(consumerProps2)) {

            consumer1.assign(Collections.singletonList(new TopicPartition("partition-test-topic", 0)));
            consumer2.assign(Collections.singletonList(new TopicPartition("partition-test-topic", 1)));

            logger.debug("Starting to send messages...");
            // Act
            String key1 = "test-key1";
            for (var i = 0; i < 10; i++) {
                producer.send(new ProducerRecord<>("partition-test-topic", 0, key1, String.valueOf(i)));
            }
            String key2 = "test-key2";
            for (var i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("partition-test-topic", 1, key2, String.valueOf(i)));
            }
            producer.flush();
            logger.debug("Finished sending messages...");

            // Poll and validate the messages
            var records1 = consumer1.poll(Duration.ofSeconds(1));
            var records2 = consumer2.poll(Duration.ofSeconds(1));

            // Assert that key1 messages went to consumer1
            for (ConsumerRecord<String, String> record : records1) {
                assertEquals("test-key1", record.key());
            }
            assertEquals(10, records1.count());

            // Assert that key2 messages went to consumer2
            for (ConsumerRecord<String, String> record : records2) {
                assertEquals("test-key2", record.key());
            }
            assertEquals(100, records2.count());
        }
    }
}