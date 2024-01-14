package test.logging.loggers;

import logging.SystemLogger;
import logging.LogLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SystemLoggerTest {
    @TempDir
    Path tempDir;

    @Test
    public void testLog() throws IOException {
        // Arrange
        SystemLogger logger = new SystemLogger("TestSystem", false, tempDir.toString());
        String todayDateString = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        String fileName = "[" + todayDateString + "]TestSystem_logs.txt";
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