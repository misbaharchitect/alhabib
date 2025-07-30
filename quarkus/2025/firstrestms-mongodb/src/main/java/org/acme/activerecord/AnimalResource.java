package org.acme.activerecord;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/animals")
public class AnimalResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalResource.class);

    @GET
    public List<Animal> getAllAnimals() {
        LOGGER.info("Getting all animals");
        return Animal.listAll();
    }

    @GET
    @Path("/{name}")
    public Response getOneAnimalByName(@PathParam("name") String name) {
        LOGGER.info("Getting all animals with name {}", name);
        Animal oneByName = Animal.findOneByName(name);

        if (Objects.isNull(oneByName)) {
            LOGGER.error("No animals found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("animalsFound {}", oneByName);

        return Response.ok(oneByName).build();
    }

    @POST
//    @Transactional
    public Response addAnimal(Animal animal) {
        LOGGER.info("Adding a animal with name {}", animal.name);
        animal.persist();
        LOGGER.info("animal added");

        return Response.created(URI.create(String.valueOf(animal.id))).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteAnimal(@PathParam("name") String name) {
        LOGGER.info("Deleting a animal with name {}", name);
        Animal.deleteByName(name);

        return Response.ok().build();
    }

    @PUT
    @Transactional
    public Response updateAnimalByName(Animal animal) {
        LOGGER.info("Updating a animal with name {}", animal.name);
        Animal oneByName = Animal.findById(animal.id);
        if (Objects.isNull(oneByName)) {
            LOGGER.error("No animals found with name {}", animal.name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("animalsFound {}", oneByName);

        LOGGER.info("updating animal");
        if (oneByName.name != null) oneByName.name = animal.name;
        if (oneByName.age > 0) oneByName.age = animal.age;
        if (oneByName.dob != null) oneByName.dob = animal.dob;
        if (oneByName.gender != null) oneByName.gender = animal.gender;

//        oneByName.persist(); // not required as entity is attached
        LOGGER.info("animal updated");
        return Response.ok(oneByName).build();
    }
}
