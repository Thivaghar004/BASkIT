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
}