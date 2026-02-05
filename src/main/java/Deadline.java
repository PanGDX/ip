public class Deadline extends Todo {
    private String deadlineTime;

    public Deadline(boolean isDone, String taskDescription, String deadlineTime) {
        super(taskDescription);
        this.deadlineTime = deadlineTime;
    }

    public String getDeadlineTime() {
        return this.deadlineTime;
    }

    @Override
    public String toString() {
        String tickString = isDone ? "[x]" : "[ ]";
        String suffixTimeString = " (by: %s)";
        suffixTimeString = String.format(suffixTimeString, this.deadlineTime);
        return String.format("[Deadline]%s %s %s", tickString, this.taskDescription, suffixTimeString);
    }
}
