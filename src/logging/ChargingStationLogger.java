package logging;

import chargingSimulation.ChargingSimulationUI;

public class ChargingStationLogger extends Logger {
    private ChargingSimulationUI ui;
    private String stationName;

    public ChargingStationLogger(String stationName, boolean writeToConsole) {
        super("charging_station_" + stationName, "logs/ChargingStations", writeToConsole);
        this.stationName = stationName;
    }

    public ChargingStationLogger(String stationName, boolean writeToConsole, String baseFolderPath) {
        super("charging_station_" + stationName, baseFolderPath, writeToConsole);
        this.stationName = stationName;
    }

    public void log(String message, LogLevel level) {
        super.log(message, level);
        if (ui != null) {
            ui.appendLog(this.stationName, message);
        }
    }

    public void setUi(ChargingSimulationUI ui) {
        this.ui = ui;
    }
}
