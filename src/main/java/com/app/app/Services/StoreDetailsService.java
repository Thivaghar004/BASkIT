package com.app.app.Services;

import com.app.app.Models.StoreDetails;
import com.app.app.Repository.StoreDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreDetailsService {

    @Autowired
    private StoreDetailsRepository storeDetailsRepository;

    public List<StoreDetails> getAllStores() {
        return storeDetailsRepository.findAll();
    }

    public StoreDetails getStoreById(Long id) {
        return storeDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store not found with ID: " + id));
    }

    public StoreDetails createStore(StoreDetails storeDetails) {
        return storeDetailsRepository.save(storeDetails);
    }

    public StoreDetails updateStore(Long id, StoreDetails storeDetails) {
        StoreDetails existingStore = getStoreById(id);
        existingStore.setStoreName(storeDetails.getStoreName());
        existingStore.setStoreContactInfo(storeDetails.getStoreContactInfo());
        existingStore.setLocation(storeDetails.getLocation());
        return storeDetailsRepository.save(existingStore);
    }

    public void deleteStore(Long id) {
        storeDetailsRepository.deleteById(id);
    }
}

