package org.acme.activerecord;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

/**
 * active record pattern
 * With PanacheEntity it's an acceptable practice to keep instance variables' access modifier as public
 * If you need a field to not be persisted, use the @Transient annotation on it.
 * usage:
 *  employee.persist()
 *  employee.delete()
 *  Employees.listAll()
 *  if (Employees.isPersistent()) Employees.delete()
 *  Employees.findById()
 *  Employees.findByIdOptional()
 *  Employees.list("type", "Developer")
 *  Employees.delete("name", "john")
 *  Employees.deleteById(1)
 *  Employees.update("name = 'John' where age = ?1", age);
 *  Employees.update("name = 'John'").where ("age", age);
 *  Stream<Employees> employees = Employees.streamAll()
 *      List<String> namesButAlice = Employees
 *     .map(p -> p.name.toLowerCase() )
 *     .filter( n -> ! "emmanuel".equals(n) )
 *     .collect(Collectors.toList());
 *  case-insensitive and wild-card search:
 *  list("lower(company) like concat('%', lower(?1), '%') and lower(name) like concat('%', lower(?2), '%')", company, name);
 */
@Entity(name = "Employees")
@Table(name = "employees_data")
public class Employee extends PanacheEntity {
    public String name;
    public int age;
    public String type;
    public LocalDate doj;

    public static Employee findOneByName(String name){
        return find("name", name).firstResult();
    }

    public static List<Employee> findByAge(int age){
        return list("age", age);
    }
    public static List<Employee> findAllByName(String name){
        return list("name", name);
    }

    public static void deleteByName(String name){
        delete("name", name);
    }
}
