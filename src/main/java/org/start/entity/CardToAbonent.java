package org.start.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.envers.Audited;
import org.start.entity.IdClass.Card_to_abonent_id;

import javax.persistence.*;

//@Audited
@Entity
@IdClass(Card_to_abonent_id.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"

)
public class CardToAbonent extends PanacheEntityBase {

    @Id
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @JsonIgnore
    public Card card;

    @Id
    @ManyToOne
    @JoinColumn(name = "abonent_id", referencedColumnName = "id")
    @JsonIgnore
    public Abonent abonent;


}
