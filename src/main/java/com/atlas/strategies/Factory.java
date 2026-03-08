package com.atlas.strategies;

import com.atlas.interfaces.ShippingStrategy;
import java.util.Map;

/**
 * Create a class that return the appropriate ShippingStrategy based on a shipment type string.
 * Uses a Map to associate type strings with  appropriate ShippingStrategy.
 */
public class Factory {
    // Immutable Map to hold the mapping of type string to ShippingStrategy instance
    private static final Map<String, ShippingStrategy> SHIPPING_CHOICES = Map.of("express", new ExpressShipping(), "fragile", new FragileShipping(), "standard", new StandardShipping());

    /**
     * Returns the ShippingStrategy based on the provided type string.
     */

    public static ShippingStrategy getStrategy(String type) {
        return SHIPPING_CHOICES.getOrDefault(type.toLowerCase(), new StandardShipping());
    }
}
