package com.app.app.Repository;

import com.app.app.Models.StoreDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDetailsRepository extends JpaRepository<StoreDetails, Long> {
}