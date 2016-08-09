package com.boot.event;

import com.boot.repository.ConnectedUserRepository;
import com.boot.util.EnvelopeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class StompConnectedEvent implements ApplicationListener<SessionConnectedEvent> {

    @Autowired
    private ConnectedUserRepository connectedUsers;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        try {
            EnvelopeUtil envelope = new EnvelopeUtil("ConnectedUsers", connectedUsers.getUsers());

            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(sha.getSessionId());
            headerAccessor.setLeaveMutable(true);

            messagingTemplate.convertAndSendToUser(sha.getSessionId(), "/queue/something", envelope, headerAccessor.getMessageHeaders());
        } catch (JsonProcessingException ex) {
            Logger.getLogger(StompConnectedEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Connect event [sessionId: " + sha.getSessionId() + "]");
        connectedUsers.add(sha.getSessionId());

        System.out.println("All currently connected users:");

        Set<String> keys = connectedUsers.getUsers().keySet();
        Iterator<String> itr = keys.iterator();

        while (itr.hasNext()) {
            String str = itr.next();

            System.out.println("  SessionID: \"" + str + "\" & Name: \"" + connectedUsers.getUsers().get(str) + "\"");
        }
    }
}
