package Sigmund;

/**
 * Special exception to signal to the main application to exit the running loop
 */
public class BreakSignal extends Exception {
    public BreakSignal(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "";
    }
}
