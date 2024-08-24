package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {


    @GET
    public List<Student> getAllPersons() {
        Log.info("Getting all persons from database");
        return Student.listAll();
    }

    @GET
    @Path("/static")
    public List<Student> getStaticStudent() {
        Log.info("GetStaticStudent");
        Student qqq = new Student();
        qqq.name = "qqq";
        qqq.marks=12;

        Student www = new Student();
        www.setName("www");
        www.setMarks(33);
        List<Student> studentsList = List.of(qqq, www);
        return studentsList;
    }

    @POST
    @Path("/static")
    public Student addtaticStudent(@RequestBody(name = "student") Student student) {
        Log.infof("GetStaticStudent %s and %s ", student.getName(), student.getMarks());
        Log.info("****************************");
        student.id=new ObjectId();
        student.marks=123;
        Log.infof("GetStaticStudent %s and %s ", student.getName(), student.getMarks());
        return student;
    }


    @POST
    public Response addPerson(@RequestBody Student student) throws URISyntaxException {
        Log.infof("Adding student to database with name %s", student.getName());
        Log.infof("Adding student to database with marks %s", student.getMarks());
        Student.persist(student);
        Log.info("****************************");
        Log.infof("Adding student to database with name %s", student.getName());
        Log.infof("Adding student to database with marks %s", student.getMarks());
        Log.infof("Succesfully add student with id %s to database", student.id);
        return Response.created(new URI("students/"+student.id.toString())).entity(student).build();
    }

}
