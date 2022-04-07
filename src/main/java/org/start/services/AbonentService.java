package org.start.services;

import org.jboss.logging.Logger;
import org.start.beans.abonent.AbonentData;
import org.start.beans.abonent.AbonentIDsList;
import org.start.entity.Abonent;
import org.start.entity.Chat;
import org.start.entity.ChatToAbonent;

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


        abonent.persistAndFlush();

        return Response.ok(abonent.id).build();
    }


    @POST
    @Path("/{id}")
    @Transactional
    public Response addAbonentToCard(AbonentIDsList abonentIdList, @PathParam("id") long card_id) {
        System.out.println("qwe");


        for (long abonentId : abonentIdList.abonent_ids) {
            ChatToAbonent chatToAbonent = new ChatToAbonent();

            //TODO желательно на нормальный запрос INSERT переделать
            chatToAbonent.chat = Chat.findById(card_id);
            chatToAbonent.abonent = Abonent.findById(abonentId);

            chatToAbonent.persist();

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
