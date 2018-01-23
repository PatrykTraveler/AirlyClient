package Airly;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import Airly.Wrappers.FullMeasurement;
import Airly.Wrappers.Measurements;
import Airly.Wrappers.TimeMeasurement;

import java.util.List;

public class Visualizer {
    private final String RESET = "\033[0m";  // Text Reset

    // Background Colors
    public final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE

    // Regular Colors
    private final String BLACK = "\033[0;30m";   // BLACK
    private final String RED = "\033[0;31m";     // RED
    private final String GREEN = "\033[0;32m";   // GREEN
    private final String YELLOW = "\033[0;33m";  // YELLOW
    private final String BLUE = "\033[0;34m";    // BLUE
    private final String PURPLE = "\033[0;35m";  // PURPLE
    private final String CYAN = "\033[0;36m";    // CYAN
    private final String WHITE = "\033[0;37m";   // WHITE

    // Bold Colors
    public final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN

    private final String LOGO = BLUE_BOLD +
            "\n" +
            "         `.-://++//:-.`        \n" +
            "     `-+oooo+++++ooooo:``     \n" +
            "   `:oo+:-`` .-:/+o+:..+o/`   \n" +
            "  .++-`  .:+o+//:-.`-/oooo+-  \n" +
            " -+.  -/+ooo+////++ooooooooo- \n" +
            "`- `:+ooooooooooooo+::---::+o.\n" +
            " ./oooooooooooooooo++:-:-:++o:\n" +
            ":ooooooooooooooooooooo/-/oooo/\n" +
            ":oooooooooo++++ooooooo/-/oooo:\n" +
            "`+oooooo+-` `` `-+oooo: :oooo.\n" +
            " -ooooo+` :+oo+: `+ooo: :ooo- \n" +
            "  -+ooo/ `oooooo. /ooo: :o+-  \n" +
            "   `:ooo. -+oo+-  /ooo: :/`   \n" +
            "     `:+o:.`  `..`/ooo-       \n" +
            "        `.:://+++//:.`        \n" +
            "------------------------------\n" +
            "     AIRLY CONSOLE CLIENT     \n" +
            "------------------------------\n" + RESET;


    private final FullMeasurement measurement;

    public Visualizer(FullMeasurement measurement){
        this.measurement = measurement;
    }

    public String getCurrentMeasurements(){
        Measurements currentMeasurements = this.measurement.getCurrentMeasurements();
        StringBuilder output = new StringBuilder();

        String caiq = formatValue(currentMeasurements.getAirQualityIndex());
        String pm10 = formatValue(currentMeasurements.getPm10());
        String pm25 = formatValue(currentMeasurements.getPm25());
        String temp = formatValue(currentMeasurements.getTemperature());
        String press = currentMeasurements.getPressure() != null ? formatValue(currentMeasurements.getPressure() / 100.0) : "--";
        String humidity = formatValue(currentMeasurements.getHumidity());

        output.append(LOGO)
                .append("\nPomiar bezpośredni                      ")
                .append("\n-------------------------------------------------")
                .append("\nIndeks jakości powietrza:             ")
                .append(caiq)
                .append("\nPM 10:                                ")
                .append(pm10)
                .append(" μg/m³")
                .append("\nPM 2.5:                               ")
                .append(pm25)
                .append(" μg/m³")
                .append("\nTemperatura:                          ")
                .append(temp)
                .append(" °C")
                .append("\nCiśnienie:                            ")
                .append(press)
                .append(" hPa")
                .append("\nWilgotność:                           ")
                .append(humidity)
                .append(" %");

        return output.toString();

    }

    public String getHistory(){
        StringBuilder output = new StringBuilder();
        Map<String, Integer> data = new HashMap<>();
        List<TimeMeasurement> history = this.measurement.getHistory();
        final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        output.append(LOGO);

        history.forEach(element -> {
            if(element.getMeasurements() == null)
                ErrorHandler.exitOnError(Error.DATA_ERROR);

            String pm10 = formatValue(element.getMeasurements().getPm10());
            String pm25 = formatValue(element.getMeasurements().getPm25());

            try {
                output.append("\n-------------------------------------------------")
                        .append("\nPomiar od: ")
                        .append(formatter.format(parser.parse(element.getFromDateTime())))
                        .append(" do ")
                        .append(formatter.format(parser.parse(element.getTillDateTime())))
                        .append("\nPM 10:                                ")
                        .append(pm10)
                        .append(" μg/m³")
                        .append("\nPM 2.5:                               ")
                        .append(pm25)
                        .append(" μg/m³");
            }catch(ParseException e){
                ErrorHandler.exitOnError(Error.PARSE_ERROR);
            }
        });

        //generate chart from historical measurements
        try {
            for (TimeMeasurement m : history) {
                Integer caqi = m.getMeasurements().getAirQualityIndex() != null
                        ? m.getMeasurements().getAirQualityIndex().intValue() : 1;

                data.put(formatter.format(parser.parse(m.getFromDateTime())), caqi);
            }
        }catch(ParseException e){
            ErrorHandler.exitOnError(Error.PARSE_ERROR);
        }

        output.append(generateChart(data));
        return output.toString();
    }

    private String generateChart(Map<String, Integer> data){
        StringBuilder chartBuilder = new StringBuilder();
        chartBuilder.append("\n")
                    .append("\n-------------------------------------------------")
                    .append("\nWykres pomiaru jakości powietrza")
                    .append("\n-------------------------------------------------\n\n");

        for(Map.Entry<String, Integer> entry : data.entrySet()){
            chartBuilder.append(entry.getKey())
                        .append(" ");

            String currentColor = getBackground(entry.getValue());
            chartBuilder.append(currentColor);

            for(int i = 0; i < entry.getValue(); i+= 2)
                chartBuilder.append(" ");

            chartBuilder.append(this.RESET);
            chartBuilder.append("\n");
        }
        return chartBuilder.toString();
    }

    private String getBackground(Integer value){
        if(value < 25)
            return this.GREEN_BACKGROUND;
        else if(value < 50)
            return this.GREEN_BACKGROUND_BRIGHT;
        else if(value < 75)
            return this.YELLOW_BACKGROUND;
        else if(value < 100)
            return this.RED_BACKGROUND_BRIGHT;
        else
            return this.PURPLE_BACKGROUND;
    }

    private String formatValue(Double value){
        return value != null ? Double.toString(Math.round(value * 100.0) / 100.0) : "--";
    }

}
