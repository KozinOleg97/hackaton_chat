package org.start.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;

//@Audited
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"

)

public class Abonent extends PanacheEntity {

    public String code;
    public ZonedDateTime date;
    public String name;

    public Boolean online;


    @OneToMany(mappedBy = "abonent")
    public Collection<ChatToAbonent> chats;



}
