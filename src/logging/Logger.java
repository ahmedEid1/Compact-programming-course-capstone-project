package logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    protected final String loggerName;
    protected final String baseFolderPath;
    private final boolean writeToConsole;
    private Date testDay;

    public Logger(String loggerName, String baseFolderPath, boolean writeToConsole) {
        this.loggerName = loggerName;
        this.baseFolderPath = baseFolderPath;
        this.writeToConsole = writeToConsole;
        createFoldersAndFile();
    }


    // TODO: remove after testing
    // For testing purpose
    public Logger(String loggerName, String baseFolderPath, boolean writeToConsole, Date date) {
        this.loggerName = loggerName;
        this.baseFolderPath = baseFolderPath;
        this.writeToConsole = writeToConsole;
        testDay = date;
        createFoldersAndFile(date);
    }

    protected void createFoldersAndFile() {
        String todayDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Path logFilePath = Paths.get(baseFolderPath, todayDateString, "[" + todayDateString + "]" + loggerName + "_logs.txt");

        File logFile = logFilePath.toFile();
        File parentDirectory = logFile.getParentFile();

        if (!parentDirectory.exists()) {
            if (!parentDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create parent directories for log file.");
            }
        }

        if (!logFile.exists()) {
            try {
                if (!logFile.createNewFile()) {
                    throw new RuntimeException("Failed to create log file.");
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating log file.", e);
            }
        }
    }

    protected String formatMessage(LogLevel level, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return "[" + timestamp + "] [" + level + "] " + message;
    }

    public void log(String message, LogLevel level) {
        System.out.println("Logging message: " + message);
        writeToFile(message, level);
        if (writeToConsole) {
            writeToConsole(message);
        }
    }

    protected void writeToFile(String message, LogLevel level) {
        String formattedMessage = formatMessage(level, message);
        try (PrintWriter writer = new PrintWriter(new FileWriter(getTodayLogFilePath().toString(), true))) {
            writer.println(formattedMessage);
            System.out.println("Log written to file.");
            System.out.println("Log file path: " + getTodayLogFilePath().toString());
        } catch (IOException e) {
            throw new RuntimeException("Error writing to log file.", e);
        }

    }

    protected void writeToConsole(String formattedMessage) {
        System.out.println(formattedMessage);
    }

    private Path getTodayLogFilePath() {
        if (testDay != null) {
            String testDayString = new SimpleDateFormat("yyyy-MM-dd").format(testDay);
            return Paths.get(baseFolderPath, testDayString, "[" + testDayString + "]" + loggerName + "_logs.txt");
        }

        String todayDateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return Paths.get(baseFolderPath, todayDateString, "[" + todayDateString + "]" + loggerName + "_logs.txt");
    }

    // For testing purpose
    protected void createFoldersAndFile(Date date) {
        String todayDateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Path logFilePath = Paths.get(baseFolderPath, todayDateString, "[" + todayDateString + "]" + loggerName + "_logs.txt");

        File logFile = logFilePath.toFile();
        File parentDirectory = logFile.getParentFile();

        if (!parentDirectory.exists()) {
            if (!parentDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create parent directories for log file.");
            }
        }

        if (!logFile.exists()) {
            try {
                if (!logFile.createNewFile()) {
                    throw new RuntimeException("Failed to create log file.");
                }
            } catch (IOException e) {
                throw new RuntimeException("Error creating log file.", e);
            }
        }
    }

}
