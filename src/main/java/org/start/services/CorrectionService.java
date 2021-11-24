package org.start.services;

import org.start.beans.card.CardData;
import org.start.beans.correction.CorrectionData;
import org.start.entity.Card;
import org.start.entity.Correction;
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

        Correction correction = new Correction();

        correction.card = Card.findById(card_id);
        correction.code = correctionData.code;
        correction.name = correctionData.name;
        correction.date = correctionData.date;
        correction.doc = Document.findById(correctionData.doc.id);


        correction.persistAndFlush();
        return Response.ok(correction.id).build();
    }
}
