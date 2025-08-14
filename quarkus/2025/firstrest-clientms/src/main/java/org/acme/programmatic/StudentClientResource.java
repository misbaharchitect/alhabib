package org.acme.programmatic;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.reactive.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Path("/students")
public class StudentClientResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentClientResource.class);
    private final StudentRestClientProgrammatic studentRestClient;

    public StudentClientResource() throws MalformedURLException {
        studentRestClient = RestClientBuilder.newBuilder()
                .baseUrl(new URL("http://localhost:8080"))
                .connectTimeout(500, TimeUnit.MILLISECONDS)
                .readTimeout(1200, TimeUnit.MILLISECONDS)
                .build(StudentRestClientProgrammatic.class);
    }

    @GET
    public Response getStudents() {
        LOGGER.info("Getting all students from the REST client");
        return studentRestClient.getAllStudents();
    }

    @GET
    @Path("/{name}")
    public Response getAllStudentsByName(@RestPath String name) {
        LOGGER.info("Getting student by name {} from the REST client", name);
        return studentRestClient.getStudentsByName(name);
    }

    @DELETE
    @Path("/{name}")
    public Response deleteStudent(@PathParam("name") String name) {
        LOGGER.info("Delete student by name {} from the REST client", name);
        Response response = studentRestClient.deleteStudent(name);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        }
    }

    @PUT
    public Response updateStudentByName(Student student) {
        LOGGER.info("Update student by name {} from the REST client", student.getName());
        Response response = studentRestClient.updateStudentByName(student);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            return Response.noContent().build();
        } else {
            return response;
        }
    }


}
