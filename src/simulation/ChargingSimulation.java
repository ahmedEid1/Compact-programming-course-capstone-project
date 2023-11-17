package simulation;

import charingStations.ChargingLocation;
import logging.BaseLogger;

public class ChargingSimulation {

	public static void main(String[] args) {
		// 1. handling multiple exception
		ChargingLocation chargingLocation = new ChargingLocation();
        chargingLocation.chargeCar(null);
		
		// 2. catching an exception chaining it to another exception and re-throwing it
		// TODO: change occupied to false in the chargingLocation
	
		// 3. resource management using Try with resources
        BaseLogger logger = new BaseLogger();
        logger.log("Hello World!");

	}

}
