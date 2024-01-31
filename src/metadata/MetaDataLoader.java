package metadata;

import cars.Car;
import station.ChargingStation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetaDataLoader {
    public static List<ChargingStation> loadChargingStations(String filePath) {
        List<ChargingStation> chargingStations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("station_capacity,station_name")) {
                    continue;
                }
                String[] parts = line.split(",");
                int capacity = Integer.parseInt(parts[0]);
                String name = parts[1];
                chargingStations.add(new ChargingStation(capacity, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chargingStations;
    }

    public static List<Car> loadCars(String filePath) {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("car_id,charging_time")) {
                    continue;
                }
                String[] parts = line.split(",");
                String id = parts[0];
                int chargingTime = Integer.parseInt(parts[1]);
                cars.add(new Car(id, chargingTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
