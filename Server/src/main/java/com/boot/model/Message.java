package com.boot.model;

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

@Entity
@Table(name = "Messages")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "messageID", unique = true, nullable = false)
    private Long messageID;

    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @Column(name = "message", nullable = false, length = 255)
    private String message;

    //@Column(name = "time", nullable = false, length = 20)
    //private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false)
    @JsonBackReference
    private User user;

    public Message() {
    }

    public Message(Long ID, String Name, String Message, User user) {
        this.setID(ID);
        this.setName(Name);
        this.setMessage(Message);
        //this.setTime(Time);
        this.setUser(user);
    }
    
    public Long getID() {
        return this.messageID;
    }

    public final void setID(Long ID) {
        this.messageID = ID;
    }
    
    public String getName() {
        return this.name;
    }

    public final void setName(String Name) {
        this.name = Name;
    }
    
    public String getMessage() {
        return this.message;
    }

    public final void setMessage(String Message) {
        this.message = Message;
    }
    /*
    public Date getTime() {
        return this.time;
    }

    public final void setTime(Date Time) {
        this.time = Time;
    }
    */
    public User getUser() {
        return this.user;
    }

    public final void setUser(User user) {
        this.user = user;
    }
}
