package org.start.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Audited
@Entity
public class Card_to_abonent extends PanacheEntity {

    @Id
    @ManyToOne
    public Card card;

    @Id
    @ManyToOne
    public Abonent abonent;


}
