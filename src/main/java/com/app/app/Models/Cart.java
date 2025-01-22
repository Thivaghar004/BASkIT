package com.app.app.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;

    private Integer quantity;
    private LocalDateTime lastUpdatedDate;
    private String address;

    @OneToMany(mappedBy = "cart")
    private List<ListOfItems> listOfItems;
}