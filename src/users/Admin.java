package users;

import charingStations.ChargingStation;
import logging.LogManager;

import java.util.Queue;

public class Admin {
    private String adminId;

    public Admin(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void changeChargingMethod(ChargingStation chargingStation, String newChargingMethod) {
        // Logic for an administrator to change the charging method of a charging station
        try {
            chargingStation.setChargingMethod(newChargingMethod, adminId);
            System.out.println("Charging method changed to " + newChargingMethod + " at Charging Station " + chargingStation.getStationId());
        } catch (IllegalAccessException e) {
            System.out.println("you are not an admin of this station");
            LogManager.logChargingStationFunctionality(adminId + " is not an admin" + " at Charging Station " + chargingStation.getStationId());
        }
    }

    public void handleQueue(ChargingStation chargingStation) {
        // Logic for an administrator to handle the charging queue
        try {
            Queue<User> q = chargingStation.getQueue(adminId);
            // TODO: logic to handle the queue and do different actions based on the queue and what the admin wants to do
        } catch (IllegalAccessException e) {
            System.out.println("Not an admin");
            LogManager.logChargingStationFunctionality(adminId + " is not an admin" + " at Charging Station " + chargingStation.getStationId());
        }
    }
}
