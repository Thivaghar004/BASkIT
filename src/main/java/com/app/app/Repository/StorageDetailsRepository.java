package com.app.app.Repository;

import com.app.app.Models.StorageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageDetailsRepository extends JpaRepository<StorageDetails, Long> {
}
