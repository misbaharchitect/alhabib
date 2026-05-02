package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/kafka")
public class KafkaPublisherApi {

    @Inject
    @Channel("mytopic")
    Emitter<String> emitter;

    @Inject
    @Channel("employees")
    private Emitter<Employee> emitterEmployee;

    @Inject
    @Channel("students")
    private Emitter<Student> emitterStudent;

    @GET
    @Path("/{message}")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@PathParam("message") String message) {
        System.out.println("Publisher received: " + message);
        emitter.send(message);
        return "Message sent to Kafka topic 'mytopic': " + message;
    }

    @POST
    @Path("/employees")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloEmployee(Employee employee) {
        System.out.println("Publisher Employee received: " + employee.getName() + ", " + employee.getAge() + ", " + employee.getType() + ", " + employee.getDoj());
        emitterEmployee.send(employee);
        return Response.ok("{\"message\":\"Employee sent to Kafka topic 'employees'\"}").build();
    }

    @POST
    @Path("/students")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloStudent(Student student) {
        System.out.println("Publisher Student received: " + student.getName() + ", " + student.getAge() + ", " + student.getGrade() + ", " + student.getDob());
        emitterStudent.send(student);
        return Response.ok("{\"message\":\"Employee sent to Kafka topic 'employees'\"}").build();
    }
}
