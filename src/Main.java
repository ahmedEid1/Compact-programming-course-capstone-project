import cars.Car;
import station.ChargingStation;

import java.util.List;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        // create a charging station
        ChargingStation station1 = new ChargingStation(2, "station1");
        ChargingStation station2 = new ChargingStation(2, "station2");
        List<ChargingStation> chargingStations = List.of(station1, station2);

        Car car1 = new Car("car1", 10);
        // wait for 1 seconds
        Car car2 = new Car("car2", 15);
        Car car3 = new Car("car3", 12);
        Car car4 = new Car("car4", 20);
        Car car5 = new Car("car5", 10);
        Car car6 = new Car("car6", 15);
        Car car7 = new Car("car7", 12);
        Car car8 = new Car("car8", 20);
        List<Car> cars = List.of(car1, car2, car3, car4, car5, car6, car7, car8);

        /*
         * Task: car arriving in random moment of time.
         *   the car will try to charge at a random station, if it is full, it will try the next one, and so on.
         */
        for (Car car : cars) {
            chargeCar(car, chargingStations);
        }
    }

    // a method that will take a car and a list of charging stations, it will try to charge the car at the first station
    // if the station is full, it will try the next one, and so on, it will wait for 1 second between each try
    // if all are full, it will wait for 5 seconds and try again
    public static void chargeCar(Car car, List<ChargingStation> chargingStations) {
        Thread thread = new Thread(() -> {
            boolean isCharged = false;

            // keep trying to charge the car until it is charged
            while (!isCharged) {
                synchronized (chargingStations) {
                    // randomize the order of the stations in the list
                    ChargingStation[] stations = randomizeStationsOrder(chargingStations);

                    // try to get the car into a station, if the waiting > 15 try the next station
                    isCharged = tryToCharge(car, stations, isCharged);

                    // if the car is not charged, wait for 5 seconds and try again
                    if (!isCharged)
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }

            }
        });

        thread.start();
    }

    private static boolean tryToCharge(Car car, ChargingStation[] stations, boolean isCharged) {
        for (ChargingStation station : stations) {
            if (station.addToStation(car)) {
                isCharged = true;
                break;
            }
            try {
                // sleep for a random time between 1 and 5 seconds
                Thread.sleep((long) (Math.random() * 4000 + 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return isCharged;
    }

    private static ChargingStation[] randomizeStationsOrder(List<ChargingStation> chargingStations) {
        ChargingStation[] stations = chargingStations.toArray(new ChargingStation[0]);
        for (int i = 0; i < stations.length; i++) {
            int randomIndex = new Random().nextInt(stations.length);
            ChargingStation temp = stations[i];
            stations[i] = stations[randomIndex];
            stations[randomIndex] = temp;
        }
        return stations;
    }
}
