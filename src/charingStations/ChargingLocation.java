package charingStations;

import car.Car;
import exceptions.ChargingException;
import exceptions.OccupiedException;


// Task: Handling Multiple Exceptions
// Task: Re-throwing Exceptions
// Task: Chaining Exceptions
public class ChargingLocation {
    private boolean occupied = false;

    public void chargeCar(Car car) {
        try {
            if (!occupied) {
                try {
                    // TODO: try charging the car
                    throw new Exception("unexcepted random exception");
                } catch (Exception e) {
                    // wrap the original exception in a ChargingException and re-throw it
                	System.out.println("an exception has been caught annd will be re throwen");    	
                    throw new OccupiedException("Charging failed.", e);
                }
            } else {
                throw new Exception("Charging location is occupied.");
            }
        } catch (OccupiedException e) {
            System.err.println("OccupiedException: " + e.getMessage());
        } catch (ChargingException e) {
            System.err.println("ChargingException: " + e.getMessage());
            if (e.getCause() != null)
            	System.err.println("Cause: " + e.getCause().getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Exception: " + e.getMessage());
        }
    }
}
