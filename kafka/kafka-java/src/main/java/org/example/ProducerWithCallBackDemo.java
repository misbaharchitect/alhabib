package org.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerWithCallBackDemo {
    public static void main(String[] args) {
        System.out.println("start producer");
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("22Sep2024", "Callback My Salam to World!");

        RoundRobinPartitioner r;
        producer.send(producerRecord, new Callback() {
            // executes every time a record is successfully sent or an exception is thrown
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("Received Callback");
                if (e == null) {
                    System.out.println("No exception");
                    System.out.println("Topic: " + recordMetadata.topic());
                    System.out.println("hasOffset: " + recordMetadata.hasOffset());
                    System.out.println("offset: " + recordMetadata.offset());
                    System.out.println("partition: " + recordMetadata.partition());
                    System.out.println("hasTimestamp: " + recordMetadata.hasTimestamp());
                    System.out.println("serializedKeySize: " + recordMetadata.serializedKeySize());
                    System.out.println("serializedValueSize: " + recordMetadata.serializedValueSize());
                    System.out.println("timestamp: " + recordMetadata.timestamp());
                } else {
                    System.out.println("Exception in Callback" + e);
                }
            }
        });

        // send all data and block until done -- synchronous
        // This is used rarely in a real application
        producer.flush();

        // flush and close the producer
        producer.close();
    }
}
