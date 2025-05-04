package org.rp.common.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

public class Consumer<K, V> implements org.apache.kafka.clients.consumer.Consumer<K, V> {

    private final KafkaConsumer<K, V> consumer;

    public Consumer(KafkaConsumer<K, V> consumer) {
        this.consumer = consumer;
    }

    public Consumer(java.util.Properties prop)
    {
        this(new KafkaConsumer<>(prop));
    }

    @Override
    public Set<TopicPartition> assignment() {
        return consumer.assignment();
    }

    @Override
    public Set<String> subscription() {
        return consumer.subscription();
    }

    @Override
    public void subscribe(Collection<String> topics, ConsumerRebalanceListener listener) {
        consumer.subscribe(topics, listener);
    }

    @Override
    public void subscribe(Collection<String> topics) {
        consumer.subscribe(topics);
    }

    @Override
    public void subscribe(Pattern pattern, ConsumerRebalanceListener listener) {
        consumer.subscribe(pattern, listener);
    }

    @Override
    public void subscribe(Pattern pattern) {
        consumer.subscribe(pattern);
    }

    @Override
    public void unsubscribe() {
        consumer.unsubscribe();
    }

    @Override
    public void assign(Collection<TopicPartition> partitions) {
        consumer.assign(partitions);
    }

    @Deprecated
    @Override
    public ConsumerRecords<K, V> poll(long timeoutMs) {
        return consumer.poll(timeoutMs);
    }

    @Override
    public ConsumerRecords<K, V> poll(Duration timeout) {
        return consumer.poll(timeout);
    }

    @Override
    public void commitSync() {
        consumer.commitSync();
    }

    @Override
    public void commitSync(Duration timeout) {
        consumer.commitSync(timeout);
    }

    @Override
    public void commitSync(Map<TopicPartition, OffsetAndMetadata> offsets) {
        consumer.commitSync(offsets);
    }

    @Override
    public void commitSync(Map<TopicPartition, OffsetAndMetadata> offsets, Duration timeout) {
        consumer.commitSync(offsets, timeout);
    }

    @Override
    public void commitAsync() {
        consumer.commitAsync();
    }

    @Override
    public void commitAsync(OffsetCommitCallback callback) {
        consumer.commitAsync(callback);
    }

    @Override
    public void commitAsync(Map<TopicPartition, OffsetAndMetadata> offsets, OffsetCommitCallback callback) {
        consumer.commitAsync(offsets, callback);
    }

    @Override
    public void seek(TopicPartition partition, long offset) {
        consumer.seek(partition, offset);
    }

    @Override
    public void seek(TopicPartition partition, OffsetAndMetadata offsetAndMetadata) {
        consumer.seek(partition, offsetAndMetadata);
    }

    @Override
    public void seekToBeginning(Collection<TopicPartition> partitions) {
        consumer.seekToBeginning(partitions);
    }

    @Override
    public void seekToEnd(Collection<TopicPartition> partitions) {
        consumer.seekToEnd(partitions);
    }

    @Override
    public long position(TopicPartition partition) {
        return consumer.position(partition);
    }

    @Override
    public long position(TopicPartition partition, Duration timeout) {
        return consumer.position(partition, timeout);
    }

    @Deprecated
    @Override
    public OffsetAndMetadata committed(TopicPartition partition) {
        return consumer.committed(partition);
    }

    @Deprecated
    @Override
    public OffsetAndMetadata committed(TopicPartition partition, Duration timeout) {
        return consumer.committed(partition, timeout);
    }

    @Override
    public Map<TopicPartition, OffsetAndMetadata> committed(Set<TopicPartition> partitions) {
        return consumer.committed(partitions);
    }

    @Override
    public Map<TopicPartition, OffsetAndMetadata> committed(Set<TopicPartition> partitions, Duration timeout) {
        return consumer.committed(partitions, timeout);
    }

    @Override
    public Map<MetricName, ? extends Metric> metrics() {
        return consumer.metrics();
    }

    @Override
    public List<PartitionInfo> partitionsFor(String topic) {
        return consumer.partitionsFor(topic);
    }

    @Override
    public List<PartitionInfo> partitionsFor(String topic, Duration timeout) {
        return consumer.partitionsFor(topic, timeout);
    }

    @Override
    public Map<String, List<PartitionInfo>> listTopics() {
        return consumer.listTopics();
    }

    @Override
    public Map<String, List<PartitionInfo>> listTopics(Duration timeout) {
        return consumer.listTopics(timeout);
    }

    @Override
    public void pause(Collection<TopicPartition> partitions) {
        consumer.pause(partitions);
    }

    @Override
    public void resume(Collection<TopicPartition> partitions) {
        consumer.resume(partitions);
    }

    @Override
    public Set<TopicPartition> paused() {
        return consumer.paused();
    }

    @Override
    public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch) {
        return consumer.offsetsForTimes(timestampsToSearch);
    }

    @Override
    public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch, Duration timeout) {
        return consumer.offsetsForTimes(timestampsToSearch, timeout);
    }

    @Override
    public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions) {
        return consumer.beginningOffsets(partitions);
    }

    @Override
    public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions, Duration timeout) {
        return consumer.beginningOffsets(partitions, timeout);
    }

    @Override
    public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions) {
        return consumer.endOffsets(partitions);
    }

    @Override
    public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions, Duration timeout) {
        return consumer.endOffsets(partitions, timeout);
    }

    @Override
    public OptionalLong currentLag(TopicPartition topicPartition) {
        return consumer.currentLag(topicPartition);
    }

    @Override
    public ConsumerGroupMetadata groupMetadata() {
        return consumer.groupMetadata();
    }

    @Override
    public void enforceRebalance(String reason) {
        consumer.enforceRebalance(reason);
    }

    @Override
    public void enforceRebalance() {
        consumer.enforceRebalance();
    }

    @Override
    public void close() {
        consumer.close();
    }

    @Override
    public void close(Duration timeout) {
        consumer.close(timeout);
    }

    @Override
    public void wakeup() {
        consumer.wakeup();
    }

    public static void main(String[] args) {
        var props = org.rp.common.kafka.Properties.getConsumerProperties(System.getenv("KAFKA_BOOTSTRAP_SERVERS"));
        try (var consumer = new Consumer<String,String>(props)) {
            consumer.subscribe(Collections.singletonList("test-topic"));

            while (true) {
                var records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> System.out.printf("Consumed record: key = %s, value = %s%n", record.key(), record.value()));
            }
        }
    }
}