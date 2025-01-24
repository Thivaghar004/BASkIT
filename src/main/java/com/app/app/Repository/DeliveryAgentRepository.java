package com.app.app.Repository;

import com.app.app.Models.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {
}