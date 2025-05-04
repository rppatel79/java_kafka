package org.rp.common.kafka;

public class Properties {
    public static java.util.Properties getConsumerProperties(String bootstrapServers) {
        if (bootstrapServers == null || bootstrapServers.isEmpty()) {
            throw new IllegalArgumentException("Environment variable KAFKA_BOOTSTRAP_SERVERS is not set");
        }

        var props = new java.util.Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "test-consumer-group");

        return props;
    }

}
