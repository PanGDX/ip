package Sigmund;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that has a deadline
 */
public class Deadline extends Todo {
    private String deadlineTimeString;
    private LocalDateTime deadlineTime = null;

    public Deadline(String taskDescription, String deadlineTimeString) {
        super(taskDescription);
        this.deadlineTimeString = deadlineTimeString;
        this.deadlineTime = DateTimeParser.parseDateTime(deadlineTimeString);
        // deadlineTime is null if not formattable

        this.isDone = false;
    }

    /**
     * If the deadline string is valid, displays in dd MMM yyyy, h:mm a format. For
     * example: 12 June 2025, 6:50 am
     * If not, return the original unconverted string.
     */
    public String getDeadlineTime() {
        if (this.deadlineTime != null) {
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mm a", Locale.ENGLISH);
            String formattedTime = this.deadlineTime.format(displayFormatter);
            return formattedTime;
        }

        return this.deadlineTimeString;
    }

    // Ordered list of patterns to attempt

    @Override
    public String toString() {
        String tickString = isDone ? "[x]" : "[ ]";

        String formattedTime = getDeadlineTime();

        return String.format("[Deadline]%s %s (by: %s)", tickString, this.taskDescription, formattedTime);
    }

    @Override
    public String toFileFormat() {
        return "Deadline | " + (isDone ? "1" : "0") + " | " + this.taskDescription + " | "
                + this.deadlineTimeString;
    }
}
