package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.BulkheadException;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Path("client")
@Produces(MediaType.APPLICATION_JSON)
public class ClientUsermsResource {

    @RestClient
    private DeclarativeUsermsHttpClient httpClient;

    @GET
    @Path("users")
    public Object getUsersFromUserms() {
        Log.info("Getting users from userms");
        return httpClient.getUsers();
    }

    @POST
    @Path("users")
    public Response addUserToUserms(@RequestBody Users user) {
        Log.info("Posting user to userms");
        return httpClient.addUser(user);
    }

    /**
     * Invoking fallback on Exception
     * @return
     * @throws Exception
     */
    @GET
    @Path("users-ex")
    @Fallback(fallbackMethod = "fallbackForException",
            applyOn = { Exception.class })
    public Object getUsersException() throws Exception {
        Log.info("Getting users from userms");
        throw new Exception("Manual Exception");
    }
    private Object fallbackForException() {
        Log.info("FALLBACK: fallbackForBulkhead");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("exception-occured").build();
    }

    /**
     * Bulkhead
     * value(@Bulkhead(1)) uses a semaphore, only allowing the specified number of concurrent invocations.
     * @Fallback annotation facilitates exception handling by specifying a fallback method
     * containing alternative logic when the annotated method completes exceptionally.
     */
    @GET
    @Path("users-bh")
    @Bulkhead(1)
    @Fallback(fallbackMethod = "fallbackForBulkhead",
            applyOn = { BulkheadException.class })
    public Object getUsersWithBH() {
        Log.info("Getting users from userms");
        return httpClient.getUsers();
    }
    private Object fallbackForBulkhead() {
        Log.info("FALLBACK: fallbackForBulkhead");
        return Response.status(Response.Status.TOO_MANY_REQUESTS).entity("bulkhead-invoked").build();
    }

    /**
     *
     * @return
     */
    @GET
    @Path("users-timeout/{timeout}")
    @Timeout(100)
    @Fallback(fallbackMethod = "fallbackForTimeout")
    public Object getUsersWithTimeout(@PathParam("timeout") String timeout) throws InterruptedException {
        Log.info("Getting users from userms");
        if ("yes".equalsIgnoreCase(timeout)) TimeUnit.SECONDS.sleep(1);
        return httpClient.getUsers();
    }
    private Object fallbackForTimeout(String timeout) {
        Log.info("FALLBACK: fallbackForTimeout");
        return Response.status(Response.Status.GATEWAY_TIMEOUT).entity("timout-invoked").build();
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
    @GET
    @Path("users-retry/{timeout}")
    @Timeout(100)
    @Retry(delay = 3000,
            jitter = 25,
            maxRetries = 2,
            retryOn = TimeoutException.class)
    @Fallback(fallbackMethod = "fallbackForRetry",
            applyOn = { TimeoutException.class })
    public Object getUsersWithRetry(@PathParam("timeout") String timeout) throws InterruptedException {
        Log.info("Getting users from userms");
        if ("yes".equalsIgnoreCase(timeout)) TimeUnit.SECONDS.sleep(1);
        return httpClient.getUsers();
    }
    private Object fallbackForRetry(String timeout) {
        Log.info("FALLBACK: fallbackForRetry");
        return Response.status(Response.Status.GATEWAY_TIMEOUT).entity("retry-invoked").build();
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
     * delay(500ms): The amount of time the circuit remains open before allowing a request
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
     * @throws InterruptedException
     */
    @GET
    @Path("users-cb/{cb}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(
            requestVolumeThreshold=3,
            failureRatio=.66,
            delay = 10,
            delayUnit = ChronoUnit.SECONDS,
            successThreshold=2
    )
    @Fallback(fallbackMethod = "fallbackOfUsersCircuitBreaker")
    public Object getUsersCircuitBreaker(@PathParam("cb") String cb) throws InterruptedException {
        Log.info("Starting getUsersCircuitBreaker");
        if ("yes".equalsIgnoreCase(cb)) {
            throw new RuntimeException("httpClient call failed");
        }
        return httpClient.getUsers();
    }

    private Object fallbackOfUsersCircuitBreaker(String cb) {
        Log.info("FALLBACK: Starting fallbackOfUsersCircuitBreaker");
        return Response.status(Response.Status.OK).entity("circuitbreaker-invoked").build();
    }

}
