package Airly;

import Airly.Wrappers.FullMeasurement;
import com.google.gson.Gson;

import java.util.Map;

public class UserInterface {
    private final String apikey;
    private final String baseURL;
    private final String mode;
    private final Map<String, String> parameters;

    private FullMeasurement measurement = null;

    public UserInterface(String apikey, String baseURL, String mode, Map<String, String> parameters){
        this.apikey = apikey;
        this.baseURL = baseURL;
        this.parameters = parameters;
        this.mode = mode;
        getInfo();

        if(this.measurement == null){
            ErrorHandler.exitOnError(Error.DATA_ERROR);
        }
    }

    public void getCurrentMeasurements(){
        System.out.println(new Visualizer(this.measurement).getCurrentMeasurements());
    }

    public void getHistory(){
        System.out.println(new Visualizer(this.measurement).getHistory());
    }

    private void getInfo(){
        String url = urlBuilder(this.baseURL, this.mode, this.parameters);

        HttpUrlConnector connector = new HttpUrlConnector("apikey", apikey, url);
        connector.sendGet();

        Gson gson = new Gson();
        this.measurement = gson.fromJson(connector.getOutput(), FullMeasurement.class);
    }

    private String urlBuilder(String baseURL, String mode, Map<String, String> parameters){
        StringBuilder result = new StringBuilder();
        result.append(baseURL)
                .append(mode)
                .append("?");

        for(Map.Entry<String, String> parameter : parameters.entrySet()){
            result.append(parameter.getKey())
                    .append("=")
                    .append(parameter.getValue())
                    .append("&");
        }
        return result.toString();
    }

}
