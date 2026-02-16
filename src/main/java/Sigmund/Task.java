package Sigmund;

public abstract class Task {
    protected String taskDescription;

    public Task(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public String toString() {
        return this.taskDescription;
    }
}
