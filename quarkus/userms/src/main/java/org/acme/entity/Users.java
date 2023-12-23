package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

/**
 * active record pattern
 * With PanacheEntity it's an acceptable practice to keep instance variables' access modifier as public
 * If you need a field to not be persisted, use the @Transient annotation on it.
 *
 * usage:
 *  Users.persist()
 *  if (Users.isPersistent()) Users.delete()
 *  Users.findById()
 *  Users.findByIdOptional()
 *  Users.list()
 *  Users.delete("name", "john")
 *  Users.deleteById(1)
 *  Users.update("name = 'John' where age = ?1", age);
 *  Users.update("name = 'John'").where ("age", age);
 *  Stream<Users> users = Users.streamAll()
 *      List<String> namesButAlice = Users
 *     .map(p -> p.name.toLowerCase() )
 *     .filter( n -> ! "emmanuel".equals(n) )
 *     .collect(Collectors.toList());
 *  case-insensitive and wild-card search:
 *  list("lower(company) like concat('%', lower(?1), '%') and lower(name) like concat('%', lower(?2), '%')", company, name);
 */
@Entity(name = "Users")
@Table(name = "users_data")
public class Users extends PanacheEntity {
    public String name;
    public int age;

    public static Users findByName(String name){
        return find("name", name).firstResult();
    }

    public static List<Users> findByAge(int age){
        return list("age", age);
    }

    public static void deleteByName(String name){
        delete("name", name);
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

}
