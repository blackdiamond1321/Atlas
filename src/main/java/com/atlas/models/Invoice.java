package com.atlas.models;

import lombok.Value;
import java.time.LocalDateTime;

// Create a class Invoice for the billing system

@Value
public class Invoice {
    String shipmentName;
    String shipmentType;
    String destination;
    double totalCost;
    LocalDateTime dateTime;
}
