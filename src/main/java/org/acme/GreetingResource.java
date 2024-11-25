package org.acme;

import io.quarkus.vertx.web.Route;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.openapi.contract.OpenAPIContract;
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

    @Route(path = "/hello/pet", methods = Route.HttpMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public void pet(RoutingContext ctx) {
        try {
            // Petsstore is a OpenAPI 3.1 JSON example
            java.io.InputStream is = getClass().getClassLoader().getResourceAsStream("petstore-v3.1.json");
            if (is == null) {
                ctx.json("{\"error\": \"Resource not found\"}");
            }
            // Now read the file into a String and turn it into an OpenAPI model
            // parsing the model will trigger the read operation on json-schema.org/**
            String textContract = new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            JsonObject jsonContract = new JsonObject(textContract);
            OpenAPIContract.from(ctx.vertx(), jsonContract)
                    .onFailure(ctx::fail)
                    .onSuccess(oai -> ctx
                            .json("Successful loaded OpenAPI contract petstore-v3.1.json:" + oai.getVersion()));
        } catch (java.io.IOException e) {
            ctx.json("{\"error\": \"" + e.getMessage() + "\"}");
        }

    }
}
