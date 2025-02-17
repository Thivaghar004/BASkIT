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

        List<Cart> carts = cartRepository.findByUser_UserId(userId);

        if (carts.isEmpty()) {
            return null; // No cart found for user
        }

        return carts.get(0);
    }

    public Cart createCart(Cart cart) {
        UserDetails user = userDetailsService.getUserById(cart.getUser().getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + cart.getUser().getUserId());
        }
        List<Cart> existingCarts = cartRepository.findByUser_UserId(user.getUserId());
        if (!existingCarts.isEmpty()) {
            return existingCarts.get(0); // Return the first cart if it exists
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
        Cart cart = getCartById(id);
        cart.setActive(false);  // Instead of deleting, mark as inactive
        cartRepository.save(cart);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}

