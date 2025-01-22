package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "list_of_items")
public class ListOfItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
