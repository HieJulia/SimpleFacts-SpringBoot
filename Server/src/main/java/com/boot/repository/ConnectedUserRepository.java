package com.boot.repository;

import com.boot.event.StompConnectedEvent;
import com.boot.util.EnvelopeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectedUserRepository {

    private Hashtable<String, String> connectedUsers = new Hashtable<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void add(String sessionID) {
        connectedUsers.put(sessionID, "Guest");
        this.echoUser(sessionID);
    }

    public void remove(String sessionID) {
        connectedUsers.put(sessionID, "");
        this.echoUser(sessionID);
        connectedUsers.remove(sessionID);
    }

    public Hashtable<String, String> getUsers() {
        return connectedUsers;
    }

    public void setUsername(String sessionID, String username) throws JsonProcessingException {
        connectedUsers.put(sessionID, username);
        this.echoUser(sessionID);
    }

    private void echoUser(String sessionID) {
        try {
            Hashtable<String, String> user = new Hashtable<>();
            user.put(sessionID, this.connectedUsers.get(sessionID));
            EnvelopeUtil envelope = new EnvelopeUtil("ConnectedUsers", user);
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setLeaveMutable(true);
            messagingTemplate.convertAndSend("/topic/system", envelope);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ConnectedUserRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
