package com.atlas;

import com.atlas.core.*;
import com.atlas.models.Shipment;
import com.atlas.strategies.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LogisticsManagerTest {
    private LogisticsManager manager;

    @BeforeEach
    void setUp() {
        manager = new LogisticsManager();
        // Base Setup: Name, Weight, Distance, City, Type
        manager.addShipment(new Shipment("Alpha", 10.0, 100.0, "London", "Standard"));
        manager.addShipment(new Shipment("Beta", 5.0, 50.0, "Manchester", "Express"));
        manager.addShipment(new Shipment("Gamma", 2.0, 10.0, "London", "Fragile"));
    }

    // --- CATEGORY 1: STRATEGY PATTERN MATH (MLO5) ---

    @Test
    @Order(1)
    void testStandardCostCalculation() {
        Shipment s = new Shipment("Test", 20.0, 200.0, "Leeds", "Standard");
        // (20*0.5) + (200*0.1) = 30.0
        assertEquals(30.0, manager.calculateSingleCost(s), 0.001);
    }

    @Test
    @Order(2)
    void testExpressCostWithProcessingFee() {
        Shipment s = new Shipment("Test", 10.0, 100.0, "Leeds", "Express");
        // Base: (10*0.5) + (100*0.1) = 15.0
        // Strategy: 15.0 * 1.75 = 26.25
        // Fee: 26.25 + 5.0 = 31.25
        assertEquals(31.25, manager.calculateSingleCost(s), 0.001);
    }

    @Test
    @Order(3)
    void testFragileCostUnderWeightLimit() {
        Shipment s = new Shipment("Test", 10.0, 100.0, "Leeds", "Fragile");
        // Base: 15.0. Strategy: (15.0 * 1.2) + 20.0 = 38.0
        assertEquals(38.0, manager.calculateSingleCost(s), 0.001);
    }

    // --- CATEGORY 2: JAVA STREAMS & ANALYTICS (MLO8) ---

    @Test
    @Order(4)
    void testStreamTotalWeightFiltering() {
        // From setup: Alpha(10) + Gamma(2) = 12
        assertEquals(12.0, manager.getTotalWeightForDestination("London"), 0.001);
    }

    @Test
    @Order(5)
    void testStreamUniqueCitiesSorting() {
        List<String> cities = manager.getUniqueDestinationsSorted();
        assertEquals(2, cities.size());
        assertEquals("London", cities.get(0));
        assertEquals("Manchester", cities.get(1));
    }

    @Test
    @Order(6)
    void testStreamHighValueShipments() {
        // Adding a very heavy/distant shipment to cross the £500 threshold
        manager.addShipment(new Shipment("HeavyGold", 1000.0, 5000.0, "Dubai", "Standard"));
        List<Shipment> highValue = manager.getHighValueShipments(500.0);
        assertEquals(1, highValue.size());
        assertEquals("HeavyGold", highValue.get(0).getName());
    }

    @Test
    @Order(7)
    void testStreamAverageCost() {
        // Average of setup items (~30.0, ~14.37, ~21.4)
        double avg = manager.calculateAverageShippingCost();
        assertTrue(avg > 0);
    }

    // --- CATEGORY 4: VALIDATION & AMBIGUITY (MLO4) ---

    @Test
    @Order(10)
    void testInvalidWeightRejection() {
        Shipment bad = new Shipment("Void", -5.0, 100.0, "London", "Standard");
        assertThrows(IllegalArgumentException.class, () -> manager.processShipment(bad));
    }

    @Test
    @Order(11)
    void testFragileWeightConstraint() {
        // Ambiguity Rule: Fragile must be under 50kg
        Shipment heavyFragile = new Shipment("Piano", 60.0, 10.0, "London", "Fragile");
        assertThrows(IllegalArgumentException.class, () -> manager.processShipment(heavyFragile));
    }

    @Test
    @Order(12)
    void testEmptyDestinationRejection() {
        Shipment nowhere = new Shipment("Ghost", 1.0, 1.0, "", "Standard");
        assertThrows(IllegalArgumentException.class, () -> manager.processShipment(nowhere));
    }

    @Test
    @DisplayName("MLO4: Validation Logic for Overweight Fragile Items")
    void testFragileWeightValidation() {
        Shipment heavyFragile = new Shipment("Vase", 60.0, 50.0, "London", "Fragile");
        // This test forces the student to decide if the code returns 0.0,
        // throws an Exception, or defaults to Standard.
        assertThrows(IllegalArgumentException.class, () -> {
            manager.processShipment(heavyFragile);
        }, "Fragile items over 50kg should be rejected per business rules.");
    }

    // --- CATEGORY 5: ROBUSTNESS & EDGE CASES (MLO10) ---

    @Test
    @Order(13)
    void testEmptyListStreamHandling() {
        LogisticsManager emptyManager = new LogisticsManager();
        assertEquals(0.0, emptyManager.getTotalWeightForDestination("London"));
        assertTrue(emptyManager.getUniqueDestinationsSorted().isEmpty());
    }

    @Test
    @Order(14)
    void testCaseInsensitiveCitySearch() {
        // Should handle "london" vs "London" if implemented correctly
        assertEquals(12.0, manager.getTotalWeightForDestination("london"), 0.001);
    }

    @Test
    @Order(15)
    void testStrategyFactoryNullHandling() {
        assertThrows(NullPointerException.class, () -> manager.calculateSingleCost(null));
    }

    @Test
    @Order(16)
    void testUnknownStrategyDefaulting() {
        Shipment weird = new Shipment("Alien", 1.0, 1.0, "Mars", "Teleport");
        // Should default to Standard or throw error based on student design
        assertNotNull(manager.calculateSingleCost(weird));
    }

    @Test
    @Order(17)
    void testLargeVolumeShipmentCount() {
        for (int i = 0; i < 100; i++) manager.addShipment(new Shipment("S", 1, 1, "C", "Standard"));
        assertEquals(103, manager.getMasterShipmentList().size());
    }

    @Test
    @Order(18)
    void testDefaultMethodFeeApplication() {
        // Verifies the 5.00 fee is added to Express but NOT Standard
        Shipment s1 = new Shipment("A", 10, 100, "C", "Standard");
        Shipment s2 = new Shipment("B", 10, 100, "C", "Express");
        double diff = manager.calculateSingleCost(s2) - (manager.calculateSingleCost(s1) * 1.75);
        assertEquals(5.00, diff, 0.001);
    }

    @Test
    @DisplayName("MLO5: Standard Shipping Strategy Calculation")
    void testStandardShippingLogic() {
        Shipment s = new Shipment("Box", 20.0, 200.0, "Bristol", "Standard");
        // Formula: (20 * 0.5) + (200 * 0.1) = 30.0
        double expected = 30.0;
        assertEquals(expected, manager.calculateSingleCost(s), 0.01);
    }

    @Test
    @DisplayName("MLO5/MLO8: Express Shipping Strategy with Default Fee")
    void testExpressShippingLogic() {
        Shipment s = new Shipment("Documents", 2.0, 100.0, "Leeds", "Express");
        // Base: (2*0.5) + (100*0.1) = 11.0
        // Strategy: 11.0 * 1.75 = 19.25
        // Default Fee: 19.25 + 5.00 = 24.25
        double expected = 24.25;
        assertEquals(expected, manager.calculateSingleCost(s), 0.01);
    }

    @Test
    @DisplayName("MLO8: Stream Filtering for Specific Destination")
    void testTotalWeightForDestination() {
        // From setUp: Item A (10kg) and Item C (2kg) are for London.
        double expected = 12.0;
        assertEquals(expected, manager.getTotalWeightForDestination("London"), 0.01);
    }

    @Test
    @DisplayName("MLO8: Stream Mapping for Unique Cities")
    void testUniqueCitiesSorted() {
        List<String> cities = manager.getUniqueDestinationsSorted();
        assertEquals(2, cities.size());
        assertEquals("London", cities.get(0));
        assertEquals("Manchester", cities.get(1));
    }
}
