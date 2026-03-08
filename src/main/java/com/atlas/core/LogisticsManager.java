package com.atlas.core;

import java.util.ArrayList;
import java.util.List;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.models.Shipment;
import com.atlas.strategies.Factory;

// Some boilerplate code needs to be generated.

public class LogisticsManager {
    private List<Shipment> masterShipmentList = new ArrayList<>();

    // Add a shipment to the master list.
    public void addShipment(Shipment shipment) {
        masterShipmentList.add(shipment);
    }

    /**
      * method to calculate the cost of a single shipment using the Strategy pattern.
      * @param shipment The shipment for which the cost is to be calculated.
      * @return The calculated cost for the given shipment.
     */
    public double calculateSingleCost(Shipment shipment) {
        ShippingStrategy strategy = Factory.getStrategy(shipment.getType());
        double cost = strategy.calculateCost(shipment);
        return cost;
    }

    /**
     * MLO8: Use of Streams to filter and aggregate data.
     * Find the total weight of all shipments for a specific destination.
     */
    public double getTotalWeightForDestination(String city) {
        //TODO: use streams to filter the list by destination and sum the weights of the shipments that match the destination.
        double totalWeight = masterShipmentList.stream()
                .filter(s -> s.getDestination().equals(city))
                .mapToDouble(s -> s.getWeight())
                .sum();
        return totalWeight;
    }
}
