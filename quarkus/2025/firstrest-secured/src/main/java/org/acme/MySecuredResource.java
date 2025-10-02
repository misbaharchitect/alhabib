package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.SecurityContext;

@Path("/firstrest-secured")
public class MySecuredResource {

    private final SecurityContext securityContext;

    public MySecuredResource(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @GET
    @Path("/get")
    public String securedEndpoint() {
        String userId = securityContext.getUserPrincipal() != null
                ? securityContext.getUserPrincipal().getName() : "anonymous";
        return "Hello, " + userId + "! This is a Microservice secured endpoint.";
    }
}
