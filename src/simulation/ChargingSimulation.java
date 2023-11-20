package simulation;

import charingStations.ChargingLocation;
import logging.BaseLogger;

public class ChargingSimulation {

	public static void main(String[] args) {
		// 1. handling multiple exception
		ChargingLocation chargingLocation = new ChargingLocation();
        chargingLocation.chargeCar(null);
		
		// 2. catching an exception chaining it to another exception and re-throwing it
		// TODO: change occupied to false in the chargingLocation
	
		// 3. resource management using Try with resources
        BaseLogger logger = new BaseLogger();
        logger.log("Hello World!");

	}
	
	private static void loadAndAddMetaData() {
        // Load charging station metadata
        Map<String, ChargingStationMetadata> chargingStations = MetadataLoader.loadChargingStationMetadata();
        System.out.println("Charging Station Metadata:");
        if (chargingStations != null) {
            chargingStations.forEach((id, metadata) -> System.out.println(metadata));
        }

        // Load car metadata
        Map<String, CarMetadata> cars = MetadataLoader.loadCarMetadata();
        System.out.println("\nCar Metadata:");
        if (cars != null) {
            cars.forEach((id, metadata) -> System.out.println(metadata));
        }

        // Add new charging station
        ChargingStationMetadata newChargingStation = new ChargingStationMetadata("CS-3", "Test station", "Electricity");
        MetadataLoader.writeChargingStationMetadata(newChargingStation);

        // Add new car
        CarMetadata newCar = new CarMetadata("Car-003", "New Model 3", "CS-3");
        MetadataLoader.writeCarMetadata(newCar);
    }

	 private static void logManagement(String task) {
	        if (task.equals("write")) {
	            // 1. logging system functionality
	            LogManager.logSystemFunctionality("System initialization.");
	            LogManager.logSystemFunctionality("Write log entries to a file.");
	            LogManager.logSystemFunctionality("Move log files to an archive folder.");
	            LogManager.logSystemFunctionality("Delete log files.");
	            LogManager.logSystemFunctionality("adding a new charging station");
	            LogManager.logSystemFunctionality("System shutdown.");


	            // 2. logging charging station functionality
	            LogManager.logChargingStationFunctionality("Charging session started for Car-001.");
	            LogManager.logChargingStationFunctionality("Charging session ended for Car-001.");
	            LogManager.logChargingStationFunctionality("Weather checked.");
	            LogManager.logChargingStationFunctionality("charging station is full");
	            LogManager.logChargingStationFunctionality("a new location had been freed");


	            // 3. logging energy management functionality
	            LogManager.logEnergyManagementFunctionality("Wind energy utilized for charging.");
	            LogManager.logEnergyManagementFunctionality("Solar energy utilized for charging.");
	            LogManager.logEnergyManagementFunctionality("Fossil fuel energy utilized for charging.");
	        }

	        if (task.equals("archive")) {
	            // Move log files to an archive folder
	            LogManager.moveSystemLogToArchive();
	            LogManager.moveChargingStationLogToArchive();
	            LogManager.moveEnergyManagementLogToArchive();
	        }

	    }

}
