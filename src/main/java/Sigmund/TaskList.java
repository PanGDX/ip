package Sigmund;

/**
 * Manages the in-memory list of tasks and coordinates logic for adding, 
 * deleting, and marking tasks.
 */
import java.util.ArrayList;
import static Sigmund.Printer.*;

class TaskList {
    private ArrayList<Todo> tasks;
    private Storage storage;

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
            printColoredText("Got it! Added: ", TextColor.BLUE);
            printColoredText("    " + task.toString(), TextColor.BLUE);
            printColoredText(String.format("You now have %d tasks in the list", tasks.size() - 1), TextColor.BLUE);
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

        printColoredText("Noted. I've removed this task:", TextColor.BLUE);
        printColoredText(" " + tasks.get(taskNumber - 1).toString(), TextColor.BLUE);
        tasks.remove(taskNumber - 1);
        save();
        printColoredText("Now you have " + tasks.size() + " tasks in the list", TextColor.BLUE);
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
            printColoredText("No tasks! Time to take a break!", TextColor.BLUE);
            return;
        }

        printColoredText("Here are the tasks in your list!", TextColor.BLUE);
        for (int i = 0; i < tasks.size(); i++) {
            String listItem = tasks.get(i).toString();
            if (tasks.get(i).getClass() == Todo.class) {
                printColoredText((i + 1) + ". " + listItem, TextColor.BLUE);
            } else if (((Todo) tasks.get(i)).isDone()) {
                printColoredText((i + 1) + ". " + listItem, TextColor.GREEN);
            } else {
                printColoredText((i + 1) + ". " + listItem, TextColor.RED);
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
            printColoredText("INVALID! CHILL OUT", TextColor.BLUE);
            return;
        }

        String formatString;
        if (line.toLowerCase().startsWith("mark")) {
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
        printColoredText(formatString, TextColor.BLUE);
    }

}
