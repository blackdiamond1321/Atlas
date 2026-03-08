package com.atlas.strategies;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.models.Shipment;

/**
 * Class StandardShipping that implements ShippingStrategy.
 * This class should implement the calculateCost method to calculate the cost of standard shipping.
 */
public class StandardShipping implements ShippingStrategy {
    /**
     * Calculates the cost for standard shipping based on the shipment details.
     * @param shipment The shipment for which the cost is to be calculated.
     * @return The calculated cost for standard shipping.
     */
    @Override
    public double calculateCost(Shipment shipment) {
        return ShippingStrategy.baseRate(shipment);
    }
}
