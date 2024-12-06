package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        System.out.println("start consumer");
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", "mygroupid");
//        properties.setProperty("auto.offset.reset", "earliest"); // none/earliest/latest
        properties.setProperty("auto.offset.reset", "latest"); // none/earliest/latest

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
//        consumer.subscribe(Arrays.asList("22Sep2024"));
        consumer.subscribe(Arrays.asList("myfirsttopic"));

        //poll for data
        while (true) {
//            System.out.println("Polling");
            // if data is in kafka return immediately otherwise wait for 1000millisecond
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord<String, String> record: records) {
                System.out.println("key: " + record.key());
                System.out.println("value: " + record.value());
                System.out.println("partition: " + record.partition());
                System.out.println("offset: " + record.offset());
            }
        }

        // flush and close the consumer
    }
}
