package org.acme.db;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepo implements PanacheRepository<Customer> {

    public Customer findByName(String name) {
        return find("name", name).firstResult();
    }

    public void deleteByFirstName(String name) {
        delete("name", name);
    }
}
