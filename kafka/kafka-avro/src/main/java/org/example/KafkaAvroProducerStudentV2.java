package org.example;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.v2.Student;

import java.util.List;
import java.util.Properties;

public class KafkaAvroProducerStudentV2 {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        properties.setProperty("schema.registry.url", "http://127.0.0.1:8081");
        properties.setProperty("acks", "1");
        properties.setProperty("retries", "10");

        KafkaProducer<String, org.example.v2.Student> producer = new KafkaProducer<>(properties);
        org.example.v2.Student student = new org.example.v2.Student("Zack", null, 88, List.of("ABC", "XYZ"));
        ProducerRecord<String, org.example.v2.Student> producerRecord = new ProducerRecord<>("avro-students",
                student);
//
        Student.Builder builder = Student.newBuilder();
//        builder.s
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