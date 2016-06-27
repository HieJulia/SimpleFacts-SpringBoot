package com.boot.controller;

import com.boot.model.Event;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.boot.model.Location;
import com.boot.repository.LocationRepository;
import com.boot.util.HibernateUtil;
import org.hibernate.Session;

@RestController
@RequestMapping("api/v1/")
public class LocationController {
    
    @Autowired
    private LocationRepository locationRepository;
    
    @RequestMapping(value = "Create", method = RequestMethod.GET)
    public void create() {
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

    @RequestMapping(value = "Locations", method = RequestMethod.GET)
    public List<Location> list() {
        return locationRepository.findAll();
    }

    @RequestMapping(value = "Locations", method = RequestMethod.POST)
    public Location create(@RequestBody Location location) {
        return locationRepository.saveAndFlush(location);
    }

    @RequestMapping(value = "Locations/{id}", method = RequestMethod.GET)
    public Location get(@PathVariable Long id) {
        return locationRepository.findOne(id);
    }

    @RequestMapping(value = "Locations/{id}", method = RequestMethod.PUT)
    public Location update(@PathVariable Long id, @RequestBody Location location) {
        Location existingLocation = locationRepository.findOne(id);
        BeanUtils.copyProperties(location, existingLocation);
        
        return locationRepository.saveAndFlush(existingLocation);
    }

    @RequestMapping(value = "Locations/{id}", method = RequestMethod.DELETE)
    public Location delete(@PathVariable Long id) {
        Location existingLocation = locationRepository.findOne(id);
        locationRepository.delete(existingLocation);
        return existingLocation;
    }
}
