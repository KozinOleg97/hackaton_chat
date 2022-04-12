package org.start.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.start.entity.IdClass.Abonent_address_book_id;
import org.start.entity.IdClass.Chat_to_abonent_id;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;

@Entity
@IdClass(Abonent_address_book_id.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"

)
public class AbonentAddressBook extends PanacheEntityBase {


    public ZonedDateTime time_modified;


    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Abonent requester;

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Abonent friend;


    boolean active;

    // getters & setters


}
