package org.acme.programmatic;

import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.reactive.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Path("/students")
public class StudentClientResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentClientResource.class);
    private final StudentRestClientProgrammatic studentRestClient;

    public StudentClientResource() throws MalformedURLException {
        studentRestClient = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://localhost:8080"))
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(1200, TimeUnit.MILLISECONDS)
                .build(StudentRestClientProgrammatic.class);
    }

    @GET
    public Response getStudents() {
        LOGGER.info("Getting all students from the REST client");
        return studentRestClient.getAllStudents();
    }

    @GET
    @Path("/{name}")
    public Response getAllStudentsByName(@RestPath String name) {
        LOGGER.info("Getting student by name {} from the REST client", name);
        return studentRestClient.getStudentsByName(name);
    }

    @DELETE
    @Path("/{name}")
    public Response deleteStudent(@PathParam("name") String name) {
        LOGGER.info("Delete student by name {} from the REST client", name);
        Response response = studentRestClient.deleteStudent(name);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        }
    }

    @PUT
    @Bulkhead(1)
    @Fallback(fallbackMethod = "updateStudentBulkheadFallback", applyOn = { BulkheadException.class })
    public Response updateStudentByName(Student student) {
        LOGGER.info("Update student by name {} from the REST client", student.getName());
        Response response = studentRestClient.updateStudentByName(student);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return response;
        }
    }

    public Response updateStudentBulkheadFallback(Student student) {
        LOGGER.warn("Bulkhead limit reached, cannot update student {}", student.getName());
        return Response.status(Response.Status.TOO_MANY_REQUESTS)
                .entity("TOO_MANY_REQUESTS, please try again later.")
                .build();
    }

    @PUT
    @Path("timeout/{timeout}")
    @Timeout(100)
    @Fallback(fallbackMethod = "fallbackForTimeout")
    public Object updateStudentWithTimeout(@PathParam("timeout") String timeout, Student student) throws InterruptedException {
        Log.info("timeout-Update students in firstrestms-postgresql");
        if ("yes".equalsIgnoreCase(timeout)) TimeUnit.SECONDS.sleep(1);
        LOGGER.info("Timeout-Update student by name {} from the REST client", student.getName());
        Response response = studentRestClient.updateStudentByName(student);
        
        return response;
    }
    private Object fallbackForTimeout(String timeout, Student student) {
        Log.info("FALLBACK: fallbackForTimeout");
        return Response.status(Response.Status.GATEWAY_TIMEOUT).entity("{\"error\": \"timout-invoked\"}").build();
    }

    /**
     * delay: Delay between each retry (ChronoUnit.MILLIS)
     * jitter: Adds or subtracts a random amount of time between each retry. For example, a
     *         delay of 100ms with a jitter of 20ms results in a delay between 80m and 120ms
     * Use the Retry resilience strategy with caution. Retrying a remote call on an
     * overloaded backend service with a small delay exacerbates the problem.
     *
     * "@Retry" annotation can be used together with @Bulkhead, @CircuitBreaker, @Fallback, and @Timeout
     *
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    @PUT
    @Path("retry/{timeout}")
    @Timeout(100)
    @Retry(delay = 3000,
            jitter = 25,
            maxRetries = 2,
            retryOn = TimeoutException.class)
    @Fallback(fallbackMethod = "fallbackForRetry",
            applyOn = { TimeoutException.class })
    public Object updateStudentWithRetry(@PathParam("timeout") String timeout, Student student) throws InterruptedException {
        Log.info("retry-Update students in studentms");
        if ("yes".equalsIgnoreCase(timeout)) TimeUnit.SECONDS.sleep(1);
        LOGGER.info("Retry-Update student by name {} from the REST client", student.getName());
        Response response = studentRestClient.updateStudentByName(student);

        return response;
    }
    private Object fallbackForRetry(String timeout, Student student) {
        Log.info("FALLBACK: fallbackForRetry");
        return Response.status(Response.Status.GATEWAY_TIMEOUT).entity("{\"error\": \"retry-invoked\"}").build();
    }

    /**
     * requestVolumeThreshold(20): The size of the rolling window (number of requests) used to calculate
     * the opening of a circuit
     *
     * failureRatio(0.5): Open the circuit if the ratio of failed requests within the
     * requestVolumeThreshold window exceeds this number. For example,
     * if the requestVolumeThreshold is 4, then two failed requests of the
     * last four will open the circuit
     *
     * delay(5000ms): The amount of time the circuit remains open before allowing a request
     *
     * delayUnit(ChronoUnit.MILLIS): Time unit of the delay parameter
     *
     * successThreshold(1): The number of successful trial requests to close the circuit
     *
     * failOn(Any exception): List of exceptions which should be considered failures
     *
     * skipOn(None): List of exceptions that should not open the circuit. This list takes
     * precedence over the types listed in the failOn parameter
     *
     * The @CircuitBreaker annotation can be used together with @Timeout, @Fallback, @Bulkhead, and @Retry.
     *
     * @return
     */
    @PUT
    @Path("circuitbreaker/{cb}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(
            requestVolumeThreshold=3,
            failureRatio=.66,
            delay = 10,
            delayUnit = ChronoUnit.SECONDS,
            successThreshold=2
    )
    @Fallback(fallbackMethod = "fallbackOfStudentsCircuitBreaker")
    public Object updateStudentWithCircuitBreaker(@PathParam("cb") String cb, Student student) {
        Log.info("Watch CircuitBreaker");
        if ("yes".equalsIgnoreCase(cb)) {
            Log.error("Throwing RuntimeException to trigger CircuitBreaker");
            throw new RuntimeException("updateStudent call failed");
        }
        LOGGER.info("CircuitBreaker-Update student by name {} from the REST client", student.getName());
        Response response = studentRestClient.updateStudentByName(student);

        return response;
    }

    private Object fallbackOfStudentsCircuitBreaker(String cb, Student student) {
        Log.info("FALLBACK: Starting fallbackOfStudentsCircuitBreaker");
        return Response.status(Response.Status.OK).entity("{\"error\": \"circuitbreaker-invoked\"}").build();
    }


}
