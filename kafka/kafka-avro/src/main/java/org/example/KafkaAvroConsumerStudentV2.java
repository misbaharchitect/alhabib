package org.example;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaAvroConsumerStudentV2 {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("group.id", "mystudentconsumerV2");
        properties.setProperty("enable.auto.commit", "false");
        properties.setProperty("auto.offset.reset", "earliest");

        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", KafkaAvroDeserializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");
        properties.setProperty("specific.avro.reader", "true");

        KafkaConsumer<String, org.example.v2.Student> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList("avro-students"));

        //poll for data
        while (true) {
            System.out.println("Polling");
            // if data is in kafka return immediately otherwise wait for 1000millisecond
            ConsumerRecords<String, org.example.v2.Student> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord<String, org.example.v2.Student> record: records) {
                System.out.println(record.value());
                System.out.println("key: " + record.key());
                System.out.println("value: " + record.value());
                System.out.println("partition: " + record.partition());
                System.out.println("offset: " + record.offset());
            }
            consumer.commitSync();
        }
    }
}
