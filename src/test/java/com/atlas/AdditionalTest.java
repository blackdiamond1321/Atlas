package com.atlas;

import com.atlas.core.*;
import com.atlas.models.Invoice;
import com.atlas.models.Shipment;
import com.atlas.strategies.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdditionalTest {
    private LogisticsManager manager;

    @BeforeEach
    void setUp() {
        manager = new LogisticsManager();
        // Base Setup: Name, Weight, Distance, City, Type
        manager.addShipment(new Shipment("Alpha", 10.0, 100.0, "London", "Standard"));
        manager.addShipment(new Shipment("Beta", 5.0, 50.0, "Manchester", "Express"));
        manager.addShipment(new Shipment("Gamma", 2.0, 10.0, "London", "Fragile"));
    }
    @Test
    @Order(1)
    void testInvoiceListEmptyWhenNoShipments() {
        // Empty Invoice list when no shipments are present
        LogisticsManager emptyManager = new LogisticsManager();
        List<Invoice> invoices = emptyManager.generateInvoices();
        assertTrue(invoices.isEmpty(), "Invoice list should be empty when there are no shipments.");
    }

    @Test
    @Order(2)
    void testExpressRevenueFor30Days() {
        // Express revenue is positive when Express shipments exist
        double revenue = manager.calculateExpressRevenueFor30Days();
        assertTrue(revenue > 0);
    }

    @Test
    @Order(3)
    void testInvoiceTimestamps() {
        // Invoices have time within the last 30 days
        List<Invoice> invoices = manager.generateInvoices();
        LocalDateTime now = LocalDateTime.now();
        for (Invoice invoice : invoices) {
            assertTrue(invoice.getDateTime().isBefore(now) && invoice.getDateTime().isAfter(now.minusDays(30)),
                    "Invoice should be within the last 30 days.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Weight = 0 is rejected")
    void testZeroWeightRejected() {
        Shipment s = new Shipment("Empty", 0.0, 100.0, "london", "Standard");
        assertThrows(IllegalArgumentException.class, () -> manager.processShipment(s));
    }

    @Test
    @Order(5)
    @DisplayName("Weight = 1000.1 is rejected")
    void testOverMaxWeightRejected() {
        Shipment s = new Shipment("TooHeavy", 1000.1, 100.0, "Manchester", "Standard");
        assertThrows(IllegalArgumentException.class, () -> manager.processShipment(s));
    }

}