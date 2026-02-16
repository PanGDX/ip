package Sigmund;

public class SigmundException extends Exception {
    public SigmundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return " OOPS!!! " + getMessage();
    }
}
