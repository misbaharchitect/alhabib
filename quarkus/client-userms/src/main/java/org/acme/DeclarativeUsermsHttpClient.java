package org.acme;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public interface DeclarativeUsermsHttpClient {

    @GET
//    @Path("/users")
//    @Produces(MediaType.APPLICATION_JSON)
    List<Users> getUsers();

    @POST
//    @Path("/users")
//    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addUser(Users user);
}
