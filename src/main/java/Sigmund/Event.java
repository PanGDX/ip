package Sigmund;

public class Event extends Todo {
    private String eventStartTime;
    private String eventEndTime;

    public Event(boolean isDone, String taskDescription, String eventStartTime, String eventEndTime) {
        super(taskDescription);
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }

    public String getEventStartTime() {
        return this.eventStartTime;
    }

    public String getEventEndTime() {
        return this.eventEndTime;
    }

    @Override
    public String toString() {
        String tickString = isDone ? "[x]" : "[ ]";
        String suffixTimeString = " (from: %s to: %s)";
        suffixTimeString = String.format(suffixTimeString, this.eventStartTime, this.eventEndTime);
        return String.format("[Event]%s %s %s", tickString, this.taskDescription, suffixTimeString);
    }
}
