package org.start.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.start.entity.IdClass.Chat_to_abonent_id;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;

//@Audited
@Entity

public class ChatToAbonent extends PanacheEntity {

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    public Chat chat;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    public Abonent abonent;

    public Boolean is_active;
    public ZonedDateTime time_stamp;





}
