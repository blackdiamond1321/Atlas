package com.atlas.core;

import java.util.ArrayList;
import java.util.List;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.interfaces.Validator;
import com.atlas.models.Shipment;
import com.atlas.strategies.Factory;
import com.atlas.strategies.FragileShipping;

// Some boilerplate code needs to be generated.

public class LogisticsManager {
    private List<Shipment> masterShipmentList = new ArrayList<>();

    //Validations rules for shipments:

    //Rule 1. Weight Check: Weight must be between 0 and 1000 kg.
    private final Validator<Shipment> weightValidator = s -> s.getWeight() > 0 && s.getWeight() <= 1000;

    //Rule 2. Destination Check: The destination string must not be null or empty.

    private final Validator<Shipment> destinationValidator = s -> s.getDestination() != null && !s.getDestination().isEmpty();

    //Rule 3. Safety Check: If the strategy is "Fragile," the weight must not exceed 50 kg.

    private final Validator<Shipment> safetyValidator = s -> !s.getType().equalsIgnoreCase("Fragile") || s.getWeight() <= FragileShipping.MAX_WEIGHT;

    // Add a shipment to the master list.
    public void addShipment(Shipment shipment) {
        masterShipmentList.add(shipment);
    }

    // Get the master shipment list.
    public List<Shipment> getMasterShipmentList() {
        return masterShipmentList;
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

        /**
        * MLO8: Use of Streams to filter and aggregate data.
        * Get a list of unique destination cities sorted alphabetically.
        */
    public List<String> getUniqueDestinationsSorted() {
        List<String> destinations = masterShipmentList.stream()
                .map(s -> s.getDestination())
                .distinct()
                .sorted()
                .toList();
        return destinations;
    }

    /**
     * MLO8: Use of Streams to filter and aggregate data.
     * @return The list of high value shipment
     */
    public List<Shipment> getHighValueShipments(double threshold) {
        List<Shipment> highValue = masterShipmentList.stream()
                .filter(s -> calculateSingleCost(s) > threshold)
                .toList();
        return highValue;
    }

    /**
     * MLO8: Use of Streams to filter and aggregate data.
     * @return The average cost of all shipments.
     */
    public double calculateAverageShippingCost() {
        double average = masterShipmentList.stream()
                .mapToDouble(s -> calculateSingleCost(s))
                .average()
                .orElse(0); // if list is empty, return 0
        return average;
    }
}
