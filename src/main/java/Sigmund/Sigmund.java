package Sigmund;

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

    public static void printHelp() {
        String helpMessage = """
                I can help you with these commands:

                --- ADDING TASKS ---
                todo <description>
                   Adds a simple task.
                   Ex: todo Read book

                deadline <description>
                   Adds a task with a deadline.
                   (I will ask for the 'By' date in the next line)
                   Ex: deadline Return book

                event <description>
                   Adds an event.
                   (I will ask for 'From' and 'To' times in the next lines)
                   Ex: event Project meeting

                --- MANAGING TASKS ---
                list (or ls)
                   Shows all tasks in your list.

                mark <task_number>
                   Marks task as done.
                   Ex: mark 1

                unmark <task_number>
                   Marks task as not done.
                   Ex: unmark 1

                --- OTHER ---
                bye (or exit)
                   Exits the program.
                """;

        printColoredText(helpMessage, TextColor.BLUE);
    }

    private static void checkEmptyDescription(String description) throws SigmundException {
        if (description.strip().isEmpty()) {
            throw new SigmundException("OOPS!!! The description cannot be empty!");
        }
    }

    public static void handleCommand(String line, Scanner scanner) throws SigmundException, BreakSignal {
        String[] lineParts = line.split(" ", 2); // only splits ONCE

        String command = line;
        String description = "";
        if (lineParts.length == 2) {
            // if-clause to catch the case when there is no space to split
            command = lineParts[0];
            description = lineParts[1];
        }

        Task newTask = null;
        switch (command.toLowerCase()) {
            case "bye":
            case "exit":
                printColoredText(LINE, TextColor.BLUE);
                printColoredText("BYE BYE! See you again!", TextColor.BLUE);
                throw new BreakSignal("");

            case "list":
            case "ls":
                printList();
                break;

            case "help":
                printHelp();
                break;

            case "mark":
            case "unmark":
                markLogic(line);
                break;

            case "deadline":
                checkEmptyDescription(description);
                System.out.print("By: ");
                String getDeadlineLine = scanner.nextLine().strip();

                newTask = new Deadline(false, description, getDeadlineLine);
                break;
            case "event":
                checkEmptyDescription(description);
                System.out.print("From: ");
                String getEventStartString = scanner.nextLine().strip();
                System.out.print("To: ");
                String getEventEndString = scanner.nextLine().strip();

                newTask = new Event(false, description, getEventStartString, getEventEndString);
                break;

            case "todo":
                checkEmptyDescription(description);
                newTask = new Todo(false, description);
                break;
            default:
                throw new SigmundException("I'm sorry, but I don't know what that means :-(");
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

    public static void main(String[] args) {
        printWelcome();

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                String line = scanner.nextLine().strip();
                handleCommand(line, scanner);
            } catch (SigmundException e) {
                printColoredText(e.toString(), TextColor.RED);
            } catch (BreakSignal e) {
                isRunning = false;
            } catch (Exception e) {
                printColoredText("NOOOOOO!!! A fatal error occurred: " + e.getMessage(), TextColor.RED);
            }

        }
        scanner.close();
    }

}

// list ok
// issue: everything is to lower case
// issue: too many ----- lines
// issue: deadline/event/todo
