package com.boot.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Events")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Column(name = "Name", nullable = false)
    private String Name;

    @Column(name = "Time", nullable = false)
    private String Time;

    private Location location;

    public Event() {
    }

    public Event(Long ID, String Name, String Time, Location location) {
        this.setID(ID);
        this.setName(Name);
        this.setTime(Time);
        this.location = location;
    }

    public Long getID() {
        return this.ID;
    }

    public final void setID(Long ID) {
        this.ID = ID;
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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getRecordId() {
        return this.ID;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID", nullable = false)
    public Location getLocation() {
            return this.location;
    }

    public void setStock(Location location) {
            this.location = location;
    }
}
