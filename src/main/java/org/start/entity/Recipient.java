package org.start.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"

)
@Entity

public class Recipient extends PanacheEntity {


    @ManyToOne
    @JoinColumn
    @JsonIgnore
    public Abonent recipient_id;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    public ChatToAbonent recipient_group_id;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    public Message message_id;

    public boolean is_read;

}
