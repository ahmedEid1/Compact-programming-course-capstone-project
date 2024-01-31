package logging;

import chargingSimulation.ChargingSimulationUI;

public class SystemLogger extends Logger {

    private ChargingSimulationUI ui;
    private String name;

    public SystemLogger(String name, boolean writeToConsole) {
        super(name, "logs/System", writeToConsole);
        this.name = name;
    }
    public SystemLogger(String name, boolean writeToConsole, String baseFolder) {
        super(name, baseFolder, writeToConsole);
        this.name = name;
    }

    public void log(String message, LogLevel level) {
        super.log(message, level);
        if (ui != null) {
            ui.appendLog(name, message);
        }
    }

    public void setUi(ChargingSimulationUI ui) {
        this.ui = ui;
    }
}
