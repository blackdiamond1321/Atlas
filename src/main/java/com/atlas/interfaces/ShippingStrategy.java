// This interface defines the contract for all pricing logic.
// Note the use of `@FunctionalInterface`, which allows students to implement simple strategies using Lambdas.

package com.atlas.interfaces;

import com.atlas.models.Shipment;

/**
 * MLO2: Background of OO implications.
 * This interface defines the Strategy for calculating shipping costs.
 */
@FunctionalInterface
public interface ShippingStrategy {

        /**
        * Method to calculate the base rate for a shipment
        * @param shipment The shipment details (weight and distance)
        * @return the base rate for the shipment
        */

    static double baseRate(Shipment shipment) {
        return (shipment.getWeight() * 0.50) + (shipment.getDistance() * 0.10);
    }

    /**
     * Calculates the cost for a given shipment.
     * @param shipment The shipment details (weight, distance, etc.)
     * @return The calculated cost as a double.
     */
    double calculateCost(Shipment shipment);

    /**
     * MLO8: Default method for adding a flat-rate processing fee.
     * This demonstrates how to evolve interfaces without breaking implementations.
     */
    default double addProcessingFee(double baseCost) {
        // the tests indicate what this method needs to return
        return baseCost + 5.0; // Test 2 shows that the processing fee is £5.0
    }
}
