package org.start.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

//@Audited
@Entity
public class Correction extends PanacheEntity {

    public String name;
    public String code;
    public boolean type;
    /**
     * Нужно ли время для метки изменений?
     */
    public ZonedDateTime date;

    @ManyToOne
    @JoinColumn
    public Card card;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    public Document doc;



}
