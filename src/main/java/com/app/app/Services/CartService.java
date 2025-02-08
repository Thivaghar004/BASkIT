package com.app.app.Services;

import com.app.app.Models.Cart;
import com.app.app.Models.UserDetails;
import com.app.app.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + id));
    }
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUser_UserId(userId);
    }

    public Cart createCart(Cart cart) {
        UserDetails user = userDetailsService.getUserById(cart.getUser().getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + cart.getUser().getUserId());
        }
        Cart existingCart = cartRepository.findByUser_UserId(user.getUserId());
        if (existingCart != null) {
            return existingCart; // Return existing cart instead of creating a new one
        }
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long id, Cart cart) {
        Cart existingCart = getCartById(id);
        existingCart.setUser(cart.getUser());
        existingCart.setLastUpdatedDate(cart.getLastUpdatedDate());
        existingCart.setAddress(cart.getAddress());
        return cartRepository.save(existingCart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}

