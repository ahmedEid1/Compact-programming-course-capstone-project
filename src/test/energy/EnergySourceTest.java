package test.energy;

import energy.EnergySource;
import energy.EnergySourceType;
import energy.ReservedBattery;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnergySourceTest {
    @Test
    public void testIsWorking() {
        // Arrange
        ReservedBattery reservedBattery = new ReservedBattery("Station1");
        EnergySource energySource = new EnergySource(EnergySourceType.SOLAR, reservedBattery, "Station1");

        // Act
        boolean isWorking = energySource.isWorking();

        // Assert
        assertTrue(isWorking);
    }

    @Test
    public void testEnergySourceConstructor() {
        // Arrange
        ReservedBattery reservedBattery = new ReservedBattery("Station1");

        // Act
        EnergySource energySource = new EnergySource(EnergySourceType.SOLAR, reservedBattery, "Station1");

        // Assert
        assertNotNull(energySource);
    }
}