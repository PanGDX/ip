public class Task {
    private boolean isDone;
    private String taskDescription;

    public Task(boolean isDone, String taskDescription) {
        this.isDone = isDone;
        this.taskDescription = taskDescription;
    }

    public boolean getDoneStatus() {
        return isDone;
    }

    public void setDoneStatus(boolean isDone) {
        this.isDone = isDone;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getType() {
        return "task";
    }
}
