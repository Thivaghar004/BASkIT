package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    private Long locationId;

    private String locationName;
}