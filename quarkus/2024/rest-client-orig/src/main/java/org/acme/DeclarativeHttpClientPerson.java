package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * To externalize the baseUri refer MicroprofileConfig (mp):
 * org.acme.UsermsHttpClient/mp-rest/url=http://localhost:8081
 * https://download.eclipse.org/microprofile/microprofile-rest-client-2.0/microprofile-rest-client-spec-2.0.html#mpconfig
 */
@RegisterRestClient(baseUri = "http://localhost:8081") // overridden in application.properties
@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
public interface DeclarativeHttpClientPerson {

    @GET
    List<Person> getPersons();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response addPerson(Person person);
}
