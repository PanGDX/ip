package Sigmund;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that occurs within a specific time range
 */
public class Event extends Todo {
    private String eventStartTimeString;
    private String eventEndTimeString;
    private LocalDateTime eventStartTime = null;
    private LocalDateTime eventEndTime = null;

    public Event(String taskDescription, String eventStartTimeString, String eventEndTimeString) {
        super(taskDescription);
        this.eventStartTimeString = eventStartTimeString;
        this.eventEndTimeString = eventEndTimeString;

        this.eventStartTime = DateTimeParser.parseDateTime(eventStartTimeString);
        this.eventEndTime = DateTimeParser.parseDateTime(eventEndTimeString);
        // deadlineTime is null if not formattable

        this.isDone = false;
    }

    public String getEventStartTime() {
        if (this.eventStartTime != null) {
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mm a", Locale.ENGLISH);
            String formattedTime = this.eventStartTime.format(displayFormatter);
            return formattedTime;
        }

        return this.eventStartTimeString;
    }

    public String getEventEndTime() {
        if (this.eventEndTime != null) {
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mm a", Locale.ENGLISH);
            String formattedTime = this.eventEndTime.format(displayFormatter);
            return formattedTime;
        }

        return this.eventEndTimeString;
    }

    /**
     * Returns a string representation of the event including its time range.
     */
    @Override
    public String toString() {
        String tickString = isDone ? "[x]" : "[ ]";
        String startStr = this.getEventStartTime();
        String endStr = this.getEventEndTime();

        return String.format("[Event]%s %s (from: %s to: %s)",
                tickString, this.taskDescription, startStr, endStr);
    }

    @Override
    public String toFileFormat() {
        return "Event | " + (isDone ? "1" : "0") + " | " + taskDescription +
                " | " + this.eventStartTimeString + "-"
                + this.eventEndTimeString;
    }
}
