package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/messages")
public class MyMessageSender {
    @Inject
    @Channel("my-topic")
    Emitter<MyMessage> myMessageEmitter;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void sendMessag(@RequestBody MyMessage message) {
        System.out.println("**** Message Received ***** " + message);
        myMessageEmitter.send(message);
        System.out.println("**** Message Sent *****");
    }
}
