package Airly;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {
    @Parameter
    private List<String> parameters = new ArrayList<>();
    @Parameter(names = "--latitude", description = "Syntax: [--latitude ][--longitude ][lat][long]")
    private Double latitude;
    @Parameter(names = "--longitude", description = "Syntax: [--latitude ][--longitude ][lat][long]")
    private Double longitude;
    @Parameter(names = "--history")
    private boolean history = false;
    @Parameter(names = "--api-key", description = "Syntax: [--api-key ][apikey]")
    private String apikey;
    @Parameter(names = "--sensor-id", description = "Syntax: [--sensor-id ][sensorid]")
    private String sensorid;

    public List<String> getParameters() {
        return parameters;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean isHistory() {
        return history;
    }

    public String getApikey() {
        return apikey;
    }

    public String getSensorid() {
        return sensorid;
    }
}
