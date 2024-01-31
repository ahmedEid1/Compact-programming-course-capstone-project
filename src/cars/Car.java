package cars;

/*
 * This class represents a car.
 * It has an id and a charging time.
 * the charging time is the time it takes for the car to be fully charged.
 * the charging time is also used to calculate the waiting time for other cars.
 */
public class Car {
    private final String id;
    private final int chargingTime;

    public Car(String id, int chargingTime) {
        this.id = id;
        this.chargingTime = chargingTime;
    }

    public String getId() {
        return id;
    }

    public int getChargingTime() {
        return chargingTime;
    }
}
