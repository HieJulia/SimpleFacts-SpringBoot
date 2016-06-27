package com.boot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
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
@Table(name = "Events")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "EventID", unique = true, nullable = false)
    private Long EventID;

    @Column(name = "Name", unique = true, nullable = false, length = 20)
    private String Name;

    @Column(name = "Time", nullable = false, length = 20)
    private String Time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LocationID", nullable = false)
    @JsonBackReference
    private Location location;

    public Event() {
    }

    public Event(Long ID, String Name, String Time, Location location) {
        this.setID(ID);
        this.setName(Name);
        this.setTime(Time);
        this.setLocation(location);
    }
    
    public Long getID() {
        return this.EventID;
    }

    public final void setID(Long ID) {
        this.EventID = ID;
    }
    
    public String getName() {
        return this.Name;
    }

    public final void setName(String Name) {
        this.Name = Name;
    }
    
    public String getTime() {
        return this.Time;
    }

    public final void setTime(String Time) {
        this.Time = Time;
    }
    
    public Location getLocation() {
        return this.location;
    }

    public final void setLocation(Location location) {
        this.location = location;
    }
}
