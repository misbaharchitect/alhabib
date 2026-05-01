package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import static java.util.Objects.requireNonNull;

@Path("/some-page-admin")
public class SomePageAdmin {

    private final Template myui;
    private final FirsrestSecuredClient firsrestSecuredClient;

    /**
     * Maps to Template page -> page.qute.html
     * @param page
     * @param firsrestSecuredClient
     */
    public SomePageAdmin(Template page,@RestClient FirsrestSecuredClient firsrestSecuredClient) {
        this.myui = requireNonNull(page, "page is required");
        this.firsrestSecuredClient = firsrestSecuredClient;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
//    @RolesAllowed("admin")
    public TemplateInstance get(@QueryParam("name") String name) {
        String data = firsrestSecuredClient.getDataAdmin();
        return myui.data("response", data);
    }

}
