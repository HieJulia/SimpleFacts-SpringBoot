package com.boot.controller.rest;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.boot.model.entity.User;
import com.boot.repository.JPA.UserRepository;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "Users", method = RequestMethod.GET)
    public List<User> list() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "Users", method = RequestMethod.POST)
    public User create(@RequestBody User user) {
        return userRepository.saveAndFlush(user);
    }

    @RequestMapping(value = "Users/{id}", method = RequestMethod.GET)
    public User get(@PathVariable Long id) {
        return userRepository.findOne(id);
    }

    @RequestMapping(value = "Users/{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userRepository.findOne(id);
        BeanUtils.copyProperties(user, existingUser);

        return userRepository.saveAndFlush(existingUser);
    }

    @RequestMapping(value = "Users/{id}", method = RequestMethod.DELETE)
    public User delete(@PathVariable Long id) {
        User existingUser = userRepository.findOne(id);
        userRepository.delete(existingUser);
        return existingUser;
    }
}
