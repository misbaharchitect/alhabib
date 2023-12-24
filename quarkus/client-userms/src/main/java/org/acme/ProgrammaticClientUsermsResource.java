package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("client-cust")
@Produces(MediaType.APPLICATION_JSON)
public class ProgrammaticClientUsermsResource {
    public ProgrammaticClientUsermsResource() throws MalformedURLException {
    }
    ProgramaticUsermsHttpClient accountServiceUrl = RestClientBuilder.newBuilder()
            .baseUrl(new URL("http://localhost:8081"))
            .connectTimeout(500, TimeUnit.MILLISECONDS)
            .readTimeout(1200, TimeUnit.MILLISECONDS)
            .build(ProgramaticUsermsHttpClient.class);



    @GET
    public Response getUsersFromUserms() throws MalformedURLException {
        Log.info("Getting users from userms");
        List<Customer> customers = accountServiceUrl.getCustomers();
        return Response.ok(customers).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserToUserms(@RequestBody Customer customer) {
        Log.info("Posting customer to userms");
        return accountServiceUrl.addCustomer(customer);
    }
}
