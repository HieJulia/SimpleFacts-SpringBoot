package com.boot.model;

import java.util.Date;

public class ChatMessage {

    private String username;
    private String message;
    private String fingerprint;
    private Date time;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }

    public void setFingerprint(String Fingerprint) {
        this.fingerprint = Fingerprint;
    }
    
    public Date getTime() {
        return this.time;
    }

    public final void setTime(Date Time) {
        this.time = Time;
    }

    @Override
    public String toString() {
        return "ChatMessage [user=" + this.username + ", message=" + this.message + ", fingerprint=" + this.fingerprint + "]";
    }
}
