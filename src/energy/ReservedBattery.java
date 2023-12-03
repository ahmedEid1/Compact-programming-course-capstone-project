package energy;

import logging.LogLevel;
import logging.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    * Task: Simulate multithread charging of the reserved batteries from several energy sources.
    * 	A reserved battery: A spare battery that are used to store energy from various sources.
    * this class was implemented to support the charging of the reserved battery from several sources.
    * the lock is used to prevent the reserved battery from being charged and discharged at the same time.
        and to prevent the energy level from being read while it is being charged or discharged.
        and prevent 2 threads from charging the reserved battery at the same time.
 */
public class ReservedBattery {
    private final String stationName;
    private int energyLevel;
    private final int MAX_CAPACITY = 100;
    private Lock lock;

    private Logger logger;

    public ReservedBattery(String stationName) {
        this.stationName = stationName;
        this.energyLevel = 0;
        this.lock = new ReentrantLock();
        this.logger = new Logger("logs/ReservedBatteries/" + stationName + "/reserved_battery.log", true);
    }

    public int getEnergyLevel() {
        try {
            lock.lock();
            logger.log(LogLevel.INFO, "locking the reserved battery to get energy level");
            logger.log(LogLevel.INFO, "energy level is " + energyLevel);
            return energyLevel;
        } finally {
            lock.unlock();
            logger.log(LogLevel.INFO, "unlocking the reserved battery after getting energy level");
        }
    }

    public void charge(int energy, String sourceName) {
        try {
            lock.lock();
            logger.log(LogLevel.INFO, "---------------------------------------------------");
            logger.log(LogLevel.INFO, "locking the reserved battery to charge");
            logger.log(LogLevel.INFO, "charging " + energy + " from " + sourceName);
            if (energyLevel >= MAX_CAPACITY)
                logger.log(LogLevel.INFO, "the reserved battery is full, cannot charge more energy");
            energyLevel = Math.min(MAX_CAPACITY, energyLevel + energy);
            logger.log(LogLevel.INFO, "energy level is " + energyLevel);
        } finally {
            lock.unlock();
            logger.log(LogLevel.INFO, "unlocking the reserved battery after charging");
            logger.log(LogLevel.INFO, "---------------------------------------------------");
        }
    }

    public void discharge(int energy) {
        try {
            lock.lock();
            logger.log(LogLevel.INFO, "---------------------------------------------------");
            logger.log(LogLevel.INFO, "locking the reserved battery to discharge");
            logger.log(LogLevel.INFO, "discharging " + energy);
            energyLevel = Math.max(0, energyLevel - energy);
        } finally {
            lock.unlock();
            logger.log(LogLevel.INFO, "unlocking the reserved battery after discharging");
            logger.log(LogLevel.INFO, "---------------------------------------------------");
        }
    }
}
