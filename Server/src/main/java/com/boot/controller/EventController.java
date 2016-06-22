package com.boot.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.boot.model.Event;
import com.boot.repository.EventRepository;

@RestController
@RequestMapping("api/v1/")
public class EventController {
    
    @Autowired
    private EventRepository eventRepository;

    @RequestMapping(value = "Events", method = RequestMethod.GET)
    public List<Event> list() {
        return eventRepository.findAll();
    }

    @RequestMapping(value = "Events", method = RequestMethod.POST)
    public Event create(@RequestBody Event character) {
        return eventRepository.saveAndFlush(character);
    }

    @RequestMapping(value = "Events/{id}", method = RequestMethod.GET)
    public Event get(@PathVariable Long id) {
        return eventRepository.findOne(id);
    }

    @RequestMapping(value = "Events/{id}", method = RequestMethod.PUT)
    public Event update(@PathVariable Long id, @RequestBody Event event) {
        Event existingEvent = eventRepository.findOne(id);
        BeanUtils.copyProperties(event, existingEvent);
        
        return eventRepository.saveAndFlush(existingEvent);
    }

    @RequestMapping(value = "Events/{id}", method = RequestMethod.DELETE)
    public Event delete(@PathVariable Long id) {
        Event existingEvent = eventRepository.findOne(id);
        eventRepository.delete(existingEvent);
        return existingEvent;
    }
}
