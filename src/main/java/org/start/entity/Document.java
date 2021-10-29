package org.start.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Audited
@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id"
//
//)
public class Document extends PanacheEntity {
    public String path_to_file;
    public String original_name;
    public String doc_type;
    public LocalDateTime date;
    public int number_of_pages;
    public String comment;
    public String format = "A4";

    @JsonIgnore
    @OneToOne(mappedBy = "doc")
    //@JsonBackReference
    public Card card;
}
