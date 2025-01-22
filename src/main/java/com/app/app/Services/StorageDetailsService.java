package com.app.app.Services;

import com.app.app.Models.StorageDetails;
import com.app.app.Repository.StorageDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageDetailsService {

    @Autowired
    private StorageDetailsRepository storageDetailsRepository;

    public List<StorageDetails> getAllStorageDetails() {
        return storageDetailsRepository.findAll();
    }

    public StorageDetails getStorageDetailsById(Long id) {
        return storageDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StorageDetails not found with ID: " + id));
    }

    public StorageDetails createStorageDetails(StorageDetails storageDetails) {
        return storageDetailsRepository.save(storageDetails);
    }

    public StorageDetails updateStorageDetails(Long id, StorageDetails storageDetails) {
        StorageDetails existingStorageDetails = getStorageDetailsById(id);
        existingStorageDetails.setStore(storageDetails.getStore());
        existingStorageDetails.setItem(storageDetails.getItem());
        existingStorageDetails.setStockAvailability(storageDetails.getStockAvailability());
        return storageDetailsRepository.save(existingStorageDetails);
    }

    public void deleteStorageDetails(Long id) {
        storageDetailsRepository.deleteById(id);
    }
}

