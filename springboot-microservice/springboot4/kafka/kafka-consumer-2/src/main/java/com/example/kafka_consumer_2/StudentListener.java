package com.example.kafka_consumer_2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StudentListener {
    @KafkaListener(topics = "studenttopic", groupId = "student-group")
    public void consume(Student student) {
        System.out.println("Consumer-2 Received student: " + student.getName());
    }

}
