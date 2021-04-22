package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@LoggedStereotype
// @Logged(prefix = "c1")
// @Logged(prefix = "c2")
public class Boundary {
    @GET
    // @LoggedStereotype
    // @Logged(prefix = "m1")
    // @Logged(prefix = "m2")
    public String ping() {
        System.out.println("ping");
        return "pong";
    }
}
