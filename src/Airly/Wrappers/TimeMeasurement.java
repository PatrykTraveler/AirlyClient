package Airly.Wrappers;

public class TimeMeasurement {
    private Measurements measurements;
    private String fromDateTime;
    private String tillDateTime;

    public Measurements getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Measurements measurements) {
        this.measurements = measurements;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getTillDateTime() {
        return tillDateTime;
    }

    public void setTillDateTime(String tillDateTime) {
        this.tillDateTime = tillDateTime;
    }
}

