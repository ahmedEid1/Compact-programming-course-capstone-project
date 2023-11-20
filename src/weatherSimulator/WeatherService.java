package weatherSimulator;

import dataexchange.interfaces.DateExchanger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class WeatherService {
    // to prevent creating instances of this class
    private WeatherService() {
    }

    public static void recordWeather(String fileName, DateExchanger dataExchangeClass) {
        // Generate weather information
        String weatherInfo = generateRandomWeatherInfo();

        // Record the current day, time, and weather
        String record = getCurrentDateTime() + " - Weather: " + weatherInfo;

        dataExchangeClass.writeToFile(fileName, record);
    }

    private static String generateRandomWeatherInfo() {
        String[] weatherConditions = {"clear sky", "wendy", "unsuitable for charging with renewable energy"};

        Random random = new Random();
        int randomIndex = random.nextInt(weatherConditions.length);

        return weatherConditions[randomIndex];
    }

    private static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
