package com.boot.controller.rest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.boot.model.entity.Message;
import com.boot.repository.MessageRepository;

@RestController
@RequestMapping("api/v1/")
public class MessageController {
    
    @Autowired
    private MessageRepository messageRepository;

    @RequestMapping(value = "Messages", method = RequestMethod.GET)
    public List<Message> list() {
        return messageRepository.findAll();
    }

    @RequestMapping(value = "Messages", method = RequestMethod.POST)
    public Message create(@RequestBody Message message) {
        return messageRepository.saveAndFlush(message);
    }

    @RequestMapping(value = "Messages/{id}", method = RequestMethod.GET)
    public Message get(@PathVariable Long id) {
        return messageRepository.findOne(id);
    }

    @RequestMapping(value = "Messages/{id}", method = RequestMethod.PUT)
    public Message update(@PathVariable Long id, @RequestBody Message message) {
        Message existingMessage = messageRepository.findOne(id);
        BeanUtils.copyProperties(message, existingMessage);
        
        return messageRepository.saveAndFlush(existingMessage);
    }

    @RequestMapping(value = "Messages/{id}", method = RequestMethod.DELETE)
    public Message delete(@PathVariable Long id) {
        Message existingMessage = messageRepository.findOne(id);
        messageRepository.delete(existingMessage);
        return existingMessage;
    }
}
