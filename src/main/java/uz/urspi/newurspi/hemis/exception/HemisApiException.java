package uz.urspi.newurspi.hemis.exception;

public class HemisApiException extends RuntimeException {

    private final int statusCode;

    public HemisApiException(String message) {
        super(message);
        this.statusCode = 0;
    }

    public HemisApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
