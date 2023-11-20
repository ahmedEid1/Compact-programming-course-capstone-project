package simulation;

import charingStations.ChargingLocation;
import logging.BaseLogger;
import car.Car;
import charingStations.ChargingStation;
import dataexchange.ByteDataExchanger;
import dataexchange.CharacterDataExchanger;
import dataexchange.interfaces.DateExchanger;
import logging.LogManager;
import metadata.CarMetadata;
import metadata.ChargingStationMetadata;
import metadata.MetadataLoader;
import users.Admin;
import users.User;
import weatherSimulator.WeatherService;

import java.util.Map;

public class ChargingSimulation {

	public static void main(String[] args) {
        // Test for: Create multi-user access to the charging station – external users who could book timeslot and be part of the prioritized queue, administrators
        // Done
        // we have users: can add themselves to the queue of a charging station, but not handle the queue or change the charging method
        // Admins can change the charging method of a charging station and handle the queue of their charging station,
        // but not a different charging station
//        accessManagementForStations();


        // Test for: Create metadata for your project
        // the cars and station in the simulation are loaded from metadata files, and if any new car or station is added
        // during the runtime, the metadata files are updated
//        loadAndAddMetaData();


        /*
        Test for: managing the log files:
         - create, move, delete, archive.
         Log files should be devided into classes:
          1. functionality of the system
          2.functionality of a  charging station
          3. functionality of the energy management system.
        * */
//        logManagement("write");
//        logManagement("archive");


        // Test for: Use byte and character streams for simulation of the data exchange in the system
        exchangeDateUsingDateExchanger();
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
	 
	    private static void accessManagementForStations() {
	        // Create instances of users, charging stations, and administrators
	        User user1 = new User("User001", new Car("Car001", "ModelX"));
	        User user2 = new User("User002", new Car("Car002", "ModelY"));

	        ChargingStation stationA = new ChargingStation("StationA");
	        Admin admin1 = new Admin("Admin001");
	        stationA.addAdmin(admin1.getAdminId());

	        ChargingStation stationB = new ChargingStation("StationB");
	        Admin admin2 = new Admin("Admin002");
	        stationB.addAdmin(admin2.getAdminId());

	        // users can add themselves to the queue of a charging station
	        user1.addToQueue(stationA);
	        user2.addToQueue(stationB);
	        // but can't handle the queue or change the charging method if they try an exception is thrown
	        try {
	            stationA.setChargingMethod("Fast Charging", user1.getId());
	        } catch (IllegalAccessException e) {
	            System.out.println("this use is not an admin");
	            LogManager.logChargingStationFunctionality(user1.getId() + " is not an admin" + " at Charging Station " + stationA.getStationId());
	        }

	        // admin can change the charging method of a charging station
	        admin1.changeChargingMethod(stationA, "Fast Charging");
	        // admin can handle the queue of a charging station
	        admin1.handleQueue(stationA);
	        // but can't do this to another charging station, this will throw an exception
	        admin1.changeChargingMethod(stationB, "Fast Charging");
	    }
	    	
	    
	    private static void exchangeDateUsingDateExchanger() {
	        // 1. create a ByteDataExchanger and CharacterDataExchanger objects that can write and read data from files
	        DateExchanger byteDataExchanger = new ByteDataExchanger();
	        DateExchanger characterDataExchanger = new CharacterDataExchanger();

	        // 2. exchange data between the weatherService and changing station using files
	        ChargingStation chargingStation = new ChargingStation("CS-1");
	        // weather can be recorded in a file using byte or character streams
//	        WeatherService.recordWeather("weatherInfo/chargingStation1.txt", characterDataExchanger);
	        WeatherService.recordWeather("weatherInfo/chargingStation1.txt", byteDataExchanger);

	        // 3. sharing station can read weather from a file using byte or character streams and select the energy source based on it
	        chargingStation.checkWeather("weatherInfo/chargingStation1.txt", characterDataExchanger);
//	        chargingStation.checkWeather("weatherInfo/chargingStation1.txt", byteDataExchanger);
	    }


}
