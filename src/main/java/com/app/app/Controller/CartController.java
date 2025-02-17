package com.app.app.Controller;

import com.app.app.Models.Cart;
import com.app.app.Models.Item;
import com.app.app.Models.UserDetails;
import com.app.app.Models.ListOfItems;
import com.app.app.Services.CartService;
import com.app.app.Services.ItemService;

import com.app.app.Services.UserDetailsService;
import com.app.app.Services.ListOfItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ListOfItemsService listOfItemsService;
    @Autowired
    private ItemService itemService;
    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return ResponseEntity.ok(cart);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            if (cart == null) {
                return ResponseEntity.status(404).body("Cart not found for userId: " + userId);
            }
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error retrieving cart: " + e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Map<String, Object> requestBody) {
        Long userId = ((Number) requestBody.get("userId")).longValue();
        Integer quantity = (Integer) requestBody.get("quantity");

        UserDetails user = userDetailsService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setAddress(user.getAddress());

        // Do not add items from the old cart, ensure it's an empty cart
        newCart.setListOfItems(new ArrayList<>()); // Explicitly make sure it's empty

        // Save the new cart to the database
        Cart createdCart = cartService.createCart(newCart);

        return ResponseEntity.status(201).body(createdCart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        Cart updatedCart = cartService.updateCart(id, cart);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
