package users;

import cars.Car;
import station.ChargingStation;
import station.StationWaitingQueue;

/*
* an admin can:
* - add energy source to a station
* - remove energy source from a station
* - add car to a queue
* - remove car from a queue
*/
public class Admin {
    private final String adminId;

    public Admin(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void addEnergySource(ChargingStation chargingStation, String energySource) {
        chargingStation.addEnergySource(energySource);
    }

    // removes a energy source from a station
    public void removeEnergySource(ChargingStation chargingStation, String energySource) {
        chargingStation.removeEnergySource(energySource);
    }

    public void addCarToQueue(ChargingStation chargingStation, Car car) {
        StationWaitingQueue chargingStationQueue = chargingStation.getQueue();
        chargingStationQueue.add(car);
    }

    public void removeCarFromQueue(ChargingStation chargingStation, Car car) {
        StationWaitingQueue chargingStationQueue = chargingStation.getQueue();
        chargingStationQueue.removeCar(car);
    }

}
