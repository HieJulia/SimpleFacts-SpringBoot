package com.boot.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * This method is responsible for handling the objects that are being 
 * sent to/retrieved from the database.
 * 
 * It has a ManyToOne relationship with com.boot.model.entity.User
 * 
 * Credit to: http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example-annotation/
 */
@Entity
@Table(name = "Messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "messageID", unique = true, nullable = false)
    private Long messageID;

    @Column(name = "message", nullable = false, length = 255)
    private String message;

    @Column(name = "fingerprint", nullable = false, length = 255)
    private String fingerprint;

    @Column(name = "time", nullable = false, length = 20)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", nullable = false)
    @JsonBackReference
    private User user;
    
    @Transient
    private String username;

    public Message() {
    }

    public Message(Long ID, String Message, String Fingerprint, Date Time, User user) {
        this.setID(ID);
        this.setMessage(Message);
        this.setFingerprint(Fingerprint);
        this.setTime(Time);
        this.setUser(user);
    }
    
    public Long getID() {
        return this.messageID;
    }

    public final void setID(Long ID) {
        this.messageID = ID;
    }
    
    public String getMessage() {
        return this.message;
    }

    public final void setMessage(String Message) {
        this.message = Message;
    }
    
    public String getFingerprint() {
        return this.fingerprint;
    }

    public final void setFingerprint(String Fingerprint) {
        this.fingerprint = Fingerprint;
    }
    
    public Date getTime() {
        return this.time;
    }

    public final void setTime(Date Time) {
        this.time = Time;
    }
    
    public User getUser() {
        return this.user;
    }

    public final void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Message [user=" + this.user + ", message=" + this.message + ", time=" + this.time + ", fingerprint=" + this.fingerprint + "]";
    }
}
