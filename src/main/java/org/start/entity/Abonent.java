package org.start.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
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

    /**
     * Это что за поле?
     */
    public int num;
    /**
     * Что за поле? плохое название
     */
    public ZonedDateTime wr_off;


    @OneToMany(mappedBy = "abonent")
    public Collection<CardToAbonent> cards;



}
