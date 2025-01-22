package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    private Long locationId;

    private String locationName;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}