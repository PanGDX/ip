public class Deadline extends Task {
    private String deadlineTime;

    public Deadline(boolean isDone, String taskDescription, String deadlineTime) {
        super(isDone, taskDescription);
        this.deadlineTime = deadlineTime;
    }

    @Override
    public String getType() {
        return "deadline";
    }

    public String getDeadlineTime() {
        return this.deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }
}
