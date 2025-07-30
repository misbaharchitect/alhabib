package org.acme.panacherepo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.activerecord.Employee;

import java.util.List;
import java.util.Optional;

/**
 * When using Repositories, you get the exact same convenient methods as with the active record pattern,
 * injected in your Repository, by making them implements PanacheRepository:
 */
@ApplicationScoped
public class StudentRepo implements PanacheRepository<Student> {

    public Optional<Student> findOneByName(String name) {
        return find("name", name).firstResultOptional();
    }

    public List<Student> findByAge(int age){
        return list("age", age);
    }
    public List<Student> findAllByName(String name){
        return list("name", name);
    }

    public void deleteByName(String name){
        delete("name", name);
    }
}
