package com.app.app.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_agent")
public class DeliveryAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryAgentId;

    private String agentName;
    private Long phoneNo;
    private String email;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    private String vehicleDetails;
    private Boolean availabilityStatus;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
