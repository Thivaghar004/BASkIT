package com.app.app.Controller;

import com.app.app.Models.ListOfItems;
import com.app.app.Services.ListOfItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/list-of-items")
public class ListOfItemsController {

    @Autowired
    private ListOfItemsService listOfItemsService;

    @GetMapping
    public List<ListOfItems> getAllItems() {
        return listOfItemsService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListOfItems> getItemById(@PathVariable Long id) {
        ListOfItems listOfItems = listOfItemsService.getItemById(id);
        return ResponseEntity.ok(listOfItems);
    }

    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody Map<String, Object> requestBody) {
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

            ListOfItems newItem = listOfItemsService.addItemToCart(cartId, itemId, quantity);

            return ResponseEntity.ok(newItem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error adding item to cart", "message", e.getMessage()));
        }
    }

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

            ListOfItems updatedItem = listOfItemsService.updateItem(id, cartId, itemId, quantity);

            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error updating cart item", "message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        listOfItemsService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
