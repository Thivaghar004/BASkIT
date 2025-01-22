package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "store_details")
public class StoreDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeName;
    private Long storeContactInfo;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getStoreContactInfo() {
        return storeContactInfo;
    }

    public void setStoreContactInfo(Long storeContactInfo) {
        this.storeContactInfo = storeContactInfo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}