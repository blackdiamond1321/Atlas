package com.atlas.interfaces;

import java.util.function.Predicate;
/**
 * Create Validator interface that will be used for validating shipments.
 * Extends Predicate to use lambda expressions in LogisticsManager.

 * It will check if a shipment is valid
 * @param <T> is a Shipment to be validated
 */

public interface Validator<T> extends Predicate <T> {

    /**
     * @return true if it is valid and false if it is not valid
     * @param item the item to be validated
     */
    boolean test(T item);
}
