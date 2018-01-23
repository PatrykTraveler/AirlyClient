package Airly;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {
    private static final Map<Error, String> errors;
    static
    {
        errors = new HashMap<Error, String>();
        errors.put(Error.DATA_ERROR, "An unexpected error has occured while receiving data");
        errors.put(Error.PARSE_ERROR, "Something went wrong while parsing data.");
        errors.put(Error.SECURITY_ERROR, "Cannot access to environment variable");
    }

    public static Error getHttpError(int code){
        switch(code){
            case 400:
                return Error.INPUT_VALIDATION_ERROR;
            case 401:
                return Error.UNAUTHORIZED;
            case 403:
                return Error.FORBIDDEN;
            case 404:
                return Error.NOT_FOUND;
            case 500:
                return Error.UNEXPECTED_ERROR;
            default:
                return Error.UNKNOWN_ERROR;
        }
    }

    public static void exitOnError(Error error){
        System.out.println("\nERROR: " + (errors.containsKey(error) ? errors.get(error) : error));
        System.exit(0);
    }

}
