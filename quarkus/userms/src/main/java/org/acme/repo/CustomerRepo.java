package org.acme.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Customer;
import org.acme.entity.Customer;

import java.util.List;

/**
 * When using Repositories, you get the exact same convenient methods as with the active record pattern,
 * injected in your Repository, by making them implements PanacheRepository:
 */
@ApplicationScoped
public class CustomerRepo implements PanacheRepository<Customer> {

    public Customer findByName(String name){
        return find("name", name).firstResult();
    }

    public List<Customer> findByAge(int age){
        return list("age", age);
    }

    public void deleteByName(String name){
        delete("name", name);
    }
}
