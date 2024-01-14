package logging;

public class ChargingStationLogger extends Logger {
    public ChargingStationLogger(String stationName, boolean writeToConsole) {
        super("charging_station_" + stationName, "logs/ChargingStations", writeToConsole);
    }

    public ChargingStationLogger(String stationName, boolean writeToConsole, String baseFolderPath) {
        super("charging_station_" + stationName, baseFolderPath, writeToConsole);
    }

    public void log(String message, LogLevel level) {
        super.log(message, level);
    }
}
