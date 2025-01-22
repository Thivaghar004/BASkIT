package com.app.app.Controller;

import com.app.app.Models.DeliveryAgent;
import com.app.app.Services.DeliveryAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class DeliveryAgentController {

    @Autowired
    private DeliveryAgentService deliveryAgentService;

    @GetMapping
    public List<DeliveryAgent> getAllDeliveryAgents() {
        return deliveryAgentService.getAllDeliveryAgents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryAgent> getDeliveryAgentById(@PathVariable Long id) {
        DeliveryAgent agent = deliveryAgentService.getDeliveryAgentById(id);
        return ResponseEntity.ok(agent);
    }

    @PostMapping
    public ResponseEntity<DeliveryAgent> createDeliveryAgent(@RequestBody DeliveryAgent deliveryAgent) {
        DeliveryAgent newAgent = deliveryAgentService.createDeliveryAgent(deliveryAgent);
        return ResponseEntity.ok(newAgent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryAgent> updateDeliveryAgent(@PathVariable Long id, @RequestBody DeliveryAgent deliveryAgent) {
        DeliveryAgent updatedAgent = deliveryAgentService.updateDeliveryAgent(id, deliveryAgent);
        return ResponseEntity.ok(updatedAgent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryAgent(@PathVariable Long id) {
        deliveryAgentService.deleteDeliveryAgent(id);
        return ResponseEntity.noContent().build();
    }
}

