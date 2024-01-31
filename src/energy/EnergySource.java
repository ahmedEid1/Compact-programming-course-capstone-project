package energy;

import chargingSimulation.ChargingSimulationUI;
import logging.EnergySourceLogger;
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

    private EnergySourceLogger logger;

    public EnergySource(EnergySourceType type, ReservedBattery reservedBattery, String stationName) {
        this.isWorking = true;
        this.lock = new ReentrantLock();
        this.type = type;
        this.logger = new EnergySourceLogger(stationName, type.toString(), true);

        // Set up a scheduled executor service with one thread and schedule the checkWeatherCondition method to run every 1 minute
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::checkWeatherCondition, 0, 10, TimeUnit.SECONDS);


        // Start a thread to charge the reserved battery, if the energy source is working
        Thread chargeThread = new Thread(() -> {
            while (true) {
                if (isWorking()) {
                    reservedBattery.charge(5, type.toString());
                    logger.log("Charging reserved battery with 5 energy units from " + type + " energy source.", LogLevel.INFO);
                }

                try {
                    Thread.sleep(5000);
                    logger.log("Waiting for 5 seconds.", LogLevel.INFO);
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
            logger.log("Checking if " + type + " energy source is working.", LogLevel.INFO);
            logger.log("Is " + type + " energy source working? " + isWorking, LogLevel.INFO);
            return isWorking;
        } finally {
            lock.unlock();
        }
    }

    private void checkWeatherCondition() {
        String[] conditions = WeatherService.readWeatherConditions().split(",");
        boolean isSunny = Boolean.parseBoolean(conditions[0]);
        boolean isWindy = Boolean.parseBoolean(conditions[1]);
        boolean isCold = Boolean.parseBoolean(conditions[2]);


        try {
            lock.lock();
            logger.log("Checking weather conditions for " + type + " energy source.", LogLevel.INFO);
            switch (type) {
                case SOLAR:
                    if (isSunny) {
                        startWorking();
                        logger.log("Solar energy source is working because it is sunny.", LogLevel.INFO);
                    } else {
                        stopWorking();
                        logger.log("Solar energy source is not working because it is not sunny.", LogLevel.INFO);
                    }
                    break;
                case WIND:
                    if (isWindy) {
                        startWorking();
                        logger.log("Wind energy source is working because it is windy.", LogLevel.INFO);
                    } else {
                        stopWorking();
                        logger.log("Wind energy source is not working because it is not windy.", LogLevel.INFO);
                    }
                    break;
                case GAS:
                    if (!isCold) {
                        startWorking();
                        logger.log("Gas energy source is working because it is not cold.", LogLevel.INFO);
                    } else {
                        stopWorking();
                        logger.log("Gas energy source is not working because it is cold.", LogLevel.INFO);
                    }
                    break;
            }
        } finally {
            lock.unlock();
        }
    }

    private void startWorking() {
        logger.log("Starting " + type + " energy source.", LogLevel.INFO);
        isWorking = true;
    }

    private void stopWorking() {
        logger.log("Stopping " + type + " energy source.", LogLevel.INFO);
        isWorking = false;
    }

    public void setUi(ChargingSimulationUI ui) {
        logger.setUi(ui);
    }

    public EnergySourceType getType() {
        return type;
    }
}
