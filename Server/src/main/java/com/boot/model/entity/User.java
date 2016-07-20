package com.boot.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example-annotation/
@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userID", unique = true, nullable = false)
    private Long userID;
    
    @Column(name = "name", unique = true, nullable = false, length = 20)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonManagedReference
    private Set<Message> messages = new HashSet<Message>(0);

    public User() {
    }

    public User(Long ID, String Name) {
        this.setID(ID);
        this.setName(Name);
    }

    public User(Long ID, String Name, Set<Message> Messages) {
        this.setID(ID);
        this.setMessages(Messages);
    }
    
    public Long getID() {
        return this.userID;
    }

    public final void setID(Long ID) {
        this.userID = ID;
    }
    
    public String getName() {
        return this.name;
    }

    public final void setName(String Name) {
        this.name = Name;
    }
    
    public Set<Message> getMessages() {
        return this.messages;
    }

    public final void setMessages(Set<Message> Messages) {
        this.messages = Messages;
    }
}
