package Airly;

public enum Error {
    UNKNOWN_ERROR,
    INPUT_VALIDATION_ERROR,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    UNEXPECTED_ERROR,
    OK;

    public static Error getError(int code){
        switch(code){
            case -1:
                return Error.UNKNOWN_ERROR;
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
                return Error.OK;
        }
    }




}
