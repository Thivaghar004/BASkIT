package com.app.app.Services;

import com.app.app.Models.ListOfItems;
import com.app.app.Repository.ListOfItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListOfItemsService {

    @Autowired
    private ListOfItemsRepository listOfItemsRepository;

    public List<ListOfItems> getAllItems() {
        return listOfItemsRepository.findAll();
    }

    public ListOfItems getItemById(Long id) {
        return listOfItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ListOfItems not found with ID: " + id));
    }

    public ListOfItems createItem(ListOfItems listOfItems) {
        return listOfItemsRepository.save(listOfItems);
    }

    public ListOfItems updateItem(Long id, ListOfItems listOfItems) {
        ListOfItems existingItem = getItemById(id);
        existingItem.setCart(listOfItems.getCart());
        existingItem.setItem(listOfItems.getItem());
        return listOfItemsRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        listOfItemsRepository.deleteById(id);
    }
}
