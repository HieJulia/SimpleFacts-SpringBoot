package com.boot;

import com.boot.model.entity.Message;
import com.boot.model.entity.User;
import com.boot.repository.UserRepository;
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
            Message message = mapper.readValue(envelope.getPayload(), Message.class);
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            User user = userRepository.findFirstByName(message.getUsername());
            
            message.setUser(user);

            user.getMessages().add(message);

            session.save(message);

            session.getTransaction().commit();
            session.close();
            
            System.out.println("Saved Message");
        } else if (envelope.getType().toUpperCase().equals("CHATNAME")) {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            User user = mapper.readValue(envelope.getPayload(), User.class);
            session.save(user);
            session.getTransaction().commit();
            session.close();
            
            System.out.println("Registered Username");
        } else {
            System.out.println("REJECTED MESSAGE");
        }
        
        
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
