package org.start.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Text extends PanacheEntity {

    public String text;

    @OneToOne(mappedBy = "text")
    public Message message;

}
