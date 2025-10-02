package org.acme;

import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8081")
@AccessToken
@Path("firstrest-secured")
public interface FirsrestSecuredClient {

    @GET
    @Path("get")
    String getData();
}
