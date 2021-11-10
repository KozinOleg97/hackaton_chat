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
public class Card extends PanacheEntity {

    public String code;
    public String name;
    public ZonedDateTime date;
    public Boolean type = false;
    public ZonedDateTime date_of_issue;
    public String inventory_number;





    @OneToMany(mappedBy = "card")
    public Collection<CardToAbonent> abonents;

    @OneToMany(mappedBy = "card")
    public Collection<Correction> corrections;






//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
//    @Transient
//    public List<Changes> changesList;
}
