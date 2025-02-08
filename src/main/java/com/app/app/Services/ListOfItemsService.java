package com.app.app.Services;

import com.app.app.Models.Cart;
import com.app.app.Models.Item;
import com.app.app.Models.ListOfItems;
import com.app.app.Repository.CartRepository;
import com.app.app.Repository.ItemRepository;
import com.app.app.Repository.ListOfItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListOfItemsService {

    @Autowired
    private ListOfItemsRepository listOfItemsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemRepository itemRepository;

    public List<ListOfItems> getAllItems() {
        return listOfItemsRepository.findAll();
    }

    public ListOfItems getItemById(Long id) {
        return listOfItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ListOfItems not found with ID: " + id));
    }

    public List<ListOfItems> getItemsByCartId(Long cartId) {
        return listOfItemsRepository.findByCart_CartId(cartId);
    }

    public ListOfItems createItem(ListOfItems listOfItems) {
        Optional<ListOfItems> existingItem = listOfItemsRepository.findByCart_CartIdAndItem_ItemId(
                listOfItems.getCart().getCartId(),
                listOfItems.getItem().getItemId()
        );

        if (existingItem.isPresent()) {
            // If item already exists in cart, update quantity
            ListOfItems existing = existingItem.get();
            existing.setQuantity(existing.getQuantity() + listOfItems.getQuantity());  // Increase quantity
            return listOfItemsRepository.save(existing);
        }

        // Otherwise, save as a new entry
        return listOfItemsRepository.save(listOfItems);
    }


    public ListOfItems updateItem(Long id, Long cartId, Long itemId, Integer quantity) {
        ListOfItems existingItem = listOfItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setCart(cart);
        existingItem.setItem(item);
        existingItem.setQuantity(quantity);

        return listOfItemsRepository.save(existingItem);
    }
    public ListOfItems addItemToCart(Long cartId, Long itemId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Optional<ListOfItems> existingItem = listOfItemsRepository.findByCart_CartIdAndItem_ItemId(cartId, itemId);

        if (existingItem.isPresent()) {
            ListOfItems existing = existingItem.get();
            existing.setQuantity(existing.getQuantity() + quantity);
            return listOfItemsRepository.save(existing);
        }

        ListOfItems newItem = new ListOfItems();
        newItem.setCart(cart);
        newItem.setItem(item);
        newItem.setQuantity(quantity);

        return listOfItemsRepository.save(newItem);
    }


    public void deleteItem(Long id) {
        listOfItemsRepository.deleteById(id);
    }
}
