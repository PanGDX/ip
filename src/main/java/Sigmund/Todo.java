package Sigmund;

public class Todo extends Task {
    protected boolean isDone;

    public Todo(boolean isDone, String taskDescription) {
        super(taskDescription);
        this.isDone = isDone;
    }

    public Todo(String taskDescription) {
        super(taskDescription);
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
}
