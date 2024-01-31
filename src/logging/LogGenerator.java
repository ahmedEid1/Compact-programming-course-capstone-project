package logging;

import java.util.Calendar;
import java.util.Date;

public class LogGenerator {

    public static void main(String[] args) {
        // 1. for some charging stations, generate logs for the last x days
        generateLogsForLastXDays("charging_station_1", "logs/ChargingStations", 5);
        generateLogsForLastXDays("charging_station_2", "logs/ChargingStations", 5);

        // for the EnergySourceLogger, generate logs for the last x days
        generateLogsForLastXDays("energy_source_1_charging_station_1", "logs/EnergySources", 5);
        generateLogsForLastXDays("energy_source_2_charging_station_2", "logs/EnergySources", 5);

        // for the entire system, generate logs for the last x days
        generateLogsForLastXDays("system_part_1", "logs/System", 5);
        generateLogsForLastXDays("system_part_2", "logs/System", 5);
    }

    public static void generateLogsForLastXDays(String loggerName, String baseFolderPath, int lastDays) {
        // loop x times, create a Date object for each day, and call the Logger constructor
        for (int i = 0; i < lastDays; i++) {
            Date date = getDateForLastXDays(i);
            Logger logger = new Logger(loggerName, baseFolderPath, false, date);
            // generate random number of logs for each day
            generateLogs(logger);

            // archive and delete old logs
            logger.archiveLogs(2);
            logger.deleteOldLogs(2);
        }

    }

    private static void generateLogs(Logger logger) {
        int randomNumberOfLogs = 10;
        for (int j = 0; j < randomNumberOfLogs; j++) {
            logger.log("This is a test log message.", LogLevel.INFO);
            System.out.println("Log generated.");
        }
    }

    private static Date getDateForLastXDays(int date) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -date);
        return cal.getTime();
    }
}
