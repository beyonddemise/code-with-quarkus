package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @Path("/world")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String world() {
        try {
            java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("test.json");
            if (is == null) {
                return "{\"error\": \"Resource not found\"}";
            }
            return new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (java.io.IOException e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }

    }
}
