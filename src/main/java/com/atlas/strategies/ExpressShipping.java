package com.atlas.strategies;

import com.atlas.interfaces.ShippingStrategy;
import com.atlas.models.Shipment;

public class ExpressShipping implements ShippingStrategy {

    @Override
    public double calculateCost(Shipment shipment) {
        // TODO:
        return 0.0;
    }
}
