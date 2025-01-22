package com.app.app.Controller;

import com.app.app.Models.StoreDetails;
import com.app.app.Services.StoreDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreDetailsController {

    @Autowired
    private StoreDetailsService storeDetailsService;

    @GetMapping
    public List<StoreDetails> getAllStores() {
        return storeDetailsService.getAllStores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDetails> getStoreById(@PathVariable Long id) {
        StoreDetails store = storeDetailsService.getStoreById(id);
        return ResponseEntity.ok(store);
    }

    @PostMapping
    public ResponseEntity<StoreDetails> createStore(@RequestBody StoreDetails storeDetails) {
        StoreDetails newStore = storeDetailsService.createStore(storeDetails);
        return ResponseEntity.ok(newStore);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDetails> updateStore(@PathVariable Long id, @RequestBody StoreDetails storeDetails) {
        StoreDetails updatedStore = storeDetailsService.updateStore(id, storeDetails);
        return ResponseEntity.ok(updatedStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeDetailsService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}

