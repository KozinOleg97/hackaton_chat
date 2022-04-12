package org.start.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;

//@Audited
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"

)
@Entity
public class Message extends PanacheEntity {


    public boolean type;
    public ZonedDateTime time_stamp;


    @OneToMany(mappedBy = "message_id")
    @JsonIgnore
    public Collection<Recipient> recipients;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    public Abonent creator;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    public Document doc;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    public Text text;


}
