package org.start.services;

import org.jboss.logging.Logger;
import org.start.beans.abonent.AbonentData;
import org.start.entity.Abonent;
import org.start.entity.Card;
import org.start.entity.CardToAbonent;

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


    @POST
    @Path("/{id}")
    @Transactional
    public Response addAbonentToCard(@PathParam("id") long card_id, long[] abonentIdList) {


        for (long abonentId : abonentIdList) {
            CardToAbonent cardToAbonent = new CardToAbonent();

            //TODO желательно на нормальный запрос INSERT переделать
            cardToAbonent.card = Card.findById(card_id);
            cardToAbonent.abonent = Abonent.findById(abonentId);

            cardToAbonent.persist();

        }


        return Response.ok().build();
    }

    @GET
    @Path("")
    public Response getAbonentList() {
        return Response.ok(Abonent.listAll()).build();
    }


    @GET
    @Path("/{id}")
    public Response getAbonentByID(@PathParam("id") long id) {

        Abonent abonent = Abonent.findById(id);


        return Response.ok(abonent).build();
    }


}
