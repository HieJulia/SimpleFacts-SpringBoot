package com.boot.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Events")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    @Column(name="Name", nullable = false)
    private String Name = "Failed Experiment";
    
    @Column(name="Backstory", nullable = false)
    private String Backstory = "Great men thankfully did not waste any ink on this worthless cretin.";
    
    @Column(name="ImageURL", nullable = false)
    private String ImageURL = "http://sunfieldfarm.org/wp-content/uploads/2014/02/profile-placeholder.png";
    
    @Column(name="ms", nullable = false)
    private Long ms = 0L;
    
    @Column(name="FulfilledLifePurpose", nullable = false)
    private boolean FulfilledLifePurpose = false;
    

    public Event() {
    }

    public Event(Long ID, String Name, String Backstory, String ImageURL, Long ms, boolean FulfilledLifePurpose) {
        this.setID(ID);
        this.setName(Name);
        this.setBackstory(Backstory);
        this.setImageURL(ImageURL);
        this.setms(ms);
        this.setFulfilledLifePurpose(FulfilledLifePurpose);
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

    public String getBackstory() {
        return this.Backstory;
    }

    public final void setBackstory(String Backstory) {
        this.Backstory = Backstory;
    }

    public String getImageURL() {
        return this.ImageURL;
    }

    public final void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }

    public Long getms() {
        return this.ms;
    }

    public final void setms(Long ms) {
        this.ms = ms;
    }

    public boolean getFulfilledLifePurpose() {
        return this.FulfilledLifePurpose;
    }

    public final void setFulfilledLifePurpose(boolean FulfilledLifePurpose) {
        this.FulfilledLifePurpose = FulfilledLifePurpose;
    }
}
