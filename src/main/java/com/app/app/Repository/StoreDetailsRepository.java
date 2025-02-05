package com.app.app.Repository;

import com.app.app.Models.StoreDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDetailsRepository extends JpaRepository<StoreDetails, Long> {
    @Query("SELECT st.storeId FROM StoreDetails st WHERE st.location.locationId = :locationId")
    List<Long> findStoreIdsByLocationId(@Param("locationId") Long locationId);
}