package org.start.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Audited
@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"
//
//)
public class Card extends PanacheEntity {

    public String code;
    public String name;
    public LocalDate date;
    public Boolean type = false;
    public LocalDate date_of_issue;
    public int inventory_number;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    //@JsonManagedReference
    public Document doc;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@Transient
    @JoinTable(
            joinColumns = {@JoinColumn()},
            inverseJoinColumns = {@JoinColumn()}
    )
    //@JsonManagedReference
    @JsonIgnore
    public Set<Abonent> abonents;

//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
//    @Transient
//    public List<Changes> changesList;
}
