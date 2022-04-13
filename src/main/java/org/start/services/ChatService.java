package org.start.services;

import io.quarkus.security.identity.SecurityIdentity;
import org.start.beans.Chat.*;
import org.start.beans.card.CardData;
import org.start.entity.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Authenticated
@Path("api/v1/chats")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ChatService {

    private static final Logger LOGGER = Logger.getLogger(ChatService.class.getName());


    @Inject
    SecurityIdentity securityIdent;

    @POST
    @Path("")
    @Transactional
    public Response addNew(CardData cardData) {

        Chat chat = new Chat();
        chat.name = cardData.name;

        chat.create_date = cardData.date_of_issue;


        chat.persistAndFlush();


        Message message = new Message();

        message.doc = Document.findById(cardData.doc.id);
//        message.chat = chat;

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

        chat.create_date = cardDataNew.date_of_issue;


        chat.persist();
        return Response.ok().build();
    }

    @GET
    @Path("")
    public Response getListOfChatsByAbonentLogin() {
        SecurityIdentity s = securityIdent;

        String requester_login = s.getPrincipal().getName();


        Collection<Chat> list = Chat.find("select c from Chat c\n" +
                "inner JOIN c.abonents a\n" +
                "where a.abonent.name = ?1", requester_login).list();

        ChatListData chatListData = new ChatListData();
        chatListData.list = new ArrayList<>();

        for (Chat chat : list) {

            ChatData data = new ChatData();
            data.id = chat.id;
            data.name = chat.name;

            chatListData.list.add(data);

        }

        chatListData.user_name = s.getPrincipal().getName();

        return Response.ok(chatListData).build();
    }

    @GET
    @Path("/{id}")
    public Response getChatMessagesById(@PathParam("id") long id) {

        Chat chat = Chat.findById(id);

        Collection<Message> messagesRaw = Message.list("recipient_group", chat);

        List<MessageDataToSend> messages = new ArrayList<>();

        for (Message mesRaw : messagesRaw) {
            MessageDataToSend mes = new MessageDataToSend();
            mes.creator = mesRaw.creator.name;
            mes.time_stamp = mesRaw.time_stamp;
            mes.text = mesRaw.text.text;

            messages.add(mes);
        }


        return Response.ok(messages).build();
    }

    @GET
    @Path("/friends")
    public Response getFriendList() {
        SecurityIdentity s = securityIdent;
        String requester_login = s.getPrincipal().getName();


        Collection<Chat> list = Chat.find("select a from Abonent a\n" +
                "inner join a.friends r\n" +
                "where r.requester.name = ?1", requester_login).list();
        return Response.ok(list).build();
    }

    //TODO доделать
    @POST
    @Path("/friends/invite")
    @Transactional
    public Response inviteFriend(FriendRequest friendRequest) {

        for (String elem : friendRequest.loginList) {

            AbonentAddressBook addressBook = new AbonentAddressBook();

            addressBook.persist();

        }


        return Response.ok().build();
    }

    @POST
    @Path("/{id}/send")
    @Transactional
    public Response sendMessage(MessageDataReceived messageDataReceived, @PathParam("id") long groupId) {
        SecurityIdentity s = securityIdent;
        String sender_name = s.getPrincipal().getName();

        Text text = new Text();
        text.text = messageDataReceived.text;

        Abonent creator = Abonent.find("name", sender_name).firstResult();

        Chat recipient_group = Chat.findById(groupId);

        Message message = new Message();
        message.text = text;
        message.creator = creator;
        message.doc = null;
        message.time_stamp = messageDataReceived.timeStamp;
        message.type = true;
        message.recipient_group = recipient_group;


        message.persist();


        return Response.ok().build();
    }


}
