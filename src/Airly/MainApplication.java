package Airly;
import com.beust.jcommander.JCommander;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainApplication {
    private static final String baseURL = "https://airapi.airly.eu/v1";
    private static String API_KEY;
    public static void main(String[] args) throws Exception {
        Args arguments = new Args();
        JCommander jCommander = new JCommander(arguments);
        jCommander.setProgramName("Airly console client");

        try{
            jCommander.parse(args);
        }catch(Exception e){
            System.out.println(e.getMessage());
            jCommander.usage();
            System.exit(0);
        }

        Map<String, String> parameters = new LinkedHashMap<>();
        String mode = "";

        if(arguments.getLatitude() != null && arguments.getLongitude() != null && arguments.getSensorid() == null){
            parameters.put("latitude", arguments.getLatitude().toString());
            parameters.put("longitude", arguments.getLongitude().toString());
            mode = Mode.MAPPOINT;
        }
        else if(arguments.getLatitude() == null && arguments.getLongitude() == null && arguments.getSensorid() != null){
            parameters.put("sensorId", arguments.getSensorid().toString());
            mode = Mode.SENSOR;
        }
        else {
            System.out.println("You can use only two modes" +
                               "\n-MAPPOINT mode                                            [--latitude ][--longitude ][lat][long]" +
                               "\n-SENSOR mode                                              [--sensorid ][sensorid]" +
                               "\n-OPTIONALLY (if not specified in environment variable)    [--api-key][apikey]\n");
            jCommander.usage();
            System.exit(0);
        }

        try {
            API_KEY = System.getenv("API_KEY");
        }catch(SecurityException e){
            System.out.println("ERROR: Cannot access to environment variable");
            System.exit(0);
        }
        if(arguments.getApikey() != null && API_KEY == null)
            API_KEY = arguments.getApikey();

        if(arguments.isHistory())
            new UserInterface(API_KEY, baseURL, mode, parameters).getHistory();
        else
            new UserInterface(API_KEY, baseURL, mode, parameters).getCurrentMeasurements();

    }
}
