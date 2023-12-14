package logging.search;

import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LogSearchCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to LogSearchCLI!");

        System.out.print("Enter equipment name (or press Enter to skip): ");
        String equipmentName = scanner.nextLine().trim();

        String date = getValidDate(scanner);

        // Search for log files
        List<String> matches;
        // if the user only entered the equipment
        if (!equipmentName.isEmpty() && date.isEmpty()) {
            matches = LogSearchUtility.searchLogsByEquipmentName(equipmentName, "logs");
        }
        // if the user only entered the date
        else if (equipmentName.isEmpty() && !date.isEmpty()) {
            matches = LogSearchUtility.searchLogsByDate(date, "logs");
        }
        // if the user entered both
        else if (!equipmentName.isEmpty() && !date.isEmpty()) {
            matches = LogSearchUtility.searchLogs(equipmentName, date, "logs");
        }
        // if the user entered nothing
        else {
            System.out.println("No search criteria entered. Exiting...");
            return;
        }

        // print the matches and allow the user to choose which one to open
        System.out.println("Found " + matches.size() + " matches:");
        System.out.println("that match the search criteria which are:");
        System.out.println("Equipment name: " + equipmentName);
        System.out.println("Date: " + date);
        System.out.println("========================================");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + ". " + matches.get(i));
        }
        System.out.println("========================================");
        // ask the user to choose which one to open
        System.out.print("Enter the number of the log file you want to open (or press Enter to skip): ");
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
            int index = Integer.parseInt(input) - 1;
            String logFilePath = matches.get(index);
            openLogFile(logFilePath);
        }

        scanner.close();
    }

    private static void openLogFile(String logFile) {
        // Implement logic to open log files in the default text editor
        // You can use Java's Desktop class for this purpose.
        // See: https://docs.oracle.com/en/java/javase/14/docs/api/java.desktop/java/awt/Desktop.html
         try {
             Desktop.getDesktop().edit(new File(logFile));
         } catch (Exception e) {
             System.out.println("Error opening log file.");
         }
    }

    private static String getValidDate(Scanner scanner) {
        while (true) {
            System.out.print("Enter date in yyyy-MM-dd format (or press Enter to skip): ");
            String inputDate = scanner.nextLine().trim();

            if (inputDate.isEmpty()) {
                return ""; // Skip date entry
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            try {
                Date parsedDate = dateFormat.parse(inputDate);
                return dateFormat.format(parsedDate); // Return formatted date
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
    }
}