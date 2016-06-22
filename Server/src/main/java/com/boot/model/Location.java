package com.boot.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//http://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example-annotation/

@Entity
@Table(name = "Locations")
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    @Column(name="Name", nullable = false)
    private String Name;
    
    @Column(name="Long", nullable = false)
    private Double Long;
    
    @Column(name="Lat", nullable = false)
    private Double Lat;
    
    @OneToMany
    private Set<Event> Events = new HashSet<Event>(0);

    

    public Location() {
    }

    public Location(Long ID, String Name, Double Long, Double Lat) {
        this.setID(ID);
        this.setName(Name);
        this.setLong(Long);
        this.setLat(Lat);
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
    
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Location")
    public Set<Event> getEvents() {
            return this.Events;
    }
}
