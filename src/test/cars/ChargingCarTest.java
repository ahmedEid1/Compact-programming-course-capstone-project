package test.cars;

import cars.Car;
import cars.ChargingCar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChargingCarTest {
    @Test
    public void testChargingCarConstructor() {
        // Arrange
        Car car = new Car("1234", 120);
        int chargingTime = 60;

        // Act
        ChargingCar chargingCar = new ChargingCar(car, chargingTime);

        // Assert
        assertEquals(car, chargingCar.car);
        assertEquals(chargingTime, chargingCar.chargingTime);
    }
}