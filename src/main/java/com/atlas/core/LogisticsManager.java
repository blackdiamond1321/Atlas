package com.atlas.core;

import java.util.ArrayList;
import java.util.List;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.interfaces.Validator;
import com.atlas.models.Invoice;
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

    public void processShipment(Shipment shipment) {
        //TODO: Validate the shipment using the defined rules. If any validation fails, throw an exception.
        if (weightValidator.and(destinationValidator).and(safetyValidator).test(shipment)) {
            addShipment(shipment);
        } else {
            throw new IllegalArgumentException("Shipment failed");
        }
    }

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
        //TODO: use the Factory to get specific strategy based on the shipment type, then call calculateCost on that strategy
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
                .filter(s -> s.getDestination().equalsIgnoreCase(city))
                .mapToDouble(s -> s.getWeight())
                .sum();
        return totalWeight;
    }

        /**
        * MLO8: Use of Streams to filter and aggregate data.
        * Get a list of unique destination cities sorted alphabetically.
        */
    public List<String> getUniqueDestinationsSorted() {
        //TODO: use streams to extract the destination from each shipment, filter them, and sort them alphabetically
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
        //TODO: use streams to filter the list of shipments by cost, returning those that exceed the specified amount
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
        //TODO: use streams to calculate the average cost of all shipments.
        double average = masterShipmentList.stream()
                .mapToDouble(s -> calculateSingleCost(s))
                .average()// calculate the average of the costs
                .orElse(0); // if list is empty, return 0
        return average;
    }

    /**
     * MLO8: Use of Streams to filter and aggregate data.
     * Transform all shipments into an invoice.
     */

    public List<Invoice> generateInvoices() {
        List<Invoice> invoices = masterShipmentList.stream()
                // TODO: use streams to transform each shipment into an invoice
                .map(s -> new Invoice(
                        s.getName(),
                        s.getType(),
                        s.getDestination(),
                        calculateSingleCost(s)
                ))
                .toList();
        return invoices;
    }
}
