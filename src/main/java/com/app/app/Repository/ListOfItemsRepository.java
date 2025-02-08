package com.app.app.Repository;

import com.app.app.Models.ListOfItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListOfItemsRepository extends JpaRepository<ListOfItems, Long> {
    List<ListOfItems> findByCart_CartId(Long cartId);
    Optional<ListOfItems> findByCart_CartIdAndItem_ItemId(Long cartId, Long itemId);
}