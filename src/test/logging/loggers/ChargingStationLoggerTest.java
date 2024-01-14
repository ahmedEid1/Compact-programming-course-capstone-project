package test.logging.loggers;

import logging.ChargingStationLogger;
import logging.LogLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChargingStationLoggerTest {
    @TempDir
    Path tempDir;

    @Test
    public void testLog() throws IOException {
        // Arrange
        ChargingStationLogger logger = new ChargingStationLogger("Station1", false, tempDir.toString());
        String todayDateString = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        String fileName = "[" + todayDateString + "]charging_station_Station1_logs.txt";
        String expectedPath = tempDir.toString() + File.separator + todayDateString + File.separator + fileName;

        // Act
        logger.log("Test message", LogLevel.INFO);

        // Assert
        File logFile = new File(expectedPath);
        assertTrue(logFile.exists());

        List<String> lines = Files.readAllLines(logFile.toPath());
        assertTrue(lines.stream().anyMatch(line -> line.contains("Test message")));
    }
}
