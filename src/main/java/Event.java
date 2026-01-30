public class Event extends Task {
    private String eventTime;

    public Event(boolean isDone, String taskDescription, String eventTime) {
        super(isDone, taskDescription);
    }

    @Override
    public String getType() {
        return "event";
    }

    public String getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
