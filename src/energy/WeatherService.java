package energy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WeatherService {
    private static final String WEATHER_FILE_PATH = "weather/weather_conditions.txt";

    public static void writeWeatherConditions() {
        // Generate random weather conditions
        boolean isSunny = Math.random() < 0.5;
        boolean isWindy = Math.random() < 0.5;
        boolean isCold = Math.random() < 0.5;

        // if folder doesn't exist, create it
        File folder = new File("weather");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // if file doesn't exist, create it
        File file = new File(WEATHER_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        try (FileWriter writer = new FileWriter(WEATHER_FILE_PATH, true)) {
//            writer.append(String.format("%b,%b,%b", isSunny, isWindy, isCold));
            // append to the end of the file
            writer.append(String.format("%b,%b,%b\n", isSunny, isWindy, isCold));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readWeatherConditions() {
        File file = new File(WEATHER_FILE_PATH);
        try {
            // If the file doesn't exist, assume default conditions
            if (!file.exists()) {
                return "true,true,true";
            }
//            read the last line of the file
            java.util.Scanner scanner = new java.util.Scanner(file);
            String lastLine = "";
            while (scanner.hasNextLine()) {
                lastLine = scanner.nextLine();
            }
            scanner.close();
            return lastLine;

        } catch (IOException e) {
            e.printStackTrace();
            return "true,true,true"; // Assume default conditions in case of an error
        }
    }
}