package com.boot.model;

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
@Table(name = "Locations")
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "LocationID", unique = true, nullable = false)
    private Long LocationID;
    
    @Column(name = "Name", unique = true, nullable = false, length = 20)
    private String Name;

    @Column(name = "Long", nullable = false, length = 20)
    private Double Long;
    
    @Column(name = "Lat", nullable = false, length = 20)
    private Double Lat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    @JsonManagedReference
    private Set<Event> events = new HashSet<Event>(0);

    public Location() {
    }

    public Location(Long ID, String Name, Double Long, Double Lat) {
        this.setID(ID);
        this.setName(Name);
        this.setLong(Long);
        this.setLat(Lat);
    }

    public Location(Long ID, String Name, Double Long, Double Lat, Set<Event> Events) {
        this.setID(ID);
        this.setName(Name);
        this.setLong(Long);
        this.setLat(Lat);
        this.setEvents(Events);
    }
    
    public Long getID() {
        return this.LocationID;
    }

    public final void setID(Long ID) {
        this.LocationID = ID;
    }
    
    public String getName() {
        return this.Name;
    }

    public final void setName(String Name) {
        this.Name = Name;
    }
    
    public Double getLong() {
        return this.Long;
    }

    public final void setLong(Double Long) {
        this.Long = Long;
    }

    public Double getLat() {
        return this.Lat;
    }

    public final void setLat(Double Lat) {
        this.Lat = Lat;
    }
    
    public Set<Event> getEvents() {
        return this.events;
    }

    public final void setEvents(Set<Event> Events) {
        this.events = Events;
    }
}
