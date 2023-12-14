package logging;

public class SystemLogger extends Logger {
    public SystemLogger(String name, boolean writeToConsole) {
        super(name, "logs/System", writeToConsole);
    }

    public void log(String message, LogLevel level) {
        super.log(message, level);
    }
}