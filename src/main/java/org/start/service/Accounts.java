//package org.start.service;
//
//import io.quarkus.panache.common.Sort;
//import org.start.entity.Card;
//import org.jboss.logging.Logger;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import java.util.List;
//
//@Path("lk")
//@ApplicationScoped
//@Produces("application/json")
//@Consumes("application/json")
//public class Accounts {
//
//    private static final Logger LOGGER = Logger.getLogger(Accounts.class.getName());
//
//
//    @GET
//    public List<Card> get() {
//        return Card.listAll(Sort.by("date"));
//    }
//
//
//}
