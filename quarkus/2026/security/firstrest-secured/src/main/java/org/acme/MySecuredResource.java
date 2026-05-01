package org.acme;

import jakarta.annotation.security.RolesAllowed;
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
        return "Hello User, " + userId + "! This is User Microservice secured endpoint.";
    }

    @GET
    @Path("/get-admin")
    @RolesAllowed("admin")
    public String securedEndpointAdmin() {
        String userId = securityContext.getUserPrincipal() != null
                ? securityContext.getUserPrincipal().getName() : "anonymous";
        return "Hello Admin, " + userId + "! This is Admin Microservice secured endpoint.";
    }
}
