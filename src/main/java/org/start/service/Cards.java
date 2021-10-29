package org.start.service;

import io.quarkus.panache.common.Sort;
import org.start.beans.card.CardData;
import org.start.entity.Abonent;
import org.start.entity.Card;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("api/v1/cards")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class Cards {

    private static final Logger LOGGER = Logger.getLogger(Cards.class.getName());


    @POST
    @Path("")
    @Transactional
    public Response addNew(CardData cardData) {
        Card card = new Card();
        card.name = cardData.name;
        card.doc = cardData.doc;
        card.code = cardData.code;
        card.date = cardData.date;
        card.date_of_issue = cardData.date_of_issue;
        card.type = cardData.type;
        card.inventory_number = cardData.inventory_number;

        card.persist();
        return Response.ok(card.id).build();
    }

    @POST
    @Path("add2")
    @Transactional
    public Response addNew(Card card) {

        card.persist();
        return Response.ok(card.id).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getCard(@PathParam("id") long id) {
        Card card = Card.findById(id);
        List<Abonent> abonents = Abonent.list("cards_id", id);
        CardData data = new CardData();

        data.name = card.name;
        data.id = card.id;
        data.date = card.date;
        data.code = card.code;
        data.date_of_issue = card.date_of_issue;
        data.doc = card.doc;
        data.inventory_number = card.inventory_number;
        data.type = card.type;
        data.abonents = abonents;
        return Response.ok(data).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(CardData cardDataNew, @PathParam("id") long id) {
        Card card = Card.findById(id);
        if (card == null) {
            return Response.status(500).build();
            //throw new NotFoundException();

        }

        // map all fields from the person parameter to the existing entity
        card.name = cardDataNew.name;
        card.name = cardDataNew.name;
        card.doc = cardDataNew.doc;
        card.code = cardDataNew.code;
        card.date = cardDataNew.date;
        card.date_of_issue = cardDataNew.date_of_issue;
        card.type = cardDataNew.type;
        card.inventory_number = cardDataNew.inventory_number;

        card.persist();
        return Response.ok().build();
    }

    @GET
    @Path("")
    public Response getList() {
        List<Card> list = Card.listAll(Sort.by("id"));
        return Response.ok(list).build();
    }


    @GET
    @Path("list2")
    public List<Abonent> getAbonents() {
        List<Abonent> list = Abonent.listAll(Sort.by("date"));
        return list;
    }


}
