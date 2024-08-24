package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Path("client-person")
@Produces(MediaType.APPLICATION_JSON)
public class ClientPersonResource {

    @RestClient
    private DeclarativeHttpClientPerson httpClientPerson;

    @GET
    @Path("/persons")
    public List<Person> getPersonFromServiceDatabase() {
        Log.info("Getting all persons from service-database");
        return httpClientPerson.getPersonsFromServiceDatabase();
    }

    @GET
    @Path("/persons/cb/{cbflag}")
    @CircuitBreaker(
            requestVolumeThreshold = 3,
            failureRatio = 0.66,
            delay = 5,
            delayUnit = ChronoUnit.SECONDS,
            successThreshold = 2
    )
    @Fallback(fallbackMethod = "fallbackGetPerson")
    public Object getPersonFromServiceDatabaseWithCircuitBreaker(@PathParam("cbflag") String cbflag) {
        Log.info("Getting all persons from service-database");
        if ("yes".equalsIgnoreCase(cbflag)) {
            throw new RuntimeException("httClient call failed");
        }
        Log.info("About to call service-database");
        return httpClientPerson.getPersonsFromServiceDatabase();
    }

    private Object fallbackGetPerson(String cbflag) {
        Log.info("fallbackForException invoked");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("circuit-breaker").build();
    }

    @GET
    @Path("/persons-exception")
    @Fallback(fallbackMethod = "fallbackForException")
    public Object getPersonWithException() throws Exception {
        Log.info("About to throw Exception");
        throw new Exception("Manual Exception");
    }

    private Object fallbackForException() {
        Log.info("fallbackForException invoked");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("exception-occurred").build();
    }

    @POST
    @Path("/persons")
    public Response addPersonFromServiceDatabase(Person person) {
        Log.info("Getting all persons from service-database");
        return httpClientPerson.addPersonsFromServiceDatabase(person);
    }

    @GET
    @Path("/persons/{id}")
    public Response getPersonFromServiceDatabase(@PathParam("id") Long id) {
        Log.info("Getting all persons from service-database");
        return httpClientPerson.getSinglePerson(id);
    }
}
