package Sigmund;

/**
 * Represents a basic task with a description and completion status.
 */
public class Todo {
    protected boolean isDone;
    protected String taskDescription;

    public Todo(String taskDescription) {
        this.taskDescription = taskDescription;
        this.isDone = false;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        String tickString = isDone ? "[x]" : "[ ]";
        return String.format("[Todo]%s %s", tickString, this.taskDescription);
    }

    /**
     * Converts the task into a specific format intended for file storage.
     * 
     * @return A formatted string representing the task data.
     */
    public String toFileFormat() {
        return "Todo | " + (isDone ? "1" : "0") + " | " + taskDescription;
    }
}
