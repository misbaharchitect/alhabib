package org.acme.activerecord;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@MongoEntity(collection = "animals")
public class Animal extends PanacheMongoEntity {
    public String name;
    public int age;
    public String gender;
    public LocalDate dob;
    public boolean isEndangered;

    public static Optional<Animal> findByNameAndAgeOptional(
            String name, int age) {
        return find("name = ?1 and age = ?2",
                name, age)
                .firstResultOptional();
    }

    public static List<Animal> listEndangered() {
        return list("isEndangered", true);
    }

    public static Animal findOneByName(String name){
        return find("name", name).firstResult();
    }

    public static List<Animal> findByAge(int age){
        return list("age", age);
    }
    public static List<Animal> findAllByName(String name){
        return list("name", name);
    }

    public static void deleteByName(String name){
        delete("name", name);
    }
}
