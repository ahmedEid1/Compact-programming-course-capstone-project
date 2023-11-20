package logging;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


// related task: managing the log files - create, move, delete, archive. Log files should be devided into classes – functionality of the system, functionality of a
//charging station, functionality of the energy
//management system.
// --------------------------------------------------
// Done: create a class LogManager with three static methods:
    // logSystemFunctionality,
    // logChargingStationFunctionality,
    // logEnergyManagementFunctionality
// that write log entries to three different files:
    // logs/system_log.txt
    // logs/charging_station_log.txt
    // logs/energy_management_log.txt
// also create a method moveLogsToArchive that moves the content of log files to an archive folder

public class LogManager {

    private static final String SYSTEM_LOG_FILE = "logs/system_log.txt";
    private static final String CHARGING_STATION_LOG_FILE = "logs/charging_station_log.txt";
    private static final String ENERGY_MANAGEMENT_LOG_FILE = "logs/energy_management_log.txt";

    public static void logSystemFunctionality(String logEntry) {
        logToFile(SYSTEM_LOG_FILE, logEntry);
    }

    public static void logChargingStationFunctionality(String logEntry) {
        logToFile(CHARGING_STATION_LOG_FILE, logEntry);
    }

    public static void logEnergyManagementFunctionality(String logEntry) {
        logToFile(ENERGY_MANAGEMENT_LOG_FILE, logEntry);
    }

    private static void logToFile(String fileName, String logEntry) {
        try {
            Path archiveFolder = Paths.get("logs");
            if (!Files.exists(archiveFolder)) {
                Files.createDirectory(archiveFolder);
            }

            // add the current timestamp to the log entry
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
            logEntry = timestamp + " - " + logEntry;
            Files.write(Paths.get(fileName), (logEntry + "\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // log the exception to the system log file
            System.out.println("Error while logging to file: " + e.getMessage());
            logSystemFunctionality(e.getMessage());
        }
    }

    private static void moveLogsToArchive(String logType) {
        // moving log files to an archive folder
        try {
            Path archiveFolder = Paths.get("log_archive");
            if (!Files.exists(archiveFolder)) {
                Files.createDirectory(archiveFolder);
            }

            if (Objects.equals(logType, "system")) {
                moveLogToArchive(SYSTEM_LOG_FILE, archiveFolder.resolve("system_log_archive.txt"));
            } else if (Objects.equals(logType, "charging_station")) {
                moveLogToArchive(CHARGING_STATION_LOG_FILE, archiveFolder.resolve("charging_station_log_archive.txt"));
            } else if (Objects.equals(logType, "energy_management")) {
                moveLogToArchive(ENERGY_MANAGEMENT_LOG_FILE, archiveFolder.resolve("energy_management_log_archive.txt"));
            } else {
                throw new IllegalArgumentException("Invalid log type.");
            }
        } catch (IOException e) {
            System.out.println("Error while creating archive folder: " + e.getMessage());
            logSystemFunctionality(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error while moving log file to archive: " + e.getMessage());
            logSystemFunctionality(e.getMessage());
        }
    }

    // archive system log file
    public static void moveSystemLogToArchive() {
        moveLogsToArchive("system");
    }

    // archive charging station log file
    public static void moveChargingStationLogToArchive() {
        moveLogsToArchive("charging_station");
    }

    // archive energy management log file
    public static void moveEnergyManagementLogToArchive() {
        moveLogsToArchive("energy_management");
    }

    private static void moveLogToArchive(String sourceFile, Path destinationFile) {
        try {
            if (!Files.exists(destinationFile)) {
                // If the destination file doesn't exist, create it and move the source file
                Files.createFile(destinationFile);
            }

            // Append the content of the source file to the destination file
            Files.write(destinationFile, Files.readAllBytes(Paths.get(sourceFile)), StandardOpenOption.APPEND);

            // Delete the content of the source file after successfully moving its content
            Files.write(Paths.get(sourceFile), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            // log the exception to the system log file
            logSystemFunctionality(e.getMessage());
        }
    }
}