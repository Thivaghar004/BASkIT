package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long phoneNo;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String address;
}