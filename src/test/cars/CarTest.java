package test.cars;

import cars.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {
    @Test
    public void testGetId() {
        // Arrange
        Car car = new Car("1234", 120);

        // Act
        String id = car.getId();

        // Assert
        assertEquals("1234", id);
    }

    @Test
    public void testGetChargingTime() {
        // Arrange
        Car car = new Car("1234", 120);

        // Act
        int chargingTime = car.getChargingTime();

        // Assert
        assertEquals(120, chargingTime);
    }
}