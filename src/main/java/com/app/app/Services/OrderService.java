package com.app.app.Services;

import com.app.app.Models.Cart;
import com.app.app.Models.Orders;
import com.app.app.Models.UserDetails;
import com.app.app.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserDetailsService userDetailsService;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserDetailsService userDetailsService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.userDetailsService = userDetailsService;
        this.cartService = cartService;

        System.out.println("✅ OrderService Initialized!");
        System.out.println("✅ Is userDetailsService NULL? " + (this.userDetailsService == null));
        System.out.println("✅ Is cartService NULL? " + (this.cartService == null));
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    @Transactional
    public Orders createOrder(Orders order) {
        System.out.println("📦 Received Order Data: " + order);

        // ✅ Validate input
        if (order.getUser() == null || order.getUser().getUserId() == null) {
            throw new RuntimeException("⚠️ Order's user field is NULL or has no ID!");
        }
        if (order.getCart() == null || order.getCart().getCartId() == null) {
            throw new RuntimeException("⚠️ Order's cart field is NULL or has no ID!");
        }

        // ✅ Fetch existing user and cart from database
        UserDetails user = userDetailsService.getUserById(order.getUser().getUserId());
        Cart oldCart = cartService.getCartById(order.getCart().getCartId());

        if (user == null || oldCart == null) {
            throw new RuntimeException("❌ User or Cart not found!");
        }

        // ✅ Set existing cart and user
        order.setUser(user);
        order.setCart(oldCart);
        order.setOrderStatus("PLACED");

        // ✅ Save the order
        Orders savedOrder = orderRepository.save(order);
        System.out.println("✅ Order saved successfully with ID: " + savedOrder.getOrderId());

        // ✅ Create a new cart for the user (WITHOUT deleting the old cart)
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setListOfItems(new ArrayList<>()); // Empty cart
        newCart.setLastUpdatedDate(LocalDateTime.now());

        // ✅ Save the new cart
        Cart createdCart = cartService.saveCart(newCart);
        System.out.println("🛒 New cart created with ID: " + createdCart.getCartId());

        return savedOrder;
    }



    @Transactional
    public Orders updateOrder(Long id, Orders orders) {
        Orders existingOrder = getOrderById(id);
        existingOrder.setUser(orders.getUser());
        existingOrder.setCart(orders.getCart());
        existingOrder.setOrderStatus(orders.getOrderStatus());
        existingOrder.setPayment(orders.getPayment());
        existingOrder.setEstimatedDeliveryTime(orders.getEstimatedDeliveryTime());
        existingOrder.setDeliveryAgent(orders.getDeliveryAgent());
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
