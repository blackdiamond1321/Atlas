package com.atlas.core;

import java.util.ArrayList;
import java.util.List;

import com.atlas.models.Shipment;

// Some boilerplate code needs to be generated.

public class LogisticsManager {
    private List<Shipment> masterShipmentList = new ArrayList<>();

    // Add a shipment to the master list.
    public void addShipment(Shipment shipment) {
        masterShipmentList.add(shipment);
    }

    /**
     * MLO8: Use of Streams to filter and aggregate data.
     * Find the total weight of all shipments for a specific destination.
     */
    public double getTotalWeightForDestination(String city) {
        //TODO
        return 0.0;
    }
}
