package com.app.app.Repository;

import com.app.app.Models.ListOfItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListOfItemsRepository extends JpaRepository<ListOfItems, Long> {
}