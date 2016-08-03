package com.boot.repository;

import java.util.Hashtable;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectedUserRepository {
    private Hashtable<String, String> connectedUsers = new Hashtable<>();
    
    public void add(String sessionID) {
        connectedUsers.put(sessionID, "Guest");
    }
    
    public void remove(String sessionID) {
        connectedUsers.remove(sessionID);
    }
    
    public Hashtable<String, String> getUsers() {
        return connectedUsers;
    }
    
    public void setUsername(String sessionID, String username) {
        connectedUsers.put(sessionID, username);
    }
}
