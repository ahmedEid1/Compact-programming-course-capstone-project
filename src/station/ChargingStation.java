package station;

import cars.Car;
import cars.ChargingCar;
import energy.EnergySource;
import energy.EnergySourceType;
import energy.ReservedBattery;
import logging.LogLevel;
import logging.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Task: Simulate the system which allow to charge simultaneously 1..N vehicles, depending on  the available resources.
 *    A charging station: A station that can charge 1..N vehicles, depending on its capacity.
 *   we have a list of cars inside the station, and a queue of cars waiting to enter the station.
 *  for the car inside the station a thread run every second to decrement the charging time of the cars.
 *  once the charging time is 0, the car will leave the station and the next car in the queue will enter the station.
 */

/*
 * Task: The testing program simulate charging the cars in parallel at 2..N charging stations.
 * every object of this class represent a charging station. and every one will have its own queue and charging list.
 * the charging server will be updated every second in each station desperately because each one has its own thread.
 */

/*
 * Task: When the car appear in the queue should be calculated the waiting time, if time is more then 15 min car leaving the queue.
 *  the waiting time is calculated based on the number of cars in the queue and the charging time of the cars in the station.
 * Method: addToStation: add the car to the station, if the waiting time is more than 15 minutes, the car will switch to the next station and will not enter the queue or the station charging list
 */
public class ChargingStation {
    private final String name;
    private Lock lock;
    private int capacity;

    private ChargingCar[] chargingCarList;
    private int smallestChargingTime = Integer.MAX_VALUE;
    private StationWaitingQueue carQueue;

    private List<EnergySource> energySources;
    private ReservedBattery reservedBattery;
    private Logger logger;

    public ChargingStation(int capacity, String name) {
        this.name = name;
        this.lock = new ReentrantLock();
        this.logger = new Logger("logs/Stations/" + name + "/charging_station.log", true);

        // ---- energy sources ----
        this.reservedBattery = new ReservedBattery(this.name + " battery");
        this.energySources = List.of(
                new EnergySource(EnergySourceType.SOLAR, reservedBattery, name),
                new EnergySource(EnergySourceType.WIND, reservedBattery, name),
                new EnergySource(EnergySourceType.GAS, reservedBattery, name)
        );
        // ---- energy sources ----

        this.capacity = capacity;
        this.chargingCarList = new ChargingCar[capacity];
        this.carQueue = new StationWaitingQueue(name);

        // Update the charging station every second
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::processChargingCars, 0, 1, TimeUnit.SECONDS);
    }

    private void processChargingCars() {
        try {
            lock.lock();
            for (int i = 0; i < chargingCarList.length; i++) {
                ChargingCar carCharging = chargingCarList[i];
                // If there is a car charging at this location, decrement the charging time
                if (carCharging != null) {
                    carCharging.chargingTime--;
                    if (carCharging.chargingTime <= 0) {
                        logger.log(LogLevel.INFO, "Car " + carCharging.car.getId() + " is done charging at Station" + name);
                        chargingCarList[i] = null;
                        // Get the next car in queue and charge it
                        ChargingCar nextCar = carQueue.remove();
                        if (nextCar != null) {
                            chargeCar(nextCar, i);
                        }
                    } else {
                        logger.log(LogLevel.INFO, "Car " + carCharging.car.getId() + " is charging at Station" + name + " for " + carCharging.chargingTime + " minutes.");
                    }
                    updateSmallestChargingTime();
                } else {
                    // Get the next car in queue and charge it
                    ChargingCar nextCar = carQueue.remove();
                    if (nextCar != null) {
                        chargeCar(nextCar, i);
                    }
                    updateSmallestChargingTime();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void chargeCar(ChargingCar car, int index) {
        chargingCarList[index] = car;
        logger.log(LogLevel.INFO, "Car " + car.car.getId() + " is charging at Station" + name + " for " + car.chargingTime + " minutes.");
    }

    private void updateSmallestChargingTime() {
        for (ChargingCar car : chargingCarList) {
            if (car != null && (car.chargingTime < smallestChargingTime)) {
                smallestChargingTime = car.chargingTime;
            }
        }
    }
 // ---- managing the station queue ----
    public boolean addToStation(Car car) {
        int waitingTime = getWaitingTimeForNextCar();
        logger.log(LogLevel.INFO, "Car " + car.getId() + " - Waiting Time: " + waitingTime + " minutes");
        if (waitingTime >= 15) {
            logger.log(LogLevel.INFO, "Car " + car.getId() + " - Waiting Time is more than 15 minutes, switching to the next station.");
            return false;
        }
        for (int i = 0; i < chargingCarList.length; i++) {
            if (chargingCarList[i] == null) {
                chargeCar(new ChargingCar(car, car.getChargingTime()), i);
                smallestChargingTime = Math.min(smallestChargingTime, car.getChargingTime());
                return true;
            }
        }
        carQueue.add(car);
        logger.log(LogLevel.INFO, "Car " + car.getId() + " added to the queue at Station.");
        return true;
    }

    public int getWaitingTimeForNextCar() {
        logger.log(LogLevel.INFO, "Number of empty locations: " + getNumberOfEmptyLocation());
        logger.log(LogLevel.INFO, "Number of cars in queue: " + carQueue.size());
        if (carQueue.size() < getNumberOfEmptyLocation()) {
            return 0;
        }
        logger.log(LogLevel.INFO, "Smallest charging time: " + smallestChargingTime);
        return smallestChargingTime + carQueue.calculateTotalChargingTime();
    }

    private int getNumberOfEmptyLocation() {
        int nulls = 0;
        for (ChargingCar car : chargingCarList) {
            if (car == null) {
                nulls++;
            }
        }
        return nulls;
    }
    // ---- managing the station queue ----

}
