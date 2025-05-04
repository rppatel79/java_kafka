package org.rp.common.kafka;

public class Properties
{
    public static java.util.Properties getProducerProperties(String bootstrapServers) {
        if (bootstrapServers == null || bootstrapServers.isEmpty()) {
            throw new IllegalArgumentException("Environment variable KAFKA_BOOTSTRAP_SERVERS is not set");
        }

        java.util.Properties props = new java.util.Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("linger.ms", 1);
        return props;
    }
}
