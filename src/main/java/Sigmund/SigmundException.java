package Sigmund;

/**
 * Custom exception class for specific errors
 */
public class SigmundException extends Exception {
    public SigmundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return " OOPS!!! " + getMessage();
    }
}
