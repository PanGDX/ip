package Sigmund;

/**
 * Manages the in-memory list of tasks and coordinates logic for adding, 
 * deleting, and marking tasks.
 */
import java.util.ArrayList;
import static Sigmund.Ui.*;

class TaskList {
    private ArrayList<Todo> tasks;
    private Storage storage;
    private Ui ui = new Ui();

    /**
     * Initializes the TaskList by loading existing tasks from storage.
     * 
     * @param storage The Storage instance used for persistence.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = storage.load();
    }

    /**
     * Converts a string input into a valid 1-based task index.
     * 
     * @param s The string representing the task number.
     * @return The integer task number.
     * @throws SigmundException      If the number is out of the list bounds.
     * @throws NumberFormatException If the string cannot be parsed as an integer.
     */
    private int getValidTaskIndexFromString(String s) throws SigmundException, NumberFormatException {

        int taskNumber = Integer.parseInt(s); // throws NumberFormatException by default

        if (taskNumber > tasks.size() || taskNumber == 0) {
            throw new SigmundException("INVALID task number! CHILL OUT");
        }
        return taskNumber;
    }

    /**
     * Adds a new task to the list and triggers an auto-save.
     * 
     * @param task The task object to be added.
     */
    public void addTask(Todo task) {
        if (task != null) {
            tasks.add(task);
            ui.showResponse("Got it! Added: ");
            ui.showResponse("    " + task.toString());
            ui.showResponse(String.format("You now have %d tasks in the list", tasks.size() - 1));
            save(); // Auto-save
        }
    }

    /**
     * Removes a task from the list based on its index and updates storage.
     * 
     * @param arg The string index of the task to be deleted.
     * @throws SigmundException If the index is invalid.
     */
    public void deleteTask(String arg) throws NumberFormatException, SigmundException {
        int taskNumber = getValidTaskIndexFromString(arg);

        ui.showResponse("Noted. I've removed this task:");
        ui.showResponse(" " + tasks.get(taskNumber - 1).toString());
        tasks.remove(taskNumber - 1);
        save();
        ui.showResponse("Now you have " + tasks.size() + " tasks in the list");
    }

    private void save() {
        storage.save(tasks);
    }

    /**
     * Displays all current tasks in the list to the console with color-coded
     * status.
     */
    public void printList() {
        if (tasks.size() == 0) { // guard for empty list
            ui.showResponse("No tasks! Time to take a break!");
            return;
        }

        ui.showResponse("Here are the tasks in your list!");
        for (int i = 0; i < tasks.size(); i++) {
            String listItem = tasks.get(i).toString();
            if (((Todo) tasks.get(i)).isDone()) {
                ui.showDone((i + 1) + ". " + listItem);
            } else {
                ui.showNotDone((i + 1) + ". " + listItem);
            }
        }
    }

    /**
     * Handles the logic for marking or unmarking a task as done based on user
     * input.
     * 
     * @param line The command string (e.g., "mark 1").
     * @throws SigmundException If the task index is invalid.
     */
    public void markLogic(String line) throws NumberFormatException, SigmundException {
        int taskNumber = getValidTaskIndexFromString(line.split(" ")[1]);

        if (taskNumber > tasks.size() || taskNumber == 0) {
            ui.showResponse("INVALID! CHILL OUT");
            return;
        }

        String formatString;
        if (line.toLowerCase().startsWith("mark") || line.toLowerCase().startsWith("tick")) {
            tasks.get(taskNumber - 1).setDone(true);
            formatString = String.format(
                    "GREAT JOB! I've marked this task as done:\n%s",
                    tasks.get(taskNumber - 1).toString());
        } else {
            tasks.get(taskNumber - 1).setDone(false);
            formatString = String.format(
                    "OK, I've marked this task as not done yet:\n%s",
                    tasks.get(taskNumber - 1).toString());
        }
        save();
        ui.showResponse(formatString);
    }

}
