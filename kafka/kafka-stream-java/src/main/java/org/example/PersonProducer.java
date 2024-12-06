package org.example;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.serde.PersonSerializer;

import java.util.Properties;

public class PersonProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", PersonSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "10");

        KafkaProducer<String, Person> producer = new KafkaProducer<>(properties);
        Person person = new Person();
        person.setAge(21);
        person.setId(1);
        person.setFirstName("Shane");
        person.setLastName("Doe");
        ProducerRecord<String, Person> producerRecord = new ProducerRecord<>("personintopic",
                person);
//
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    System.out.println("Success");
                    System.out.println(recordMetadata.toString());
                } else {
                    System.out.println("Exception Occured");
                    e.printStackTrace();
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
