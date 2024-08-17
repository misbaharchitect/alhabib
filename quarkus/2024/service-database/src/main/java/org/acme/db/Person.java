package org.acme.db;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

/**
 * Active Record Pattern
 */
@Entity(name = "Person")
@Table(name = "person_data")
public class Person extends PanacheEntity {
    public String firstName;
    public String lastName;
    public int ssn;
    public LocalDate dob;

    public static void deleteByFirstName(String firstName) {
        delete("firstName", firstName);
    }
    public static List<Person> findByFirstName(String firstName) {
        return list("firstName", firstName);
    }

    public static List<Person> findByLastName(String lastName) {
        return list("lastName", lastName);
    }

    public static List<Person> findBySsn(int ssn) {
        return list("ssn", ssn);
    }

    public static List<Person> findByDob(LocalDate dob) {
        return list("dob", dob);
    }

    public static List<Person> findByFirstNameAndLastname(String firstName, String lastName) {
        return list("lower(firstName) like concat('%', lower(?1), '%') and " +
                "lower(lastName) like concat('%', lower(?2), '%')", firstName, lastName);
    }
}
