package com.atlas;

import com.atlas.core.LogisticsManager;
import com.atlas.models.Shipment;
import lombok.extern.java.Log;

@Log
public class Main {
    static void main(String[] args) {
        //AuditLogger logger = AuditLogger.getInstance();
        LogisticsManager manager = new LogisticsManager();

        log.info("System initialized using Interface-Driven Strategy architecture.");

        // Sample Data Entry
        manager.addShipment(new Shipment("Medical Kit", 5.0, 200.0, "London", "Express"));
        manager.addShipment(new Shipment("Large Crate", 150.0, 50.0, "Manchester", "Standard"));
        manager.addShipment(new Shipment("Glass Vase", 2.5, 100.0, "Bristol", "Fragile"));

        // Dashboard Output
        System.out.println("\n--- ATLAS LOGISTICS DASHBOARD ---");
        System.out.printf("Total Shipments: %d%n", manager.getMasterShipmentList().size());
        System.out.printf("Unique Cities:   %s%n", manager.getUniqueDestinationsSorted());

        System.out.println("\n--- DETAILED BILLING ---");
        manager.getMasterShipmentList().forEach(s -> {
            double cost = manager.calculateSingleCost(s);
            System.out.printf("[%s] -> %-10s | Cost: £%7.2f%n", s.getType(), s.getName(), cost);
        });

        // Test GitHub actions connection

        // Invoice Output
        System.out.println("\n--- INVOICES ---");
        System.out.println("-".repeat(102));
        int[] counter = {1};
        manager.generateInvoices().forEach(i -> {
            System.out.printf("| Invoice: #%-3d | Name: %-12s | Type: %-10s | Destination: %-12s | Cost: £%7.2f |%n", counter[0]++, i.getShipmentName(), i.getShipmentType(), i.getDestination(), i.getTotalCost());
            System.out.println("-".repeat(102));
        });

        log.info("Analysis complete. All billing calculated via Strategy delegates.");
    }
}
