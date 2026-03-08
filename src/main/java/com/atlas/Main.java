package com.atlas;

import com.atlas.core.LogisticsManager;
import com.atlas.models.Shipment;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        int[] counter = {1}; //array used to modify the counter inside the lambda expression

        System.out.println("\n--- INVOICES ---");
        System.out.println("-".repeat(128));
        manager.generateInvoices().forEach(i -> {
            System.out.printf("| Invoice: #%-3d | Name: %-12s | Type: %-10s | Destination: %-12s | Cost: £%.2f | Date: %s |%n",
                    counter[0]++,
                    i.getShipmentName(),
                    i.getShipmentType(),
                    i.getDestination(),
                    i.getTotalCost(),
                    i.getDateTime().format(formatter));
            System.out.println("-".repeat(128));
        });

        log.info("Analysis complete. All billing calculated via Strategy delegates.");
    }
}
