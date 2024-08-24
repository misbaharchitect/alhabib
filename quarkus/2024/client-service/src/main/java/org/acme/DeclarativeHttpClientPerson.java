package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(baseUri = "http://localhost:8081")
@Path("/persons")
public interface DeclarativeHttpClientPerson {

    @GET
    List<Person> getPersonsFromServiceDatabase();

    @POST
    Response addPersonsFromServiceDatabase(Person person);

    @GET
    @Path("/{id}")
    public Response getSinglePerson(@PathParam("id") Long id);
}
