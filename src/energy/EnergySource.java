package energy;

import logging.LogLevel;
import logging.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * energy.EnergySource: A class that represents an energy source.
 *     An energy source can be solar, wind, or gas.
 *     Each energy source has a lock to prevent multiple threads from accessing it at the same time.
 *     it has 2 threads:
 *      1. A thread that charges the reserved battery every 5 seconds, if the energy source is working.
 *     2. A thread that checks the weather conditions every 30 seconds and starts or stops the energy source accordingly.
 */
public class EnergySource {
    private final Lock lock;
    private final EnergySourceType type;
    private boolean isWorking;

    private Logger logger;

    public EnergySource(EnergySourceType type, ReservedBattery reservedBattery, String stationName) {
        this.isWorking = true;
        this.lock = new ReentrantLock();
        this.type = type;
        this.logger = new Logger("logs/EnergySources/" + stationName + "/" + type + ".log", true);

        // Set up a scheduled executor service with one thread and schedule the checkWeatherCondition method to run every 1 minute
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::checkWeatherCondition, 0, 30, TimeUnit.SECONDS);


        // Start a thread to charge the reserved battery, if the energy source is working
        Thread chargeThread = new Thread(() -> {
            while (true) {
                if (isWorking()) {
                    reservedBattery.charge(5, type.toString());
                    logger.log(LogLevel.INFO, "Charging reserved battery with 5 energy units from " + type + " energy source.");
                }

                try {
                    Thread.sleep(5000);
                    logger.log(LogLevel.INFO, "Waiting for 5 seconds.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        chargeThread.start();
    }

    public boolean isWorking() {
        try {
            lock.lock();
            logger.log(LogLevel.INFO, "Checking if " + type + " energy source is working.");
            logger.log(LogLevel.INFO, "Is " + type + " energy source working? " + isWorking);
            return isWorking;
        } finally {
            lock.unlock();
        }
    }

    private void checkWeatherCondition() {
        // Simulated logic to check weather conditions
        boolean isSunny = Math.random() > 0.5;
        boolean isWindy = Math.random() > 0.5;
        boolean isCold = Math.random() > 0.5;

        try {
            lock.lock();
            logger.log(LogLevel.INFO, "Checking weather conditions for " + type + " energy source.");
            switch (type) {
                case SOLAR:
                    if (isSunny) {
                        startWorking();
                        logger.log(LogLevel.INFO, "Solar energy source is working because it is sunny.");
                    } else {
                        stopWorking();
                        logger.log(LogLevel.INFO, "Solar energy source is not working because it is not sunny.");
                    }
                    break;
                case WIND:
                    if (isWindy) {
                        startWorking();
                        logger.log(LogLevel.INFO, "Wind energy source is working because it is windy.");
                    } else {
                        stopWorking();
                        logger.log(LogLevel.INFO, "Wind energy source is not working because it is not windy.");
                    }
                    break;
                case GAS:
                    if (!isCold) {
                        startWorking();
                        logger.log(LogLevel.INFO, "Gas energy source is working because it is not cold.");
                    } else {
                        stopWorking();
                        logger.log(LogLevel.INFO, "Gas energy source is not working because it is cold.");
                    }
                    break;
            }
        } finally {
            lock.unlock();
        }
    }

    private void startWorking() {
        logger.log(LogLevel.INFO, "Starting " + type + " energy source.");
        isWorking = true;
    }

    private void stopWorking() {
        logger.log(LogLevel.INFO, "Stopping " + type + " energy source.");
        isWorking = false;
    }
}
