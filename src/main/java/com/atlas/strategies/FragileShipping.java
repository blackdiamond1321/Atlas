package com.atlas.strategies;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.models.Shipment;

/**
 * Create a class FragileShipping that implements ShippingStrategy.
 * This class should implement the calculateCost method to calculate the cost of express shipping.
 */
public class FragileShipping implements ShippingStrategy {

    // declare a constant for the maximum weight limit for fragile shipments
    public static final double MAX_WEIGHT = 50;

    /**
     * Calculates the cost for fragile shipping based on the shipment details.
     * ONLY if the weight of the shipment <= MAX_WEIGHT.
     * @param shipment The shipment for which the cost is to be calculated.
     * @return The calculated cost for fragile shipping
     */
    @Override
    public double calculateCost(Shipment shipment) {
        double base = ShippingStrategy.baseRate(shipment);
        return (base * 1.2) + 20;
    }
}
