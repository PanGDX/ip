package Sigmund;

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

    public String toFileFormat() {
        return "Todo | " + (isDone ? "1" : "0") + " | " + taskDescription;
    }
}
