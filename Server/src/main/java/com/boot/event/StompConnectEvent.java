package com.boot.event;

import com.boot.repository.ConnectedUserRepository;
import com.mysql.jdbc.log.Log;
import java.util.Hashtable;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {
    
    @Autowired
    ConnectedUserRepository connectedUsers;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        
        System.out.println("Connect event [sessionId: " + sha.getSessionId() + "]");
        connectedUsers.add(sha.getSessionId());
    }
}
