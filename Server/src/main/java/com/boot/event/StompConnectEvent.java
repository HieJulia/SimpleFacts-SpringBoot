package com.boot.event;

import com.boot.repository.ConnectedUserRepository;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private ConnectedUserRepository connectedUsers;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        System.out.println("Connect event [sessionId: " + sha.getSessionId() + "]");
        connectedUsers.add(sha.getSessionId());

        System.out.println("All currently connected users:");

        Set<String> keys = connectedUsers.getUsers().keySet();
        Iterator<String> itr = keys.iterator();

        while (itr.hasNext()) {
            String str = itr.next();

            System.out.println("  SessionID: \"" + str + "\" & Name: \"" + connectedUsers.getUsers().get(str) + "\"");
        }
        
        messagingTemplate.convertAndSend("/topic/system.name", connectedUsers.getUsers());
    }
}
