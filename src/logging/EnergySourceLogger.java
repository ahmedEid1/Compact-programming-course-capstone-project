package logging;

public class EnergySourceLogger extends Logger {
    public EnergySourceLogger(String stationName, String energySourceName, boolean writeToConsole) {
        super(energySourceName + "_" + stationName, "logs/EnergySources/" , writeToConsole);
    }

    public EnergySourceLogger(String stationName, String energySourceName, boolean writeToConsole, String baseFolder) {
        super(energySourceName + "_" + stationName, baseFolder , writeToConsole);
    }

    public void log(String message, LogLevel level) {
        super.log(message, level);
    }
}
