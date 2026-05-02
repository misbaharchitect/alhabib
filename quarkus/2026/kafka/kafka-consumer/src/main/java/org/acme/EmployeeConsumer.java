package org.acme;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class EmployeeConsumer {

    /**
     * The @Blocking annotation in Quarkus (with SmallRye Reactive Messaging) tells the framework
     * to run the annotated method on a worker thread instead of the event loop.
     * This is important when your method performs blocking operations (like database or network calls),
     * preventing the event loop from being blocked and keeping your application responsive.
     * Use @Blocking for methods that are not fully non-blocking or reactive.
     */
    @Incoming("employees")
    @Blocking
    public void consume(Employee message) {
        System.out.println("Processing employee: with Name as " + message.getName() + ", Age as " + message.getAge()
                + ", Type as " + message.getType() + ", Date of Joining as " + message.getDoj());
    }

}
