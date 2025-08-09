package org.acme.declarative;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

/**
 * Timeouts are set per client interface in the config file (application.properties).
 */
@RegisterRestClient(baseUri = "http://localhost:8080")
@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EmployeeRestClientDeclarative {


    @GET
    List<Employee> getAllEmployees();

    @GET
    @Path("/{name}")
    Employee getOneEmployeeByName(@RestPath String name);

    @POST
    Employee addEmployee(Employee employee);

    @DELETE
    @Path("/{name}")
    Response deleteEmployee(@PathParam("name") String name);

    @PUT
    Response updateEmployeeByName(Employee employee);
}
