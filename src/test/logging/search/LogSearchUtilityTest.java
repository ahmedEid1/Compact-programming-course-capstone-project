package test.logging.search;

import logging.search.LogSearchUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogSearchUtilityTest {
    @TempDir
    Path tempDir;

    @Test
    public void testSearchLogs() throws IOException {
        // Arrange
        createDummyLogFiles("Equipment1", "20220101");

        // Act
        List<String> logs = LogSearchUtility.searchLogs("Equipment1", "20220101", tempDir.toString());

        // Assert
        assertEquals(1, logs.size());
    }

    @Test
    public void testSearchLogsByEquipmentName() throws IOException {
        // Arrange
        createDummyLogFiles("Equipment1", "20220101");

        // Act
        List<String> logs = LogSearchUtility.searchLogsByEquipmentName("Equipment1", tempDir.toString());

        // Assert
        assertEquals(1, logs.size());
    }

    @Test
    public void testSearchLogsByDate() throws IOException {
        // Arrange
        createDummyLogFiles("Equipment1", "20220101");

        // Act
        List<String> logs = LogSearchUtility.searchLogsByDate("20220101", tempDir.toString());

        // Assert
        assertEquals(1, logs.size());
    }

    private void createDummyLogFiles(String equipmentName, String dateString) throws IOException {
        // Arrange
        Path equipmentFolder = Files.createDirectory(tempDir.resolve(equipmentName));
        Path dateFolder = Files.createDirectory(equipmentFolder.resolve(dateString));

        // Act
        Files.createFile(dateFolder.resolve("[" + dateString + "]_" + equipmentName + "_logs.txt"));
    }
}