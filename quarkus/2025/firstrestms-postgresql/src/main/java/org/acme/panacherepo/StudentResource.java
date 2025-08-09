package org.acme.panacherepo;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/students")
public class StudentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);
    private final StudentRepo studentRepo;

    @Inject
    public StudentResource(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GET
    public List<Student> getAllStudents() {
        LOGGER.info("Getting all users");
        return studentRepo.listAll();
    }

    @GET
    @Path("/{name}")
    public Response getStudentsByName(@PathParam("name") String name) {
        LOGGER.info("Getting all users with name {}", name);
        List<Student> allByName = studentRepo.findAllByName(name);
        if (allByName.isEmpty()) {
            LOGGER.error("No students found with name {}", name);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }

        LOGGER.info("studentsFound {}", allByName);

        return Response.ok(allByName).build();
    }

    @POST
    @Transactional
    public Response addStudent(Student student) {
        LOGGER.info("Adding a user with name {}", student.getName());
        studentRepo.persist(student);
        LOGGER.info("student added");

        return Response.created(URI.create(student.getId().toString())).build();
    }

    @DELETE
    @Path("/{name}")
    public Response deleteStudent(@PathParam("name") String name) {
        LOGGER.info("Deleting a user with name {}", name);
        studentRepo.deleteByName(name);

        return Response.ok().build();
    }

    @PUT
    @Transactional
    public Response updateStudent(Student student) {
        LOGGER.info("Updating a user with name {}", student.getName());
        Optional<Student> studentFoundOpt = studentRepo.findOneByName(student.getName());
        if (studentFoundOpt.isEmpty()) {
            LOGGER.error("No students found {}", student);
            Response.Status notFound = Response.Status.NOT_FOUND;
            return Response.status(notFound).build();
        }
        Student studentFound = studentFoundOpt.get();
        LOGGER.info("updating student");
        if (student.getName() != null) studentFound.setName(student.getName());
        if (student.getAge() > 0) studentFound.setAge(student.getAge());
        if (student.getDob() != null) studentFound.setDob(student.getDob());
        if (student.getGrade() > 0) studentFound.setGrade(student.getGrade());
        LOGGER.info("student updated");

        return Response.ok(studentFound).build();
    }
}
