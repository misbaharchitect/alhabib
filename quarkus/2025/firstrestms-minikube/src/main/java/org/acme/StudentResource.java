package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/students")
public class StudentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);
    private static final List<Student> students = new ArrayList<>();

    public StudentResource() {
        students.add(new Student("John", 20, "2003-01-15", 90));
    }

    @GET
    public List<Student> getAllStudents() {
        LOGGER.info("Getting all students");
        return students;
    }

    @GET
    @Path("/{name}")
    public Response getStudentsByName(@PathParam("name") String name) {
        LOGGER.info("Getting all students with name {}", name);
        List<Student> studentsFound = students.stream().filter(s -> s.getName().equalsIgnoreCase(name)).collect(Collectors.toList());

        if (studentsFound.isEmpty()) {
            LOGGER.error("No students found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("studentsFound {}", studentsFound);

        return Response.ok(studentsFound).build();
    }

    @POST
    public Response addStudent(Student student) {
        LOGGER.info("Adding a student with name {}", student.getName());
        students.add(student);
        LOGGER.info("student added");

        return Response.created(URI.create(student.getName())).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteStudent(@PathParam("name") String name) {
        LOGGER.info("Deleting a student with name {}", name);
        boolean areStudentsRemoved = students.removeIf(s -> s.getName().equalsIgnoreCase(name));

        if (!areStudentsRemoved) {
            LOGGER.error("No students found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("new students list after removal {}", students);

        return Response.ok().build();
    }

    @PUT
    public Response updateStudentByName(Student student) {
        LOGGER.info("Updating a student with name {}", student.getName());
        Optional<Student> studentFound = students.stream().filter(s -> s.getName().equalsIgnoreCase(student.getName())).findAny();
        if (studentFound.isEmpty()) {
            LOGGER.error("No students found with name {}", student.getName());
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        Student studentUpdated = studentFound.map(s -> {
            LOGGER.info("updating student");
            if (student.getAge() > 0) s.setAge(student.getAge());
            if (student.getDob() != null) s.setDob(student.getDob());
            if (student.getGrade() > 0) s.setGrade(student.getGrade());
            return s;
        }).get();
        LOGGER.info("student updated");

        return Response.ok(studentUpdated).build();
    }
}
