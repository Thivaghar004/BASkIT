package com.app.app.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    private Double minimumOrderAmount;
    private LocalDate validFrom;
    private LocalDate validTill;
    private Double discountPercentage;
}
