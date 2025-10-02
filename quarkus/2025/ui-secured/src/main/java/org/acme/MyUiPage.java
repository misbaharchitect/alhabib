package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import static java.util.Objects.requireNonNull;

@Path("/myui")
public class MyUiPage {

    private final Template myPage;
    private final FirsrestSecuredClient firsrestSecuredClient;

    public MyUiPage(Template myui, @RestClient FirsrestSecuredClient firsrestSecuredClient) {
        this.myPage = requireNonNull(myui, "page is required");
        this.firsrestSecuredClient = firsrestSecuredClient;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        String data = firsrestSecuredClient.getData();
        return myPage.data("response", data);
    }

}
