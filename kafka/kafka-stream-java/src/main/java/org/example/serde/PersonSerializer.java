package org.example.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.example.Person;

import java.io.IOException;
import java.util.Objects;

public class PersonSerializer implements Serializer<Person> {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, Person person) {
        if (Objects.isNull(person)) return null;
        try {
            return mapper.writeValueAsBytes(person);
        } catch (JsonProcessingException e) {
            System.out.println("*********************");
            System.out.println("Exception occured during Serialization");
            e.printStackTrace();
            throw new SerializationException("Error Serializing Person object", e);
        }
    }

}
