package com.app.app.Services;

import com.app.app.DTO.ItemDTO;
import com.app.app.Models.Item;
import com.app.app.Repository.ItemRepository;
import com.app.app.Repository.LocationRepository;
import com.app.app.Repository.StorageDetailsRepository;
import com.app.app.Repository.StoreDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private StorageDetailsRepository storageDetailsRepository;

    @Autowired
    private StoreDetailsRepository storeRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item item) {
        Item existingItem = getItemById(id);
        existingItem.setItemName(item.getItemName());
        existingItem.setPrice(item.getPrice());
        existingItem.setCategory(item.getCategory());
        existingItem.setOffer(item.getOffer());
        return itemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<ItemDTO> getItemsByLocation(Long locationId) {
        List<Object[]> results = itemRepository.findItemsByLocationId(locationId);
        return mapToDTO(results);
    }

    public List<ItemDTO> getItemsByLocationAndCategory(Long locationId, Long categoryId) {
        List<Object[]> results = itemRepository.findItemsByLocationAndCategory(locationId, categoryId);
        return mapToDTO(results);
    }

    private List<ItemDTO> mapToDTO(List<Object[]> results) {
        List<ItemDTO> items = new ArrayList<>();
        for (Object[] result : results) {
            Item item = (Item) result[0];
            Integer stockAvailability = (Integer) result[1]; // Extract stockAvailability
            String categoryName = (String) result[2];
            ItemDTO dto = new ItemDTO(
                    item.getItemId(),
                    item.getItemName(),
                    item.getPrice(),
                    stockAvailability,
                    categoryName
            );
            items.add(dto);
        }
        return items;
    }
}

