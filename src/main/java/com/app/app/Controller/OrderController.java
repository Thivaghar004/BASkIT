package com.app.app.Controller;

import com.app.app.Models.Cart;
import com.app.app.Models.Orders;
import com.app.app.Models.Payment;
import com.app.app.Models.UserDetails;
import com.app.app.Services.CartService;
import com.app.app.Services.OrderService;
import com.app.app.Services.PaymentService;
import com.app.app.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserDetailsService userDetailsService;
    private final CartService cartService;

    public OrderController(OrderService orderService, PaymentService paymentService,
                           UserDetailsService userDetailsService, CartService cartService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userDetailsService = userDetailsService;
        this.cartService = cartService;
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders orders = orderService.getOrderById(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> orderData) {
        System.out.println("📦 Received Order Data: " + orderData);  // ✅ Log incoming request

        try {
            Long userId = Long.valueOf(orderData.get("userId").toString());
            Long cartId = Long.valueOf(orderData.get("cartId").toString());
            String paymentMethod = orderData.get("paymentMethod").toString();
            Double totalAmount = Double.valueOf(orderData.get("totalAmount").toString());

            System.out.println("🔍 Fetching user with ID: " + userId);
            System.out.println("🔍 Fetching cart with ID: " + cartId);

            UserDetails user = userDetailsService.getUserById(userId);
            Cart cart = cartService.getCartById(cartId);

            if (user == null) {
                System.out.println("❌ User not found!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!");
            }

            if (cart == null) {
                System.out.println("❌ Cart not found!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart not found!");
            }

            System.out.println("✅ User and Cart fetched successfully!");

            // ✅ Create Payment Entity
            Payment payment = new Payment();
            payment.setOrderAmount(totalAmount);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus(paymentMethod.equals("cod") ? "Pending" : "Paid");
            payment.setPaymentDate(LocalDateTime.now());

            Payment savedPayment = paymentService.createPayment(payment);
            System.out.println("✅ Payment saved successfully with ID: " + savedPayment.getPaymentId());

            // ✅ Create Order Entity
            Orders order = new Orders();
            order.setUser(user);
            order.setCart(cart);
            order.setPayment(savedPayment);  // ✅ Link payment to the order
            order.setName(orderData.get("name").toString());
            order.setPhoneNumber(orderData.get("phoneNumber").toString());
            order.setAddress(orderData.get("address").toString());
            order.setTotalAmount(totalAmount);
            order.setOrderStatus("Pending");

            Orders savedOrder = orderService.createOrder(order);
            System.out.println("✅ Order saved successfully with ID: " + savedOrder.getOrderId());

            // ✅ Update Payment Entity with Order
            savedPayment.setOrder(savedOrder);
            paymentService.updatePayment(savedPayment.getPaymentId(), savedPayment);

            // ✅ Clear the Cart (if COD)
            if (paymentMethod.equals("cod")) {
                cartService.deleteCart(cartId);
                System.out.println("🛒 Cart cleared after order placement.");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
        } catch (Exception e) {
            System.out.println("❌ Order Placement Error: " + e.getMessage());  // ✅ Debugging log
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to place order: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody Orders orders) {
        Orders updatedOrders = orderService.updateOrder(id, orders);
        return ResponseEntity.ok(updatedOrders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}