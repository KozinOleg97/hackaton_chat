package org.start.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

//@Audited
@Entity
public class Message extends PanacheEntity {


    public String text;
    public boolean type;
    public ZonedDateTime time_stamp;

    @ManyToOne
    @JoinColumn
    public Chat chat;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    public Document doc;


}
