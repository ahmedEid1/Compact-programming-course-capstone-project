package users;

import car.Car;
import charingStations.ChargingStation;
public class User {
    private String userId;
    private Car car;

    public User(String userId, Car carModel) {
        this.userId = userId;
        this.car = carModel;
    }

    public String getUserId() {
        return userId;
    }

    public Car getCar() {
        return car;
    }

    public void addToQueue(ChargingStation chargingStation) {
        chargingStation.addToQueue(this);
    }

    public String getId() {
        return userId;
    }
}
