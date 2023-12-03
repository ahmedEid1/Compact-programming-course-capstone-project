package cars;

/*
 * this a class that represents the charging of car inside the station.
 * it has a car and the charging time for that car.
 * the charging time is the time remaining for the car to be fully charged.
 * when this time is 0, the car is fully charged and ready to leave the station.
 * this class is used by the charging-manager thread in every station to handle the charging of cars.
 */
public class ChargingCar {
    public Car car;
    public int chargingTime;

    public ChargingCar(Car car, int chargingTime) {
        this.car = car;
        this.chargingTime = chargingTime;
    }
}
