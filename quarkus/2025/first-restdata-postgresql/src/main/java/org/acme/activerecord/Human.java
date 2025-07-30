package org.acme.activerecord;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Humans")
@Table(name = "human_data")
public class Human  extends PanacheEntity {
    public String name;
    public int age;
    public String gender;
    public LocalDate dob;

    public static Human findOneByName(String name){
        return find("name", name).firstResult();
    }

    public static List<Human> findByAge(int age){
        return list("age", age);
    }
    public static List<Human> findAllByName(String name){
        return list("name", name);
    }

    public static void deleteByName(String name){
        delete("name", name);
    }
}
