package org.acme.db;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jboss.resteasy.reactive.RestQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/persons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    public List<Person> getAllPersons() {
        Log.info("Getting all persons from database");
        return Person.listAll();
    }

    @GET
    @Path("/byFirstName")
    public List<Person> getAllPersonsByFirstName(@RestQuery String firstName) {
        Log.infof("Getting all persons from database with firstName as %s", firstName);
        return Person.findByFirstName(firstName);
    }

    @GET
    @Path("/byLastName")
    public List<Person> getAllPersonsByLastName(@RestQuery String lastName) {
        Log.infof("Getting all persons from database with lastName as %s", lastName);
        return Person.findByLastName(lastName);
    }

    @GET
    @Path("/{id}")
    public Response getSinglePerson(@PathParam("id") Long id) {
        Log.infof("Getting single person with id %s from database", id);
        Optional<PanacheEntityBase> personFound = Person.findByIdOptional(id);
        if (personFound.isEmpty()) {
            Log.errorf("Person not found with id %s from database", id);
            return Response.status(NOT_FOUND).build();
        }
        return Response.ok(personFound.get()).build();
    }

    @POST
    @Transactional
    public Response addPerson(@RequestBody Person person) throws URISyntaxException {
        Log.info("Adding person to database");
        Person.persist(person);
        Log.infof("Succesfully add person with id %s to database", person.id);
        return Response.created(new URI("persons/"+person.id.toString())).entity(person).build();
    }
}
