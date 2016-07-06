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
    @Column(name = "MessageID", unique = true, nullable = false)
    private Long MessageID;

    @Column(name = "Name", unique = true, nullable = false, length = 20)
    private String Name;

    @Column(name = "Message", nullable = false, length = 255)
    private String Message;

    @Column(name = "Time", nullable = false, length = 20)
    private Date Time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    @JsonBackReference
    private User user;

    public Message() {
    }

    public Message(Long ID, String Name, String Message, Date Time, User user) {
        this.setID(ID);
        this.setName(Name);
        this.setMessage(Message);
        this.setTime(Time);
        this.setUser(user);
    }
    
    public Long getID() {
        return this.MessageID;
    }

    public final void setID(Long ID) {
        this.MessageID = ID;
    }
    
    public String getName() {
        return this.Name;
    }

    public final void setName(String Name) {
        this.Name = Name;
    }
    
    public String getMessage() {
        return this.Message;
    }

    public final void setMessage(String Message) {
        this.Message = Message;
    }
    
    public Date getTime() {
        return this.Time;
    }

    public final void setTime(Date Time) {
        this.Time = Time;
    }
    
    public User getUser() {
        return this.user;
    }

    public final void setUser(User user) {
        this.user = user;
    }
}
