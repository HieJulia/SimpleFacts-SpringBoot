package com.boot;

import com.boot.model.entity.Message;
import com.boot.model.entity.User;
import com.boot.repository.JPA.UserRepository;
import com.boot.util.EnvelopeUtil;
import com.boot.util.HibernateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    private UserRepository userRepository;

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String receivedMessage) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        EnvelopeUtil envelope = mapper.readValue(receivedMessage, EnvelopeUtil.class);

        System.out.println("Received <" + receivedMessage + ">");

        if (envelope.getType().toUpperCase().equals("CHATMESSAGE")) {
            System.out.println("Attempting to save message");
            
            Message message = mapper.readValue(envelope.getPayload(), Message.class);

            User user = userRepository.findFirstByName(message.getUsername());
            
            if (user != null) {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                
                message.setUser(user);

                user.getMessages().add(message);

                session.save(message);

                session.getTransaction().commit();
                session.close();

                System.out.println("Saved Message");
            } else {
                System.out.println("User not found in database");
                System.out.println("Sending message to dead letter queue...");
            }
        } else if (envelope.getType().toUpperCase().equals("CHATNAME")) {
            System.out.println("Attempting to register user");
            
            User chatName = mapper.readValue(envelope.getPayload(), User.class);
            User user = userRepository.findFirstByName(chatName.getName());

            if (user == null) {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();

                session.save(chatName);
                session.getTransaction().commit();
                session.close();
                
                System.out.println("Registered User");
            } else {
                System.out.println("User already registered");
            }
        } else {
            System.out.println("REJECTED MESSAGE");
        }

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
