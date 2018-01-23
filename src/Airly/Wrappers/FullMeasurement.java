package Airly.Wrappers;

import java.util.List;

public class FullMeasurement {
    private Measurements currentMeasurements;
    private List<TimeMeasurement> history;
    private List<TimeMeasurement> forecast;

    public Measurements getCurrentMeasurements() { return currentMeasurements; }

    public void setCurrentMeasurements(Measurements currentMeasurements) { this.currentMeasurements = currentMeasurements; }

    public List<TimeMeasurement> getHistory() {
        return history;
    }

    public void setHistory(List<TimeMeasurement> history) {
        this.history = history;
    }

    public List<TimeMeasurement> getForecast() {
        return forecast;
    }

    public void setForecast(List<TimeMeasurement> forecast) {
        this.forecast = forecast;
    }
}
