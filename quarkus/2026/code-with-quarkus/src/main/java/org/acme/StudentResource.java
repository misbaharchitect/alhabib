package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collection;

@Path("/students")
public class StudentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    private final StudentService studentService;

    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        LOGGER.info("Getting students");
        Collection<Student> students = studentService.getStudents();
        LOGGER.info("Found {} students", students.size());
        return Response.ok(students).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentById(@PathParam("id") Long id) {
        LOGGER.info("Getting student by id {}", id);
        Student studentById = studentService.getStudentById(id);
        if (studentById == null) {
            LOGGER.error("Student with id {} not found", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOGGER.info("Found student with id {}", id);
        return Response.ok(studentById).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(Student student) {
        LOGGER.info("Adding student with name {}", student.getName());
        Student addedStudent = studentService.addStudent(student);
        if (addedStudent == null) {
            LOGGER.error("Failed to add student with name {}", student.getName());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.info("Student added with id {}", addedStudent.getId());
        return Response.created(URI.create("/students/" + addedStudent.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") Long id, Student student) {
        LOGGER.info("Updating student with id {}", id);
        Student updatedStudent = studentService.updateStudent(student);
        if (updatedStudent == null) {
            LOGGER.error("Student does not exist with id {}", id);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        LOGGER.info("Student updated with id {}", updatedStudent.getId());
        return Response.ok(updatedStudent).build();
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
