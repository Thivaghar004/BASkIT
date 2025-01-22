package com.app.app.Services;

import com.app.app.Models.DeliveryAgent;
import com.app.app.Repository.DeliveryAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAgentService {

    @Autowired
    private DeliveryAgentRepository deliveryAgentRepository;

    public List<DeliveryAgent> getAllDeliveryAgents() {
        return deliveryAgentRepository.findAll();
    }

    public DeliveryAgent getDeliveryAgentById(Long id) {
        return deliveryAgentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DeliveryAgent not found with ID: " + id));
    }

    public DeliveryAgent createDeliveryAgent(DeliveryAgent deliveryAgent) {
        return deliveryAgentRepository.save(deliveryAgent);
    }

    public DeliveryAgent updateDeliveryAgent(Long id, DeliveryAgent deliveryAgent) {
        DeliveryAgent existingAgent = getDeliveryAgentById(id);
        existingAgent.setAgentName(deliveryAgent.getAgentName());
        existingAgent.setPhoneNo(deliveryAgent.getPhoneNo());
        existingAgent.setEmail(deliveryAgent.getEmail());
        existingAgent.setLocation(deliveryAgent.getLocation());
        existingAgent.setVehicleDetails(deliveryAgent.getVehicleDetails());
        existingAgent.setAvailabilityStatus(deliveryAgent.getAvailabilityStatus());
        return deliveryAgentRepository.save(existingAgent);
    }

    public void deleteDeliveryAgent(Long id) {
        deliveryAgentRepository.deleteById(id);
    }
}
