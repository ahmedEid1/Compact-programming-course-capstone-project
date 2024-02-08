package chargingSimulation;

import cars.Car;
import energy.WeatherService;
import metadata.MetaDataLoader;
import station.ChargingStation;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChargingSimulator {

    public static void carChargingSimulation() {
        // read metadata from csv files
        List<ChargingStation> chargingStations = MetaDataLoader.loadChargingStations("SimulationData/charging_stations.csv");
        List<Car> cars = MetaDataLoader.loadCars("SimulationData/cars.csv");

        // start weather simulation
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(WeatherService::writeWeatherConditions, 0, 20, TimeUnit.SECONDS);

        ChargingSimulationUI ui = getChargingSimulationUI(chargingStations, cars);

        for (Car car : cars) {
            chargeCar(car, chargingStations, ui);
        }
    }

    private static ChargingSimulationUI getChargingSimulationUI(List<ChargingStation> chargingStations, List<Car> cars) {
        ChargingSimulationUI ui = new ChargingSimulationUI(chargingStations, cars);
        // log to the ui, number of locations in each station
        for (ChargingStation station : chargingStations) {
            ui.appendLog(station.getName(), "Station " + station.getName() + " has " + station.getAvailableSlots() + " slots");
            station.setUi(ui);
        }

        // for every car, log the charging time
        for (Car car : cars) {
            ui.appendLog(car.getId(), "Car " + car.getId() + " will take " + car.getChargingTime() + " seconds to charge");
        }
        return ui;
    }

    // a method that will take a car and a list of charging stations, it will try to charge the car at the first station
    // if the station is full, it will try the next one, and so on, it will wait for 1 second between each try
    // if all are full, it will wait for 5 seconds and try again
    public static void chargeCar(Car car, List<ChargingStation> chargingStations, ChargingSimulationUI ui) {
        Thread thread = new Thread(() -> {
            boolean isCharged = false;

            // keep trying to charge the car until it is charged
            while (!isCharged) {
                synchronized (chargingStations) {
                    // randomize the order of the stations in the list
                    ChargingStation[] stations = randomizeStationsOrder(chargingStations);

                    // try to get the car into a station, if the waiting > 15 try the next station
                    isCharged = tryToCharge(car, stations, isCharged, ui);

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

    private static boolean tryToCharge(Car car, ChargingStation[] stations, boolean isCharged, ChargingSimulationUI ui) {
        for (ChargingStation station : stations) {
            // ui logging
            ui.appendLog(car.getId(), "Car " + car.getId() + " is trying to enter station " + station.getName());
            ui.appendLog(car.getId(), "waiting time for the next car is " + station.getWaitingTimeForNextCar() + " seconds");

            if (station.addToStation(car)) {
                // ui logging
                ui.appendLog(car.getId(), "Car " + car.getId() + " has entered " + station.getName());
                isCharged = true;
                break;
            }
            // ui logging
            ui.appendLog(car.getId(), "Car " + car.getId() + " failed to enter " + station.getName() + ", trying next station");

            try {
                // sleep for a random time between 1 and 5 seconds
                Thread.sleep(1000);
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
