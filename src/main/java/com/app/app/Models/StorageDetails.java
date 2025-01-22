package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "storage_details")
public class StorageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private StoreDetails store;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer stockAvailability;
}
