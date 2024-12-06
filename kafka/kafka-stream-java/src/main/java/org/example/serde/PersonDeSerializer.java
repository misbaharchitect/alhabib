package org.example.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.example.Person;

import java.io.IOException;
import java.util.Objects;

public class PersonDeSerializer implements Deserializer<Person> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Person deserialize(String s, byte[] bytes) {
        if (Objects.isNull(bytes)) return null;
        try {
            return mapper.readValue(bytes, Person.class);
        } catch (IOException e) {
            System.out.println("*********************");
            System.out.println("Exception occured during deserialization");
            e.printStackTrace();
            throw new SerializationException("Error DeSerializing Person object", e);
        }
    }
}
