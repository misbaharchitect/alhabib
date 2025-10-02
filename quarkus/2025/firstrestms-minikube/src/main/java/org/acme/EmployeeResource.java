package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/employees")
public class EmployeeResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResource.class);
    private static final List<Employee> employees = new ArrayList<>();

    public EmployeeResource() {
        employees.add(new Employee("Brian", 22, "2003-01-15", 90));
    }

    @GET
    public List<Employee> getAllEmployees() {
        LOGGER.info("Getting all employees");
        return employees;
    }

    @GET
    @Path("/{name}")
    public Response getEmployeesByName(@PathParam("name") String name) {
        LOGGER.info("Getting all employees with name {}", name);
        List<Employee> employeesFound = employees.stream().filter(s -> s.getName().equalsIgnoreCase(name)).collect(Collectors.toList());

        if (employeesFound.isEmpty()) {
            LOGGER.error("No employees found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("employeesFound {}", employeesFound);

        return Response.ok(employeesFound).build();
    }

    @POST
    public Response addEmployee(Employee employee) {
        LOGGER.info("Adding a employee with name {}", employee.getName());
        employees.add(employee);
        LOGGER.info("employee added");

        return Response.created(URI.create(employee.getName())).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteEmployee(@PathParam("name") String name) {
        LOGGER.info("Deleting a employee with name {}", name);
        boolean areEmployeesRemoved = employees.removeIf(s -> s.getName().equalsIgnoreCase(name));

        if (!areEmployeesRemoved) {
            LOGGER.error("No employees found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("new employees list after removal {}", employees);

        return Response.ok().build();
    }

    @PUT
    public Response updateEmployeeByName(Employee employee) {
        LOGGER.info("Updating a employee with name {}", employee.getName());
        Optional<Employee> employeeFound = employees.stream().filter(s -> s.getName().equalsIgnoreCase(employee.getName())).findAny();
        if (employeeFound.isEmpty()) {
            LOGGER.error("No employees found with name {}", employee.getName());
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        Employee employeeUpdated = employeeFound.map(s -> {
            LOGGER.info("updating employee");
            if (employee.getAge() > 0) s.setAge(employee.getAge());
            if (employee.getDob() != null) s.setDob(employee.getDob());
            if (employee.getGrade() > 0) s.setGrade(employee.getGrade());
            return s;
        }).get();
        LOGGER.info("employee updated");

        return Response.ok(employeeUpdated).build();
    }
}
