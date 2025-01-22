package com.app.app.Services;

import com.app.app.Models.Orders;
import com.app.app.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    public Orders createOrder(Orders orders) {
        return orderRepository.save(orders);
    }

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

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

