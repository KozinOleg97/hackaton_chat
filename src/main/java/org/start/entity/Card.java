//package org.start.entity;
//
//import io.quarkus.hibernate.orm.panache.PanacheEntity;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import javax.persistence.Transient;
//import java.time.LocalDate;
//import java.util.List;
//
//@Entity
//public class Card extends PanacheEntity {
//
//    public String code;
//    public String name;
//    public LocalDate date;
//    public String format = "A4";
//    public Boolean type = false;
//    public LocalDate date_in;
//    public int inventory_number;
//    public int number_of_pages;
//
//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
//    @Transient
//    public List<Abonent> abonents;
//
//    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
//    @Transient
//    public List<Changes> changesList;
//}
