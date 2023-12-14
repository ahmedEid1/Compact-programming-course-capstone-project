package station;

import cars.Car;
import cars.ChargingCar;
import logging.LogLevel;
import logging.Logger;
import logging.SystemLogger;

import java.util.ArrayList;
import java.util.List;

/*
 * This class represents the waiting queue for a station.
 * It is used to store cars that are waiting to be charged.
 * The queue is implemented as a list.
 * The queue is used to calculate the total charging time for the cars in the queue.
 * The queue is also used to remove cars from the queue when they are ready to be charged.
 * The queue is also used to log information about the cars in the queue.
 */
public class StationWaitingQueue {
    private final String stationName;
    private List<Car> queue;
    private SystemLogger logger;

    public StationWaitingQueue(String stationName) {
        this.stationName = stationName;
        this.queue = new ArrayList<>();
        this.logger = new SystemLogger("queue_for_" + stationName, true);
    }

    public void add(Car car) {
        logger.log("Car " + car.getId() + " is added to the queue.", LogLevel.INFO);
        queue.add(car);
    }

    public ChargingCar remove() {
        if (!queue.isEmpty()) {
            Car car = queue.remove(0);
            logger.log("Car " + car.getId() + " has a charging time of " + car.getChargingTime() + " minutes.", LogLevel.INFO);
            logger.log("Car " + car.getId() + " is removed from the queue and is ready to be charged.", LogLevel.INFO);
            return new ChargingCar(car, car.getChargingTime());
        }
        return null;
    }

    public int size() {
        return queue.size();
    }

    public int calculateTotalChargingTime() {
        int totalChargingTime = 0;
        for (Car car : queue) {
            totalChargingTime += car.getChargingTime();
        }
        logger.log("Total charging time for cars in queue: " + totalChargingTime, LogLevel.INFO);
        return totalChargingTime;
    }
}