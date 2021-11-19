package org.start.beans.abonent;

import org.start.entity.Card;

import java.time.ZonedDateTime;
import java.util.List;

public class AbonentData {

    public String code;
    public ZonedDateTime date;
    public String name;
    /**
     * Это что за поле?
     */
    public String num;
    /**
     * Что за поле? плохое название
     */
    public ZonedDateTime wr_off;
    public List<Card> cards;

}
