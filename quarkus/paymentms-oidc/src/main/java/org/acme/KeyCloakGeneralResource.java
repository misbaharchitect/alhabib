package org.acme;

 import jakarta.annotation.security.RolesAllowed;
 import jakarta.ws.rs.GET;
 import jakarta.ws.rs.Path;
 import jakarta.ws.rs.Produces;
 import jakarta.ws.rs.core.Context;
 import jakarta.ws.rs.core.MediaType;
 import jakarta.ws.rs.core.SecurityContext;

 import java.security.Principal;

 @Path("")
public class KeyCloakGeneralResource {

     @GET
     @Path("/greet-user")
     @RolesAllowed("myfirst_normal_realmrole")
     @Produces(MediaType.TEXT_PLAIN)
     public String greeting(@Context SecurityContext securityContext) {
         Principal userPrincipal = securityContext.getUserPrincipal();
         return "Greeting " + userPrincipal.getName();
     }

    @GET
    @Path("/whoami")
    @RolesAllowed("myfirst_admin_realmrole")
    @Produces(MediaType.TEXT_PLAIN)
    public String whoAmI(@Context SecurityContext securityContext) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal != null) {
            return userPrincipal.getName();
        } else {
            return "anonymous";
        }
    }
}
