package com.app.app.Controller;

import com.app.app.Models.ListOfItems;
import com.app.app.Services.ListOfItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ListOfItems> createItem(@RequestBody ListOfItems listOfItems) {
        ListOfItems newItem = listOfItemsService.createItem(listOfItems);
        return ResponseEntity.ok(newItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListOfItems> updateItem(@PathVariable Long id, @RequestBody ListOfItems listOfItems) {
        ListOfItems updatedItem = listOfItemsService.updateItem(id, listOfItems);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        listOfItemsService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
