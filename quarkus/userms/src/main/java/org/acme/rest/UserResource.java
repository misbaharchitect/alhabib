package org.acme.rest;

import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Users;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("users")
@Produces(APPLICATION_JSON)
public class UserResource {

    @GET
    public List<Users> allUsers() {
        Log.info("Getting all users");
        /*User john = new User();
        john.setId(1);
        john.setName("John");

        return List.of(john, john);*/
        return Users.listAll();
    }

    @GET
    @Path("id/{id}")
    public Users findByName(@PathParam("id") Long id) {
        Log.infof("Getting one user by name %s", id);
        return Users.findById(id);
    }

    @GET
    @Path("name/{name}")
    public Users findByName(@PathParam("name") String name) {
        Log.infof("Getting one user by name %s", name);
        return Users.findByName(name);
    }

    @GET
    @Path("age/{age}")
    public List<Users> findByName(@PathParam("age") int age) {
        Log.info("Getting all users");
        return Users.findByAge(age);
    }
    @DELETE
    @Path("name/{name}")
    @Transactional
    public void deleteByName(@PathParam("name") String name) {
        Log.info("Getting all users");
        Users.deleteByName(name);
    }

    @PUT
    @Path("age/{age}")
    @Transactional
    public void updateNameByAge(@PathParam("age") int age) {
        Log.info("Getting all users");
        Users.update("name = 'John' where age = ?1", age);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Transactional
    public Response newUser(@RequestBody Users user) throws URISyntaxException {
        Log.infof("save user %s", user);
        user.id = null;
        user.persist();
        return Response.created(new URI(user.id.toString())).entity(user).build();
    }
}
