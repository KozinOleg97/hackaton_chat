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
public class Chat extends PanacheEntity {

    public String description;
    public String name;
    public Boolean is_active;
    public ZonedDateTime create_date;


    @OneToMany(mappedBy = "chat")
    public Collection<ChatToAbonent> abonents;




//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
//    @Transient
//    public List<Changes> changesList;
}
