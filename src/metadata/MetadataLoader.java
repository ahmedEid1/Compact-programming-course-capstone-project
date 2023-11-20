package metadata;

import logging.LogManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetadataLoader {


    public static Map<String, ChargingStationMetadata> loadChargingStationMetadata() {
        try {
            Map<String, ChargingStationMetadata> chargingStations = new HashMap<>();
            Path path = Path.of("metadata/CarMetadata.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String location = parts[1].trim();
                    String energySource = parts[2].trim();
                    chargingStations.put(id, new ChargingStationMetadata(id, location, energySource));
                }
            }

            LogManager.logSystemFunctionality("Charging station metadata loaded.");
            return chargingStations;
        } catch (IOException e) {
            LogManager.logSystemFunctionality("Error while loading charging station metadata.");
        }

        return null;
    }

    public static Map<String, CarMetadata> loadCarMetadata() {
        try {
            Map<String, CarMetadata> cars = new HashMap<>();
            Path path = Path.of("metadata/CarMetadata.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String id = parts[0].trim();
                    String model = parts[1].trim();
                    String chargingStationID = parts[2].trim();
                    cars.put(id, new CarMetadata(id, model, chargingStationID));
                }
            }

            LogManager.logSystemFunctionality("Car metadata loaded.");
            return cars;
        } catch (Exception e) {
            LogManager.logSystemFunctionality("Error while loading car metadata.");
        }

        return null;
    }

    public static void writeChargingStationMetadata(ChargingStationMetadata chargingStation) {
        try {
            Path path = Path.of("metadata/ChargingStationMetadata.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            List<String> lines = Files.readAllLines(path);

            String line = String.join(",", chargingStation.getId(), chargingStation.getLocation(), chargingStation.getEnergySource());
            lines.add(line);

            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            LogManager.logChargingStationFunctionality("A new charging station had been added to the system.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            LogManager.logSystemFunctionality("Error while writing charging station metadata.");
        }
    }

    public static void writeCarMetadata(CarMetadata car) {
        try {

            Path path = Path.of("metadata/CarMetadata.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            List<String> lines = Files.readAllLines(path);

            String line = String.join(",", car.getId(), car.getModel(), car.getChargingStationID());
            lines.add(line);
            // clear the file and write the new content

            Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            LogManager.logChargingStationFunctionality("A new car had been added to the system.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            LogManager.logSystemFunctionality("Error while writing car metadata.");
        }
    }
}
