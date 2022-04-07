package org.start.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.start.entity.IdClass.Chat_to_abonent_id;

import javax.persistence.*;

//@Audited
@Entity
@IdClass(Chat_to_abonent_id.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"

)
public class ChatToAbonent extends PanacheEntityBase {

    @Id
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    @JsonIgnore
    public Chat chat;

    @Id
    @ManyToOne
    @JoinColumn(name = "abonent_id", referencedColumnName = "id")
    @JsonIgnore
    public Abonent abonent;

    public Boolean is_host;


}
