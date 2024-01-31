package logging;

import chargingSimulation.ChargingSimulationUI;

public class EnergySourceLogger extends Logger {
    private ChargingSimulationUI ui;
    private String stationName;
    private String energySourceName;

    public EnergySourceLogger(String stationName, String energySourceName, boolean writeToConsole) {
        super(energySourceName + "_" + stationName, "logs/EnergySources/" , writeToConsole);
        this.stationName = stationName;
        this.energySourceName = energySourceName;
    }

    public EnergySourceLogger(String stationName, String energySourceName, boolean writeToConsole, String baseFolder) {
        super(energySourceName + "_" + stationName, baseFolder , writeToConsole);
        this.stationName = stationName;
        this.energySourceName = energySourceName;
    }

    public void log(String message, LogLevel level) {
        super.log(message, level);
        if (ui != null) {
            System.out.println(this.stationName + "_" + this.energySourceName + ": " + message);
            ui.appendLog(this.stationName + "_" + this.energySourceName, message);
        }
    }

    public void setUi(ChargingSimulationUI ui) {
        this.ui = ui;
    }
}
