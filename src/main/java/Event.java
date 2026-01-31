public class Event extends Task {
    private String eventStartTime;
    private String eventEndTime;

    public Event(boolean isDone, String taskDescription, String eventStartTime, String eventEndTime) {
        super(isDone, taskDescription);
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
    }

    @Override
    public String getType() {
        return "event";
    }

    public String getEventStartTime() {
        return this.eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventEndTime() {
        return this.eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }
}
