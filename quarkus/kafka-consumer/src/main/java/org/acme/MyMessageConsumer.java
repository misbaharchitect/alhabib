package org.acme;

import org.eclipse.microprofile.reactive.messaging.Incoming;

public class MyMessageConsumer {

    @Incoming("my-topic")
    public void receiveMessag(MyMessage message) {
        System.out.println("Message received as: " + message);

    }
}
