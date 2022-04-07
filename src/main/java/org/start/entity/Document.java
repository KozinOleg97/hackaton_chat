package org.start.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

//@Audited
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"
)
public class Document extends PanacheEntity {
    public String path_to_file;
    public String original_name;
    public String doc_type;
    public ZonedDateTime date;

    public String comment;


    @OneToOne(mappedBy = "doc")
    public Message message;
}
