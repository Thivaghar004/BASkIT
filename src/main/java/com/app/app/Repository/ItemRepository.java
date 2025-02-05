package com.app.app.Repository;

import com.app.app.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i, s.stockAvailability, c.categoryName FROM Item i " +
            "JOIN StorageDetails s ON i.itemId = s.item.itemId " +
            "JOIN StoreDetails st ON s.store.storeId = st.storeId " +
            "JOIN Category c ON i.category.categoryId = c.categoryId " +
            "WHERE st.location.locationId = :locationId")
    List<Object[]> findItemsByLocationId(@Param("locationId") Long locationId);

    @Query("SELECT i, s.stockAvailability, c.categoryName FROM Item i " +
            "JOIN StorageDetails s ON i.itemId = s.item.itemId " +
            "JOIN StoreDetails st ON s.store.storeId = st.storeId " +
            "JOIN Category c ON i.category.categoryId = c.categoryId " +
            "WHERE st.location.locationId = :locationId AND i.category.categoryId = :categoryId")
    List<Object[]> findItemsByLocationAndCategory(@Param("locationId") Long locationId, @Param("categoryId") Long categoryId);


}