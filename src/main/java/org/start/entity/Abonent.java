package org.start.entity;

import com.fasterxml.jackson.annotation.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Audited
@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"
//
//)

public class Abonent extends PanacheEntity {

    public String abonent;
    public LocalDate date;
    public String name;

    /**
     * Это что за поле?
     */
    public int num;
    /**
     * Что за поле? плохое название
     */
    public LocalDate wr_off;

    @JsonIgnore
    @ManyToMany(mappedBy = "abonents", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JsonBackReference
    public Set<Card> cards;


}
