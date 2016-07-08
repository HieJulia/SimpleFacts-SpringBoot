package com.boot.controller;

import com.boot.model.Message;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.boot.model.User;
import com.boot.util.HibernateUtil;
import org.hibernate.Session;
import com.boot.repository.UserRepository;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "Create", method = RequestMethod.GET)
    public void create() throws ParseException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String number = Integer.toString((int) (Math.random() * 100));

        session.beginTransaction();

        User user = new User();

        user.setName("bob " + number);

        session.save(user);

        
        Message message = new Message();
        message.setName("oinks " + number);
        message.setMessage("Oiiiiiiiiiiiiiiiiiiiiiiinks");

        //Date in = new Date();
        //LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        //Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

        //message.setTime(out);
        message.setUser(user);

        user.getMessages().add(message);

        session.save(message);

        session.getTransaction().commit();
        session.close();
        System.out.println("Done");
    }

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
