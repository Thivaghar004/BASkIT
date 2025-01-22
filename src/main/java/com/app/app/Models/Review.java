package com.app.app.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer ratings;
    private String comments;
    private LocalDateTime reviewDate;
}