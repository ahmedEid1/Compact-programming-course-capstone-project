package metadata;

public class CarMetadata {
    private final String id;
    private final String model;
    private final String chargingStationID;

    public CarMetadata(String id, String model, String chargingStationID) {
        this.id = id;
        this.model = model;
        this.chargingStationID = chargingStationID;
    }

    @Override
    public String toString() {
        return "CarMetadata{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", chargingStationID='" + chargingStationID + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getChargingStationID() {
        return chargingStationID;
    }
}
