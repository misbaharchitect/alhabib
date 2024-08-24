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

@Path("client")
@Produces(MediaType.APPLICATION_JSON)
public class ClientPersonResource {

    @RestClient
    private DeclarativeHttpClientPerson httpClient;

    @GET
    @Path("persons")
    public List<Person> getPersonsFromUserms() {
        Log.info("Getting persons from another service");
        return httpClient.getPersons();
    }

    @POST
    @Path("persons")
    public Response addUserTo(Person person) {
        Log.info("Posting persons to another service");
        return httpClient.addPerson(person);
    }

    /**
     * Resiliency Patterns
     */

    @GET
    @Path("persons-ex")
    @Fallback(fallbackMethod = "fallbackForException",
            applyOn = { Exception.class })
    public Object getPersonsWithFallback() throws Exception {
        Log.info("Getting persons from another service");
        throw new Exception("Manual Exception");
    }

    private Object fallbackForException() {
        Log.info("FALLBACK: fallbackForException");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("exception-occured").build();
    }

    @GET
    @Path("circuit-breaker/{cb}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(
            requestVolumeThreshold=3,
            failureRatio=.66,
            delay = 5,
            delayUnit = ChronoUnit.SECONDS,
            successThreshold=2
    )
    @Fallback(fallbackMethod = "fallbackOfCircuitBreaker")
    public Object getPersonsCircuitBreaker(@PathParam("cb") String cb) {
        Log.info("Starting getPersonsCircuitBreaker");
        if ("yes".equalsIgnoreCase(cb)) {
            throw new RuntimeException("httpClient call failed");
        }
        return httpClient.getPersons();
    }

    private Object fallbackOfCircuitBreaker(String cb) {
        Log.info("FALLBACK: Starting fallbackOfgetPersonsCircuitBreaker");
        return Response.status(Response.Status.OK).entity("circuitbreaker-invoked").build();
    }
}
