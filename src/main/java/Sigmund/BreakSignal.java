package Sigmund;

public class BreakSignal extends Exception {
    public BreakSignal(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "";
    }
}
