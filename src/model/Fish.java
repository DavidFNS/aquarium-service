package model;

import enumeration.FishGender;
import jdk.jfr.Description;

import java.util.Date;

public class Fish {
    private Long fishID;
    private FishGender fishGender;
    private Date created_at = new Date();
    @Description(value = "All lifetime of fish per second")
    private Integer lifetime;
    @Description(value = "This field is needed in order to meet 2 fish with each other")
    private Integer fishLocation;
    @Description(value = "This field shows how many fish lived per second")
    private Integer currentLifetime = 0;

    public Fish(Long fishID, FishGender fishGender, Integer lifetime, Integer fishLocation, Integer currentLifetime) {
        this.fishGender = fishGender;
        this.lifetime = lifetime;
        this.fishLocation = fishLocation;
        this.currentLifetime = currentLifetime;
    }

    public Fish() {
    }

    public Fish fishGender(FishGender fishGender) {
        this.fishGender = fishGender;
        return this;
    }

    public Fish fishLifetime(Integer lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public Fish fishLocation(Integer fishLocation) {
        this.fishLocation = fishLocation;
        return this;
    }

    public Fish currentLifetime(Integer currentLifetime) {
        this.currentLifetime = currentLifetime;
        return this;
    }

    public Fish fishID(Long fishID) {
        this.fishID = fishID;
        return this;
    }
    public Long getFishID() {
        return fishID;
    }


    public Integer getCurrentLifetime() {
        return currentLifetime;
    }

    public void setCurrentLifetime(Integer currentLifetime) {
        this.currentLifetime = currentLifetime;
    }

    public FishGender getFishGender() {
        return fishGender;
    }

    public void setFishGender(FishGender fishGender) {
        this.fishGender = fishGender;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getLifetime() {
        return lifetime;
    }

    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }

    public Integer getFishLocation() {
        return fishLocation;
    }

    public void setFishLocation(Integer fishLocation) {
        this.fishLocation = fishLocation;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "fishID=" + fishID +
                ", fishGender=" + fishGender +
                ", created_at=" + created_at +
                ", lifetime=" + lifetime +
                ", fishLocation=" + fishLocation +
                ", currentLifetime=" + currentLifetime +
                '}';
    }
}
