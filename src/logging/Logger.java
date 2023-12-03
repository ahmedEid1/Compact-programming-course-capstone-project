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
    private final String filePath;
    private final boolean writeToConsole;

    public Logger(String filePath, boolean writeToConsole) {
        this.filePath = filePath;
        this.writeToConsole = writeToConsole;
        createFoldersAndFile();
    }

    private void createFoldersAndFile() {
        Path path = Paths.get(filePath);
        File parentDirectory = path.toFile().getParentFile();

        if (!parentDirectory.exists()) {
            if (!parentDirectory.mkdirs()) {
                throw new RuntimeException("Failed to create parent directories for log file.");
            }
        }

        File logFile = path.toFile();

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

    public void log(LogLevel level, String message) {
        String formattedMessage = formatMessage(level, message);
        writeToFile(formattedMessage);
        if (writeToConsole) {
            writeToConsole(formattedMessage);
        }
    }

    private String formatMessage(LogLevel level, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        return "[" + timestamp + "] [" + level + "] " + message;
    }

    private void writeToFile(String formattedMessage) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(formattedMessage);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to log file.", e);
        }
    }

    private void writeToConsole(String formattedMessage) {
        System.out.println(formattedMessage);
    }
}
