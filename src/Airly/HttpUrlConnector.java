package Airly;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnector {
    private String output;
    private int responseCode;
    private final String propertyKey;
    private final String propertyValue;
    private final String url;

    HttpUrlConnector(String propertyKey, String propertyValue, String url){
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
        this.url = url;
        this.responseCode = -1;
    }

    public void sendGet(){
        try {
            URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty(propertyKey, propertyValue);
            this.responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            this.output = response.toString();
        }catch(Exception e){
            ErrorHandler.exitOnError(ErrorHandler.getHttpError(this.responseCode));
        }
    }

    public String getOutput(){
        return this.output;
    }
}
