package org.acme;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

import static java.util.Objects.requireNonNull;

@Path("/myuiold")
public class MyUiPageOld {

    private final Template myPage;
    private final SecurityContext securityContext;

    public MyUiPageOld(Template page2, SecurityContext securityContext) {
        this.myPage = requireNonNull(page2, "page is required");
        this.securityContext = securityContext;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        String userId = securityContext.getUserPrincipal() != null
                ? securityContext.getUserPrincipal().getName() : null;
        return myPage.data("name", userId);
    }

}
