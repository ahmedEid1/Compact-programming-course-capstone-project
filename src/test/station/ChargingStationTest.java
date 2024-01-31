package test.station;

import cars.Car;
import station.ChargingStation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChargingStationTest {
    @Test
    public void testAddToStation() {
        // Arrange
        ChargingStation station = new ChargingStation(2, "Station1");
        Car car1 = new Car("1234", 10);
        Car car2 = new Car("5678", 20);
        Car car3 = new Car("5622", 10);
        Car car4 = new Car("3478", 20);

        // Act
        boolean result1 = station.addToStation(car1);
        boolean result2 = station.addToStation(car2);
        boolean result3 = station.addToStation(car3);
        boolean result4 = station.addToStation(car4); // Should return false as the station is full and waiting time is more than 15 minutes

        // Assert
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }

    @Test
    public void testGetWaitingTimeForNextCar() {
        // Arrange
        ChargingStation station = new ChargingStation(2, "Station1");
        Car car1 = new Car("1234", 10);
        Car car2 = new Car("5678", 20);
        station.addToStation(car1);
        station.addToStation(car2);

        // Act
        int waitingTime = station.getWaitingTimeForNextCar();

        // Assert
        assertEquals(10, waitingTime); // Should return the smallest charging time as the station is full
    }
}