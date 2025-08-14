package org.acme.declarative;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.programmatic.StudentClientResource;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/client-employees")
public class EmployeeClientResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentClientResource.class);
    private EmployeeRestClientDeclarative employeeRestClientDeclarative;

    @Inject
    public EmployeeClientResource(@RestClient EmployeeRestClientDeclarative employeeRestClientDeclarative) {
        this.employeeRestClientDeclarative = employeeRestClientDeclarative;
    }

    @GET
    public List<Employee> getAllEmployees() {
        LOGGER.info("Getting all employees from the REST client");
        return employeeRestClientDeclarative.getAllEmployees();
    }

    @GET
    @Path("/{name}")
    public Employee getOneEmployeeByName(@RestPath String name) {
        LOGGER.info("Getting employee by name {} from the REST client", name);
        return employeeRestClientDeclarative.getOneEmployeeByName(name);
    }

    @DELETE
    @Path("/{name}")
    public Response deleteEmployee(@PathParam("name") String name) {
        LOGGER.info("Delete student by name {} from the REST client", name);
        Response response = employeeRestClientDeclarative.deleteEmployee(name);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        }
    }

    @PUT
    public Response updateEmployeeByName(Employee employee) {
        LOGGER.info("Update student by name {} from the REST client", employee.getName());
        Response response = employeeRestClientDeclarative.updateEmployeeByName(employee);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        }
    }


}
