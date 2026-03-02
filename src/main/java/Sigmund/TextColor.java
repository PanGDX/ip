package Sigmund;

/**
 * Enum containing ANSI escape code for printing colored text
 */
public enum TextColor {
    RED("\u001B[91m"), // High Intensity Red
    GREEN("\u001B[92m"), // High Intensity Green
    BLUE("\u001B[94m"); // High Intensity Blue

    private final String ansiCode;

    TextColor(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    public String getAnsiCode() {
        return ansiCode;
    }
}
