package charingStations;

import dataexchange.interfaces.DateExchanger;
import logging.LogManager;
import users.User;

import java.util.LinkedList;
import java.util.Queue;

public class ChargingStation {
    private final String stationId;
    // admins
    private Queue<String> adminIdsQueue;
    private Queue<User> chargingQueue;
    private String chargingMethod;
    private String currentWeather;

    public ChargingStation(String stationId) {
        this.stationId = stationId;
        this.chargingQueue = new LinkedList<>();
        this.adminIdsQueue = new LinkedList<>();
        this.chargingMethod = "Default Charging Method";
    }

    public String getChargingMethod() {
        return chargingMethod;
    }

    public void setChargingMethod(String chargingMethod, String adminId) throws IllegalAccessException {
        // if not an admin, throw an exception
        if (!adminIdsQueue.contains(adminId)) {
            throw new IllegalAccessException("Not an admin");
        }
        this.chargingMethod = chargingMethod;
    }


    // a method that uses the DateExchanger interface to read the current weather: checkWeather()
    public String checkWeather(String filePath, DateExchanger dataExchanger) {
        // Read the current weather from the file
        String weatherInfos = dataExchanger.readFile(filePath);
        // get the last line of the file
        String[] weatherInfo = weatherInfos.split("\n");
        this.currentWeather = weatherInfo[weatherInfo.length - 1];

        selectEnergySourceBasedOnWeather(this.currentWeather);

        return this.currentWeather;
    }

    private void selectEnergySourceBasedOnWeather(String currentWeather) {
        // Make decisions based on the weather (simplified for the example)
        System.out.println("Charging Station Status Update:");
        System.out.println("Current Weather: " + currentWeather);

        // Assume the charging station has multiple energy sources
        if (currentWeather.contains("clear sky")) {
            System.out.println("Using solar panels");
        } else if (currentWeather.contains("wendy")) {
            System.out.println("Using wind turbines");
        } else {
            System.out.println("using fossil fuels");
        }
    }

    public void addToQueue(User user) {
        chargingQueue.add(user);
        LogManager.logChargingStationFunctionality("User " + user.getId() + " added to the queue.");
    }

    public void addAdmin(String adminId) {
        adminIdsQueue.add(adminId);
    }

    public String getStationId() {
        return stationId;
    }

    public Queue<User> getQueue(String adminId) throws IllegalAccessException {
        if (adminIdsQueue.contains(adminId)) {
           return chargingQueue;
        } else {
            throw new IllegalAccessException("Not an admin");
        }
    }
}
