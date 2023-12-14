package logging.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*
 * LogSearchUtility is a utility class that provides methods to search for logs in the log folder
 * based on name/date or any combination of the two.
 */
public class LogSearchUtility {

    // ==================== Public Methods ====================
    // filter logs by equipment name and date
    public static List<String> searchLogs(String equipmentName, String dateString, String baseFolderPath) {
        List<String> matches = new ArrayList<>();
        File baseFolder = new File(baseFolderPath);

        if (!baseFolder.exists() || !baseFolder.isDirectory()) {
            System.out.println("Base folder does not exist or is not a directory.");
            return matches;
        }

        File[] equipmentFolders = baseFolder.listFiles();

        if (equipmentFolders != null) {
            for (File equipmentFolder : equipmentFolders) {
                if (equipmentFolder.isDirectory()) {
                    File[] dateFolders = equipmentFolder.listFiles();
                    if (dateFolders != null) {
                        for (File dateFolder : dateFolders) {
                            if (dateFolder.isDirectory() && dateFolder.getName().equals(dateString)) {
                                matches.addAll(searchAndCollectMatchingLogs(equipmentName, dateFolder));
                            }
                        }
                    }
                }
            }
        }

        return matches;
    }

    // filter logs by equipment name
    public static List<String> searchLogsByEquipmentName(String equipmentName, String baseFolderPath) {
        List<String> matches = new ArrayList<>();
        File baseFolder = new File(baseFolderPath);

        if (!baseFolder.exists() || !baseFolder.isDirectory()) {
            System.out.println("Base folder does not exist or is not a directory.");
            return matches;
        }

        File[] equipmentFolders = baseFolder.listFiles();

        if (equipmentFolders != null) {
            for (File equipmentFolder : equipmentFolders) {
                if (equipmentFolder.isDirectory()) {
                    File[] dateFolders = equipmentFolder.listFiles();
                    if (dateFolders != null) {
                        for (File dateFolder : dateFolders) {
                            if (dateFolder.isDirectory()) {
                                matches.addAll(searchAndCollectMatchingLogs(equipmentName, dateFolder));
                            }
                        }
                    }
                }
            }
        }

        return matches;
    }

    // filter logs by date
    public static List<String> searchLogsByDate(String dateString, String baseFolderPath) {
        List<String> matches = new ArrayList<>();
        File baseFolder = new File(baseFolderPath);

        if (!baseFolder.exists() || !baseFolder.isDirectory()) {
            System.out.println("Base folder does not exist or is not a directory.");
            return matches;
        }

        File[] equipmentFolders = baseFolder.listFiles();

        if (equipmentFolders != null) {
            for (File equipmentFolder : equipmentFolders) {
                if (equipmentFolder.isDirectory()) {
                    File[] dateFolders = equipmentFolder.listFiles();
                    if (dateFolders != null) {
                        for (File dateFolder : dateFolders) {
                            if (dateFolder.isDirectory()) {
                                if (dateFolder.getName().equals(dateString)) {
                                    matches.addAll(searchAndCollectAllLogs(dateFolder));
                                }
                            }
                        }
                    }
                }
            }
        }

        return matches;
    }

    // ==================== End Public Methods ====================

    // ==================== Helper Methods ====================

    // searchAndCollectMatchingLogs is used by searchLogs and searchLogsByEquipmentName to
    // get all the logs files in a date folder that match a equipment name
    private static List<String> searchAndCollectMatchingLogs(String equipmentName, File dateFolder) {
        List<String> matches = new ArrayList<>();
        File[] logFiles = dateFolder.listFiles();

        if (logFiles != null) {
            for (File logFile : logFiles) {
                if (logFile.isFile()) {
                    String logFileName = logFile.getName();
                    String regex = "\\[" + Pattern.quote(dateFolder.getName()) + "\\].*" + Pattern.quote(equipmentName) + ".*_logs.txt";

                    if (logFileName.matches(regex)) {
                        matches.add(logFile.getAbsolutePath());
                    }
                }
            }
        }

        return matches;
    }

    // searchAndCollectAllLogs is used by searchLogsByDate to
    // collect all logs files in a date folder that match the date
    private static List<String> searchAndCollectAllLogs(File dateFolder) {
        List<String> matches = new ArrayList<>();
        File[] logFiles = dateFolder.listFiles();

        if (logFiles != null) {
            for (File logFile : logFiles) {
                if (logFile.isFile()) {
                    matches.add(logFile.getAbsolutePath());
                }
            }
        }

        return matches;
    }

    // ==================== End Helper Methods ====================
}