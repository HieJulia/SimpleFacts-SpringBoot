package com.boot.event;

import com.boot.repository.ConnectedUserRepository;
import com.boot.util.EnvelopeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
    
    @Autowired
    private ConnectedUserRepository connectedUsers;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        
        System.out.println("Disconnect event [sessionId: " + sha.getSessionId() + "]");
        connectedUsers.remove(sha.getSessionId());
        
        try {
            EnvelopeUtil envelope = new EnvelopeUtil("ConnectedUsers", connectedUsers.getUsers());
            messagingTemplate.convertAndSend("/topic/system", envelope);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(StompConnectedEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
