package com.boot;

import com.boot.model.ChatMessage;
import com.boot.model.ChatName;
import com.boot.model.entity.Message;
import com.boot.model.entity.User;
import com.boot.repository.UserRepository;
import com.boot.util.EnvelopeUtil;
import com.boot.util.HibernateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
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
            ChatMessage chatMessage = mapper.readValue(envelope.getPayload(), ChatMessage.class);
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            User user = userRepository.findFirstByName(chatMessage.getUsername());

            Message message = new Message();
            message.setMessage(chatMessage.getMessage());
            message.setUser(user);

            user.getMessages().add(message);

            session.save(message);

            session.getTransaction().commit();
            session.close();
            
        } else if (envelope.getType().toUpperCase().equals("CHATNAME")) {
            ChatName chatName = mapper.readValue(envelope.getPayload(), ChatName.class);
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            User user = new User();
            user.setName(chatName.getUsername());
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } else {
            System.out.println("REJECTED MESSAGE");
        }
        
        
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
