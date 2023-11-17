package charingStations.exceptions;

public class OccupiedException extends ChargingException {
    public OccupiedException(String message) {
        super(message);
    }

    public OccupiedException(String message, Throwable cause) {
        super(message, cause);
    }
}