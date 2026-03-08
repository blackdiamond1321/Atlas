package com.atlas.strategies;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.models.Shipment;

/**
 * Class ExpressShipping that implements ShippingStrategy.
 * This class should implement the calculateCost method to calculate the cost of express shipping.
 */
public class ExpressShipping implements ShippingStrategy {

    /**
     * Calculates the cost for express shipping based on the shipment details.
     * @param shipment The shipment for which the cost is to be calculated.
     * @return The calculated cost for express shipping.
     */
    @Override
    public double calculateCost(Shipment shipment) {
        // TODO: Implement the cost calculation logic for express shipping
        double base = ShippingStrategy.baseRate(shipment);
        double baseWithMultiplier = base * 1.75;

        // Add processing fee for express shipping
        return addProcessingFee(baseWithMultiplier);
    }
}
