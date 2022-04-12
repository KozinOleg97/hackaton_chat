package org.start.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Path("api/v1/alert")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AlertService {
    private static final Logger LOGGER = Logger.getLogger(AlertService.class.getName());

    @GET
    @Path("")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Response stream() {
        return Response.ok().build();
    }

    @Inject
    EventBus bus;

//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    @Path("{name}")
//    public Uni<String> greeting(@PathParam("name") String name) {
//        return bus.<String>request("greeting", name).onItem().transform(Message::body);
//    }



}
