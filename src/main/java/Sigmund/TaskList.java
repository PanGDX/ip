package Sigmund;

import java.util.ArrayList;
import static Sigmund.Printer.*;

class TaskList {
    private ArrayList<Todo> tasks;
    private Storage storage;

    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = storage.load();
    }

    private int getValidTaskIndexFromString(String s) throws SigmundException, NumberFormatException {

        int taskNumber = Integer.parseInt(s); // throws NumberFormatException by default

        if (taskNumber > tasks.size() || taskNumber == 0) {
            throw new SigmundException("INVALID task number! CHILL OUT");
        }
        return taskNumber;
    }

    public void addTask(Todo task) {
        if (task != null) {
            tasks.add(task);
            printColoredText("Got it! Added: ", TextColor.BLUE);
            printColoredText("    " + task.toString(), TextColor.BLUE);
            printColoredText(String.format("You now have %d tasks in the list", tasks.size() - 1), TextColor.BLUE);
            save(); // Auto-save
        }
    }

    public void deleteTask(String arg) throws NumberFormatException, SigmundException {
        int taskNumber = getValidTaskIndexFromString(arg);

        printColoredText("Noted. I've removed this task:", TextColor.BLUE);
        printColoredText(" " + tasks.get(taskNumber - 1).toString(), TextColor.BLUE);
        tasks.remove(taskNumber - 1);
        save();
        printColoredText("Now you have " + tasks.size() + " tasks in the list", TextColor.BLUE);
    }

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

    private void save() {
        storage.save(tasks);
    }

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
