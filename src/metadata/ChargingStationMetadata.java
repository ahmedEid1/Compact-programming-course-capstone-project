package metadata;

public class ChargingStationMetadata {
    private final String id;
    private final String location;
    private final String energySource;

    public ChargingStationMetadata(String id, String location, String energySource) {
        this.id = id;
        this.location = location;
        this.energySource = energySource;
    }

    @Override
    public String toString() {
        return "ChargingStationMetadata{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", energySource='" + energySource + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getEnergySource() {
        return energySource;
    }
}
