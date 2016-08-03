package com.boot.event;

import com.boot.repository.ConnectedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
    
    @Autowired
    private ConnectedUserRepository connectedUsers;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        
        System.out.println("Disconnect event [sessionId: " + sha.getSessionId() + "]");
        connectedUsers.remove(sha.getSessionId());
    }
}
