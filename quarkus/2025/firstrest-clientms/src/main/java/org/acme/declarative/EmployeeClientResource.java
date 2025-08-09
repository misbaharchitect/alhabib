package org.acme.declarative;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/client-employees")
public class EmployeeClientResource {
    private EmployeeRestClientDeclarative employeeRestClientDeclarative;

    @Inject
    public EmployeeClientResource(@RestClient EmployeeRestClientDeclarative employeeRestClientDeclarative) {
        this.employeeRestClientDeclarative = employeeRestClientDeclarative;
    }

    @GET
    public List<Employee> getAllEmployees() {
        return employeeRestClientDeclarative.getAllEmployees();
    }

    @GET
    @Path("/{name}")
    public Employee getOneEmployeeByName(@RestPath String name) {
        return employeeRestClientDeclarative.getOneEmployeeByName(name);
    }

    @DELETE
    @Path("/{name}")
    public Response deleteEmployee(@PathParam("name") String name) {
        Response response = employeeRestClientDeclarative.deleteEmployee(name);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        }
    }

    @PUT
    public Response updateEmployeeByName(Employee employee) {
        Response response = employeeRestClientDeclarative.updateEmployeeByName(employee);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        }
    }


}
