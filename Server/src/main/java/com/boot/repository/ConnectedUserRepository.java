package com.boot.repository;

import com.boot.model.entity.User;
import java.util.Hashtable;
import java.util.List;

public class ConnectedUserRepository {
    private Hashtable<String, String> connectedUsers = new Hashtable<>();
    
    public void add(String sessionID) {
        connectedUsers.put(sessionID, "Guest");
    }
    
//    public List<User> getUsers() {
//        
//    }
    
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
