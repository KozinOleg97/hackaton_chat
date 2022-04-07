package org.start.services;

import io.quarkus.panache.common.Sort;
import org.start.beans.card.CardData;
import org.start.entity.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;

@Path("api/v1/chats")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ChatService {

    private static final Logger LOGGER = Logger.getLogger(ChatService.class.getName());


    @POST
    @Path("")
    @Transactional
    public Response addNew(CardData cardData) {

        Chat chat = new Chat();
        chat.name = cardData.name;

        chat.date_of_issue = cardData.date_of_issue;


        chat.persistAndFlush();


        Message message = new Message();

        message.doc = Document.findById(cardData.doc.id);
        message.chat = chat;

        message.persist();




        //TODO Нужно проверить адекватность такого связывания
        for (long id : cardData.abonent_ids) {
            ChatToAbonent relation = new ChatToAbonent();
            relation.chat = chat;
            relation.abonent = Abonent.findById(id);
            relation.persist();
        }


        return Response.ok(chat.id).build();
    }

    @POST
    @Path("add2")
    @Transactional
    public Response addNew(Chat chat) {

        chat.persist();
        return Response.ok(chat.id).build();
    }


    @GET
    @Path("/{id}")
//    @Transactional()
    public Response getCard(@PathParam("id") long id) {

        Chat chat = Chat.findById(id);


        //List<PanacheEntityBase> cardToAbonent = CardToAbonent.list("card_id", id);

        /*
        Возвращает список абонентов связанных с выбранной карточкой
         */
        Collection<ChatToAbonent> list = Abonent.find("SELECT a " +
                "FROM Abonent a " +
                "INNER JOIN a.cards c " +
                "WHERE c.card =  ?1", chat).list();



        List<Message> messages = Message.list("card_id", id);


        chat.abonents = list;
        chat.messages = messages;

        return Response.ok(chat).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(CardData cardDataNew, @PathParam("id") long id) {
        Chat chat = Chat.findById(id);
        if (chat == null) {
            return Response.status(500).build();
            //throw new NotFoundException();

        }

        // map all fields from the person parameter to the existing entity
        chat.name = cardDataNew.name;
        chat.name = cardDataNew.name;
        //card.doc = cardDataNew.doc;

        chat.date_of_issue = cardDataNew.date_of_issue;


        chat.persist();
        return Response.ok().build();
    }

    @GET
    @Path("")
    public Response getList() {
        List<Chat> list = Chat.listAll(Sort.by("date_of_issue"));
        return Response.ok(list).build();
    }


    @GET
    @Path("list2")
    public List<Abonent> getAbonents() {
        List<Abonent> list = Abonent.listAll(Sort.by("date"));
        return list;
    }


}
