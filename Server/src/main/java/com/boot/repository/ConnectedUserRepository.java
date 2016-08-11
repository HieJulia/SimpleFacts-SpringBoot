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

    /**
     * Record a new session in the repository.
     * The user of this session will default to Guest.
     * 
     * Implements: {@link #echoUser}
     * 
     * @param sessionID 
     */
    public void add(String sessionID) {
        connectedUsers.put(sessionID, "Guest");
        this.echoUser(sessionID);
    }

    /**
     * Remove a session from the repository
     * 
     * Implements: {@link #echoUser}
     * 
     * @param sessionID 
     */
    public void remove(String sessionID) {
        connectedUsers.put(sessionID, "");
        this.echoUser(sessionID);
        connectedUsers.remove(sessionID);
    }

    /**
     * returns a list of all connected users
     * 
     * @return connectedUsers
     */
    public Hashtable<String, String> getUsers() {
        return connectedUsers;
    }

    /**
     * Sets a username to a specific session id
     * 
     * Implements: {@link #echoUser}
     * 
     * @param sessionID
     * @param username
     */
    public void setUsername(String sessionID, String username) {
        connectedUsers.put(sessionID, username);
        this.echoUser(sessionID);
    }

    /**
     * Echos the selected sessionIDs recent name changes to everyone currently
     * connected via websockets.
     * 
     * @param sessionID 
     */
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
