package test.energy;

import energy.ReservedBattery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservedBatteryTest {
    @Test
    public void testGetEnergyLevel() {
        // Arrange
        ReservedBattery reservedBattery = new ReservedBattery("Station1");

        // Act
        int energyLevel = reservedBattery.getEnergyLevel();

        // Assert
        assertEquals(0, energyLevel);
    }

    @Test
    public void testCharge() {
        // Arrange
        ReservedBattery reservedBattery = new ReservedBattery("Station1");

        // Act
        reservedBattery.charge(50, "Solar");
        int energyLevel = reservedBattery.getEnergyLevel();

        // Assert
        assertEquals(50, energyLevel);
    }

    @Test
    public void testDischarge() {
        // Arrange
        ReservedBattery reservedBattery = new ReservedBattery("Station1");
        reservedBattery.charge(50, "Solar");

        // Act
        reservedBattery.discharge(20);
        int energyLevel = reservedBattery.getEnergyLevel();

        // Assert
        assertEquals(30, energyLevel);
    }
}