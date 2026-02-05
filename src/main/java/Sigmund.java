import java.util.Scanner;

public class Sigmund {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String LINE = "____________________________________________________________";
    public static Task[] storage = new Task[100];
    public static int itemCount = 0;

    public static void printColoredText(String text, TextColor color) {
        System.out.println(color.getAnsiCode() + text + ANSI_RESET);
    }

    public static void printList() {
        if (itemCount == 0) { // guard for empty list
            printColoredText("No tasks! Time to take a break!", TextColor.BLUE);
            return;
        }

        printColoredText("Here are the tasks in your list!", TextColor.BLUE);
        for (int i = 0; i < itemCount; i++) {
            String listItem = storage[i].toString();
            if (storage[i].getClass() == Task.class) {
                printColoredText((i + 1) + ". " + listItem, TextColor.BLUE);
            } else if (((Todo) storage[i]).isDone()) {
                printColoredText((i + 1) + ". " + listItem, TextColor.GREEN);
            } else {
                printColoredText((i + 1) + ". " + listItem, TextColor.RED);
            }
        }
    }

    public static void markLogic(String line) {
        int taskNumber = Integer.parseInt(line.split(" ")[1]);

        if (taskNumber > itemCount || taskNumber == 0 || storage[taskNumber - 1].getClass() == Task.class) {
            printColoredText("INVALID! CHILL OUT", TextColor.BLUE);
            return;
        }

        String formatString;
        if (line.toLowerCase().startsWith("mark")) {
            ((Todo) storage[taskNumber - 1]).setDone(true);
            formatString = String.format(
                    "GREAT JOB! I've marked this task as done:\n%s",
                    storage[taskNumber - 1].toString());
        } else {
            ((Todo) storage[taskNumber - 1]).setDone(false);
            formatString = String.format(
                    "OK, I've marked this task as not done yet:\n%s",
                    storage[taskNumber - 1].toString());
        }
        printColoredText(formatString, TextColor.BLUE);
    }

    public static void printWelcome() {
        String logo = LINE + """
                \nHello! I'm Sigmund
                What can I do for you?
                I love Kopi C Kosong!\n""" + LINE;
        printColoredText(logo, TextColor.BLUE);
    }

    public static void main(String[] args) {
        printWelcome();

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            String line = scanner.nextLine().strip();
            String[] lineParts = line.split(" ", 2); // only splits ONCE

            String command = line;
            String description = "";
            if (lineParts.length == 2) {
                // if-clause to catch the case when there is no space to split
                command = lineParts[0];
                description = lineParts[1];
            }

            System.out.println(LINE);
            Task newTask = null;
            switch (command.toLowerCase()) {
                case "bye":
                case "exit":
                    printColoredText("BYE BYE! See you again!", TextColor.BLUE);
                    isRunning = false;
                    break;

                case "list":
                case "ls":
                    printList();
                    break;

                case "mark":
                case "unmark":
                    markLogic(line);
                    break;

                case "deadline":
                    System.out.print("By: ");
                    String getDeadlineLine = scanner.nextLine().strip();

                    newTask = new Deadline(false, description, getDeadlineLine);
                    break;
                case "event":
                    System.out.print("From: ");
                    String getEventStartString = scanner.nextLine().strip();
                    System.out.print("To: ");
                    String getEventEndString = scanner.nextLine().strip();

                    newTask = new Event(false, description, getEventStartString, getEventEndString);
                    break;

                case "todo":
                    newTask = new Todo(false, description);
                    break;
                default:
                    newTask = new Task(line);
                    break;
            }

            if (newTask != null) {
                storage[itemCount] = newTask;
                itemCount++;
                printColoredText("Got it! Added: ", TextColor.BLUE);
                printColoredText("    " + newTask.toString(), TextColor.BLUE);
                printColoredText(String.format("You now have %d tasks in the list", itemCount), TextColor.BLUE);
            }
            System.out.println(LINE);
        }
        scanner.close();
    }
}

// list ok
// issue: everything is to lower case
// issue: too many ----- lines
// issue: deadline/event/todo
