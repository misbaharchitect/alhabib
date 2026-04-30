package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collection;

@Path("/students/v2")
public class StudentResourceV2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentService studentService;

    public StudentResourceV2(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        LOGGER.info("Getting students");
        Collection<StudentV2> studentV2s = studentService.getStudents();
        LOGGER.info("Found {} students", studentV2s.size());
        return Response.ok(studentV2s).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") Long id) {
        LOGGER.info("Getting student by id {}", id);
        StudentV2 studentV2ById = studentService.getStudentById(id);
        if (studentV2ById == null) {
            LOGGER.error("Student with id {} not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOGGER.info("Found student with id {}", id);
        return Response.ok(studentV2ById).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(StudentV2 studentV2) {
        LOGGER.info("Adding student with name {}", studentV2.getName());
        StudentV2 addedStudentV2 = studentService.addStudent(studentV2);
        if (addedStudentV2 == null) {
            LOGGER.error("Failed to add student with name {}", studentV2.getName());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.info("Student added with id {}", addedStudentV2.getId());
        return Response.created(URI.create("/students/" + addedStudentV2.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") Long id, StudentV2 studentV2) {
        LOGGER.info("Updating student with id {}", id);
        StudentV2 updatedStudentV2 = studentService.updateStudent(studentV2);
        if (updatedStudentV2 == null) {
            LOGGER.error("Student does not exist with id {}", id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.info("Student updated with id {}", updatedStudentV2.getId());
        return Response.ok(updatedStudentV2).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") Long id) {
        LOGGER.info("Deleting student with id {}", id);
        boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted) {
            LOGGER.error("Student does not exist with id {}", id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.info("Student deleted with id {}", id);
        return Response.noContent().build();
    }

}
