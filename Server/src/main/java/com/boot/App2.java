package com.boot;

import java.util.Date;

import org.hibernate.Session;

import com.boot.model.Event;
import com.boot.model.Location;
import com.boot.util.HibernateUtil;

public class App2 {

    public static void main(String[] args) {
        System.out.println("Hibernate one to many (Annotation)");
        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Location location = new Location();
        
        location.setName("bob");
        location.setLat(1d);
        location.setLong(2d);
        
        session.save(location);
        
        Event event = new Event();
        event.setName("oinks");
        event.setTime("11:55:22.000");
        event.setLocation(location);
        
        location.getEvents().add(event);

        session.save(event);

        session.getTransaction().commit();
        System.out.println("Done");
    }
}
