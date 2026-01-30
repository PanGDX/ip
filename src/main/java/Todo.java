public class Todo extends Task {
    public Todo(boolean isDone, String taskDescription) {
        super(isDone, taskDescription);
    }

    @Override
    public String getType() {
        return "todo";
    }
}
