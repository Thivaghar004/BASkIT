package com.app.app.Controller;

import com.app.app.Models.Cart;
import com.app.app.Models.Item;
import com.app.app.Models.ListOfItems;
import com.app.app.Services.CartService;
import com.app.app.Services.ItemService;
import com.app.app.Services.ListOfItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/list-of-items")
public class ListOfItemsController {

    @Autowired
    private ListOfItemsService listOfItemsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;

    // Get all items in a cart
    @GetMapping
    public List<ListOfItems> getAllItems() {
        return listOfItemsService.getAllItems();
    }

    // Get an item by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ListOfItems> getItemById(@PathVariable Long id) {
        ListOfItems listOfItems = listOfItemsService.getItemById(id);
        return ResponseEntity.ok(listOfItems);
    }

    // Add an item to the cart
    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody Map<String, Object> requestBody) {
        try {
            // Validate request body
            if (!requestBody.containsKey("cartId") || !requestBody.containsKey("itemId") || !requestBody.containsKey("quantity")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
            }

            Long cartId = ((Number) requestBody.get("cartId")).longValue();
            Long itemId = ((Number) requestBody.get("itemId")).longValue();
            Integer quantity = (Integer) requestBody.get("quantity");

            if (cartId == null || itemId == null || quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid request data"));
            }

            // Fetch cart and item
            Cart cart = cartService.getCartById(cartId);

            Item item = itemService.getItemById(itemId);

            // Check if the item already exists in the cart
            Optional<ListOfItems> existingItem = listOfItemsService.findByCart_CartIdAndItem_ItemId(cartId, itemId);
            if (existingItem.isPresent()) {
                // If item already exists, update quantity
                ListOfItems existing = existingItem.get();
                existing.setQuantity(existing.getQuantity() + quantity);  // Increase quantity
                return ResponseEntity.ok(listOfItemsService.save(existing));
            }

            // If item doesn't exist in the cart, create a new entry
            ListOfItems newItem = new ListOfItems();
            newItem.setCart(cart);
            newItem.setItem(item);
            newItem.setQuantity(quantity);

            return ResponseEntity.status(201).body(listOfItemsService.save(newItem));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error adding item to cart", "message", e.getMessage()));
        }
    }

    // Update quantity of an item in the cart
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            if (!requestBody.containsKey("cartId") || !requestBody.containsKey("itemId") || !requestBody.containsKey("quantity")) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
            }

            Long cartId = ((Number) requestBody.get("cartId")).longValue();
            Long itemId = ((Number) requestBody.get("itemId")).longValue();
            Integer quantity = (Integer) requestBody.get("quantity");

            if (cartId == null || itemId == null || quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid request data"));
            }

            ListOfItems existingItem = listOfItemsService.getItemById(id);
            Cart cart = cartService.getCartById(cartId);


            Item item = itemService.getItemById(itemId);

            existingItem.setCart(cart);
            existingItem.setItem(item);
            existingItem.setQuantity(quantity);

            return ResponseEntity.ok(listOfItemsService.save(existingItem));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error updating cart item", "message", e.getMessage()));
        }
    }

    // Delete an item from the cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        listOfItemsService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
