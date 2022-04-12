package org.start.services;

import org.start.beans.correction.CorrectionData;
import org.start.entity.Chat;
import org.start.entity.Message;
import org.start.entity.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("api/v1/corrections")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CorrectionService {


    @POST
    @Path("/{id}")
    @Transactional
    public Response addNew(@PathParam("id") long card_id, CorrectionData correctionData) {

        Message message = new Message();

//        message.chat = Chat.findById(card_id);
        message.doc = Document.findById(correctionData.doc.id);


        message.persistAndFlush();
        return Response.ok(message.id).build();
    }
}
