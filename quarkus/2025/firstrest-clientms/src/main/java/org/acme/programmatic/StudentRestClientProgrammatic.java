package org.acme.programmatic;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StudentRestClientProgrammatic {


    @GET
    Response getAllStudents();

    @POST
    Response addStudent(Student rental);

    @GET
    @Path("/{name}")
    public Response getStudentsByName(@PathParam("name") String name);

    @DELETE
    @Path("/{name}")
    public Response deleteStudent(@PathParam("name") String name);

    @PUT
    public Response updateStudentByName(Student student);
/*    @PUT
    public Response updateStudentByName(Student student)*/
}
