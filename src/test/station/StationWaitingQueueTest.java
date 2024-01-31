package test.station;

import cars.Car;
import cars.ChargingCar;
import station.StationWaitingQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StationWaitingQueueTest {
    @Test
    public void testAdd() {
        // Arrange
        StationWaitingQueue queue = new StationWaitingQueue("Station1");
        Car car = new Car("1234", 10);

        // Act
        queue.add(car);

        // Assert
        assertEquals(1, queue.size());
    }

    @Test
    public void testRemove() {
        // Arrange
        StationWaitingQueue queue = new StationWaitingQueue("Station1");
        Car car = new Car("1234", 10);
        queue.add(car);

        // Act
        ChargingCar chargingCar = queue.remove();

        // Assert
        assertEquals(0, queue.size());
        assertEquals(car.getId(), chargingCar.car.getId());
    }

    @Test
    public void testSize() {
        // Arrange
        StationWaitingQueue queue = new StationWaitingQueue("Station1");
        Car car1 = new Car("1234", 10);
        Car car2 = new Car("5678", 20);
        queue.add(car1);
        queue.add(car2);

        // Act
        int size = queue.size();

        // Assert
        assertEquals(2, size);
    }

    @Test
    public void testCalculateTotalChargingTime() {
        // Arrange
        StationWaitingQueue queue = new StationWaitingQueue("Station1");
        Car car1 = new Car("1234", 10);
        Car car2 = new Car("5678", 20);
        queue.add(car1);
        queue.add(car2);

        // Act
        int totalChargingTime = queue.calculateTotalChargingTime();

        // Assert
        assertEquals(30, totalChargingTime);
    }
}