package org.start.services;

import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;
import org.start.beans.abonent.AbonentData;
import org.start.entity.Abonent;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("api/v1/abonents")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AbonentService {

    private static final Logger LOGGER = Logger.getLogger(AbonentService.class.getName());

    @POST
    @Path("")
    @Transactional
    public Response addNew(AbonentData abonentData) {

        Abonent abonent = new Abonent();

        abonent.code = abonentData.code;
        abonent.date = abonentData.date;
        abonent.name = abonentData.name;
        abonent.num = abonentData.num;


        abonent.persistAndFlush();

        return Response.ok(abonent.id).build();
    }

    @GET
    @Path("")
    public Response getAbonentList() {
        return Response.ok(Abonent.listAll()).build();
    }

}
