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

    public void addTask(Todo task) {
        if (task != null) {
            tasks.add(task);
            System.out.println("Got it. I've added this task:\n  " + task);
            save(); // Auto-save
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Todo removed = tasks.remove(index);
            System.out.println("Noted. I've removed this task:\n  " + removed);
            save(); // Auto-save
        }
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

    public void markLogic(String line) {
        int taskNumber = Integer.parseInt(line.split(" ")[1]);

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
