package com.app.app.Controller;

import com.app.app.Models.StorageDetails;
import com.app.app.Services.StorageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storage")
public class StorageDetailsController {

    @Autowired
    private StorageDetailsService storageDetailsService;

    @GetMapping
    public List<StorageDetails> getAllStorageDetails() {
        return storageDetailsService.getAllStorageDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageDetails> getStorageDetailsById(@PathVariable Long id) {
        StorageDetails storageDetails = storageDetailsService.getStorageDetailsById(id);
        return ResponseEntity.ok(storageDetails);
    }

    @PostMapping
    public ResponseEntity<StorageDetails> createStorageDetails(@RequestBody StorageDetails storageDetails) {
        StorageDetails newStorageDetails = storageDetailsService.createStorageDetails(storageDetails);
        return ResponseEntity.ok(newStorageDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageDetails> updateStorageDetails(@PathVariable Long id, @RequestBody StorageDetails storageDetails) {
        StorageDetails updatedStorageDetails = storageDetailsService.updateStorageDetails(id, storageDetails);
        return ResponseEntity.ok(updatedStorageDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorageDetails(@PathVariable Long id) {
        storageDetailsService.deleteStorageDetails(id);
        return ResponseEntity.noContent().build();
    }
}

