package org.start.beans.card;

import org.start.entity.Abonent;
import org.start.entity.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class CardData {

    public Long id;
    public String code;
    public String name;
    public LocalDate date;
    public Boolean type = false;
    public LocalDate date_of_issue;
    public int inventory_number;
    public Document doc;
    public List<Abonent> abonents;


}
