package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {
        System.out.println("start producer");
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("myfirsttopic", "Greetings World!");
        System.out.println("Sending Message");
        producer.send(producerRecord);
        System.out.println("Message sent");

        // send all data and block until done -- synchronous
        // This is used rarely in a real application
        producer.flush();

        // flush and close the producer
        producer.close();
    }
}
