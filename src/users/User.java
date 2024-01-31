package users;

import cars.Car;
import station.ChargingStation;

/*
 * a user can:
 * - add car to a queue
 * - remove car from a queue
 */
public class User {
    private String userId;
    private Car car;

    public User(String userId, Car car) {
        this.userId = userId;
        this.car = car;
    }

    public String getUserId() {
        return userId;
    }

    public Car getCar() {
        return car;
    }

    public boolean addToQueue(ChargingStation chargingStation) {
        return chargingStation.addToStation(car);
    }

    public void removeFromQueue(ChargingStation chargingStation) {
        chargingStation.getQueue().removeCar(car);
    }

    public String getId() {
        return userId;
    }

    public String getCarId() {
        return car.getId();
    }
}
