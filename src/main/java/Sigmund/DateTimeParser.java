package Sigmund;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeParser {
    public static final String[] PATTERNS = {
            // Date + 24h Time
            "d/M/yyyy H:mm", "d/MMM/yyyy H:mm", "d MMM yyyy H:mm",

            // Date + 12h Time (with minutes)
            "d/M/yyyy h:mm a", "d/MMM/yyyy h:mm a", "d MMM yyyy h:mm a",

            // Date + 12h Time (no minutes, e.g., 4pm)
            "d/M/yyyy h a", "d/MMM/yyyy h a", "d MMM yyyy h a",

            // Date Only
            "d/M/yyyy", "d/MMM/yyyy", "d MMM yyyy"
    };

    public static LocalDateTime parseDateTime(String input) {
        // 1. Pre-process: Normalize spacing
        String cleanedInput = input.trim();

        // Insert space before AM/PM if it is attached directly to a digit (e.g., "4pm"
        // -> "4 pm")
        cleanedInput = cleanedInput.replaceAll("(?i)(?<=\\d)(am|pm)", " $1");
        cleanedInput = cleanedInput.replaceAll("\\s+", " ");

        // 2. Iterate through patterns
        for (String pattern : PATTERNS) {
            try {
                // Building a Case-Insensitive Formatter safely covers "Jun" vs "JUN" and "pm"
                // vs "PM"
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive()
                        .appendPattern(pattern)
                        .toFormatter(Locale.ENGLISH);

                // If pattern does not contain time components, parse as Date and add midnight
                if (!pattern.contains("H") && !pattern.contains("h")) {
                    return LocalDate.parse(cleanedInput, formatter).atStartOfDay();
                }

                // Otherwise parse as full LocalDateTime
                return LocalDateTime.parse(cleanedInput, formatter);
            } catch (DateTimeParseException e) {
                // Ignore and try the next pattern
                continue;
            }
        }

        // 3. Final Fallback
        Ui ui = new Ui();
        ui.showError("Warning: Format not recognized for '" + input + "'. Returning null.");
        return null;
    }

    public static String formatDateTime(LocalDateTime input) {
        return input.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
    }
}
