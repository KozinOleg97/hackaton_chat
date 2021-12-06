package org.start.beans.card;

import org.start.entity.Abonent;
import org.start.entity.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@RequestScoped
public class CardData {

    public Long id;
    public String code;
    public String name;
    public ZonedDateTime date;
    public Boolean type = false;
    public ZonedDateTime date_of_issue;
    public String inventory_number;
    public Document doc;
    //TODO Long or Integer
    public List<Integer> abonent_ids;


}
