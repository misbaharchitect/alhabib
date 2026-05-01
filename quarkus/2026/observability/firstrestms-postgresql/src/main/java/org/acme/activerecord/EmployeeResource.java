package org.acme.activerecord;

import io.micrometer.core.annotation.Counted;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/employees")
public class EmployeeResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResource.class);

    @GET
    public List<Employee> getAllEmployees() {
        LOGGER.info("Getting all employees");
        return Employee.listAll();
    }

    @GET
    @Path("/{name}")
    public Response getOneEmployeeByName(@PathParam("name") String name) {
        LOGGER.info("Getting one employee by name {}", name);
        Employee oneByName = Employee.findOneByName(name);

        if (Objects.isNull(oneByName)) {
            LOGGER.error("No employees found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("employeesFound {}", oneByName);

        return Response.ok(oneByName).build();
    }

    @POST
    @Transactional
    @Counted(value = "employees", description = "Number of employees added")
    public Response addEmployee(Employee employee) {
        LOGGER.info("Adding a employee with name {}", employee.name);
        employee.persist();
        LOGGER.info("employee added");

        return Response.created(URI.create(String.valueOf(employee.id))).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteEmployee(@PathParam("name") String name) {
        LOGGER.info("Deleting a employee with name {}", name);
        Employee.deleteByName(name);

        return Response.ok().build();
    }

    @PUT
    @Transactional
    public Response updateEmployee(Employee employee) {
        LOGGER.info("Updating a employee with name {}", employee.name);
//        Employee oneByName = Employee.findById(employee.id);
        Employee oneByName = Employee.findOneByName(employee.name);
        if (Objects.isNull(oneByName)) {
            LOGGER.error("No employees found with name {}", employee.name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("employeesFound {}", oneByName);

        LOGGER.info("updating employee");
        if (oneByName.name != null) oneByName.name = employee.name;
        if (oneByName.age > 0) oneByName.age = employee.age;
        if (oneByName.doj != null) oneByName.doj = employee.doj;
        if (oneByName.type != null) oneByName.type = employee.type;

//        oneByName.persist(); // not required as entity is attached
        LOGGER.info("employee updated");
        return Response.ok(oneByName).build();
    }
}
