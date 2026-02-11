package Sigmund;

import static Sigmund.Sigmund.LINE;

public class SigmundException extends Exception {
    public SigmundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return LINE + "\n" +
                " OOPS!!! " + getMessage()
                + "\n" + LINE;
    }
}
