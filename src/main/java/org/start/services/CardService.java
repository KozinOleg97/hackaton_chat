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


        List<PanacheEntityBase> cardToAbonent = CardToAbonent.list("card_id", id);

        Collection<CardToAbonent> list = Abonent.find("SELECT a " +
                "FROM Abonent a " +
                "INNER JOIN a.cards c " +
                "WHERE c.card =  ?1", card).list();

//        PanacheQuery<PanacheEntityBase> panacheEntityBasePanacheQuery = Abonent.find("select distinct a " +
//                "from Abonent a" +
//                "inner join  a.cards");

        //Abonent.list("SELECT items from Abonent items")
        //Abonent.list("id IN (?1)", );


//        Session session = em.unwrap(Session.class);
//        MultiIdentifierLoadAccess<Abonent> multiLoadAccess = session.byMultipleIds(Abonent.class);
//        List

//        List<Abonent> abonents = CardToAbonent.find
//                ("SELECT * FROM abonent\n" +
//                        "INNER JOIN cardtoabonent on abonent.id = cardtoabonent.abonent_id\n" +
//                        "WHERE card_id = ?1", id).list();


//        List<Card_to_abonent> cardToAbonents = Card_to_abonent.listAll();

///*TODO сюда нормальный запрос к базе, эт для теста*/
//        List<CardToAbonent> list = CardToAbonent.find("card_id", id).list();
//        List<Abonent> abonents = list.stream().map(cardToAbonent -> cardToAbonent.abonent).collect(Collectors.toList());


//        CardToAbonent cardToAbonent = CardToAbonent.list();


//        CardData data = new CardData();
//
//        data.name = card.name;
//        data.id = card.id;
//        data.date = card.date;
//        data.code = card.code;
//        data.date_of_issue = card.date_of_issue;
//        data.doc = card.doc;
//        data.inventory_number = card.inventory_number;
//        data.type = card.type;
//        data.abonents = list;


        card.abonents = list;

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
