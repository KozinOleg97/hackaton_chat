package org.start.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import org.start.beans.card.CardData;
import org.start.entity.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Path("api/v1/cards")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CardService {

    private static final Logger LOGGER = Logger.getLogger(CardService.class.getName());


    @POST
    @Path("")
    @Transactional
    public Response addNew(CardData cardData) {

        Card card = new Card();
        card.name = cardData.name;
        card.code = cardData.code;
        card.date = cardData.date;
        card.date_of_issue = cardData.date_of_issue;
        card.type = cardData.type;
        card.inventory_number = cardData.inventory_number;

        card.persistAndFlush();


        Correction correction = new Correction();
        correction.date = ZonedDateTime.now();
        correction.code = "0";
        correction.doc = Document.findById(cardData.doc.id);
        correction.card = card;

        correction.persist();




        //TODO Нужно проверить адекватность такого связывания
        for (long id : cardData.abonent_ids) {
            CardToAbonent relation = new CardToAbonent();
            relation.card = card;
            relation.abonent = Abonent.findById(id);
            relation.persist();
        }


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
//    @Transactional()
    public Response getCard(@PathParam("id") long id) {

        Card card = Card.findById(id);


        //List<PanacheEntityBase> cardToAbonent = CardToAbonent.list("card_id", id);

        /*
        Возвращает список абонентов связанных с выбранной карточкой
         */
        Collection<CardToAbonent> list = Abonent.find("SELECT a " +
                "FROM Abonent a " +
                "INNER JOIN a.cards c " +
                "WHERE c.card =  ?1", card).list();



        List<Correction> corrections = Correction.list("card_id", id);


        card.abonents = list;
        card.corrections = corrections;

        return Response.ok(card).build();
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
        //card.doc = cardDataNew.doc;
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
