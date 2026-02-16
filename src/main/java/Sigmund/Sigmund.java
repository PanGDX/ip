package Sigmund;

import java.util.Scanner;
import java.security.SignatureException;
import java.util.ArrayList;

public class Sigmund {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String LINE = "____________________________________________________________";
    public static ArrayList<Task> storage = new ArrayList<Task>();

    public static void printColoredText(String text, TextColor color) {
        System.out.println(color.getAnsiCode() + text + ANSI_RESET);
    }

    public static void printList() {
        if (storage.size() == 0) { // guard for empty list
            printColoredText("No tasks! Time to take a break!", TextColor.BLUE);
            return;
        }

        printColoredText("Here are the tasks in your list!", TextColor.BLUE);
        for (int i = 0; i < storage.size(); i++) {
            String listItem = storage.get(i).toString();
            if (storage.get(i).getClass() == Task.class) {
                printColoredText((i + 1) + ". " + listItem, TextColor.BLUE);
            } else if (((Todo) storage.get(i)).isDone()) {
                printColoredText((i + 1) + ". " + listItem, TextColor.GREEN);
            } else {
                printColoredText((i + 1) + ". " + listItem, TextColor.RED);
            }
        }
    }

    public static void printWelcome() {
        String logo = """
                Hello! I'm Sigmund
                What can I do for you?
                I love Kopi C Kosong!""";
        System.out.println(LINE);
        printColoredText(logo, TextColor.BLUE);
        System.out.println(LINE);
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

            case "delete":
                deleteTask(description);
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

        addNewTask(newTask);
    }

    private static void checkEmptyDescription(String description) throws SigmundException {
        if (description.strip().isEmpty()) {
            throw new SigmundException("OOPS!!! The description cannot be empty!");
        }
    }

    private static int getValidTaskIndexFromString(String s) throws SigmundException, NumberFormatException {

        int taskNumber = Integer.parseInt(s); // throws NumberFormatException by default

        if (taskNumber > storage.size() || taskNumber == 0) {
            throw new SigmundException("INVALID task number! CHILL OUT");
        }
        return taskNumber;
    }

    public static void markLogic(String line) throws NumberFormatException, SigmundException {
        int taskNumber = getValidTaskIndexFromString(line.split(" ")[1]);

        if (taskNumber > storage.size() || taskNumber == 0) {
            printColoredText("INVALID! CHILL OUT", TextColor.BLUE);
            return;
        }

        String formatString;
        if (line.toLowerCase().startsWith("mark")) {
            ((Todo) storage.get(taskNumber - 1)).setDone(true);
            formatString = String.format(
                    "GREAT JOB! I've marked this task as done:\n%s",
                    storage.get(taskNumber - 1).toString());
        } else {
            ((Todo) storage.get(taskNumber - 1)).setDone(false);
            formatString = String.format(
                    "OK, I've marked this task as not done yet:\n%s",
                    storage.get(taskNumber - 1).toString());
        }
        printColoredText(formatString, TextColor.BLUE);
    }

    private static void deleteTask(String arg) throws NumberFormatException, SigmundException {

        int taskNumber = getValidTaskIndexFromString(arg);

        printColoredText("Noted. I've removed this task:", TextColor.BLUE);
        printColoredText(" " + storage.get(taskNumber - 1).toString(), TextColor.BLUE);
        storage.remove(taskNumber - 1);
        printColoredText("Now you have " + storage.size() + " tasks in the list", TextColor.BLUE);
    }

    private static void addNewTask(Task newTask) {
        if (newTask != null) {
            storage.add(newTask);
            printColoredText("Got it! Added: ", TextColor.BLUE);
            printColoredText("    " + newTask.toString(), TextColor.BLUE);
            printColoredText(String.format("You now have %d tasks in the list", storage.size()), TextColor.BLUE);
        }
    }

    public static void main(String[] args) {
        printWelcome();

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                String line = scanner.nextLine().strip();
                System.out.println(LINE);
                handleCommand(line, scanner);
            } catch (SigmundException e) {
                printColoredText(e.toString(), TextColor.RED);
            } catch (BreakSignal e) {
                isRunning = false;
            } catch (NumberFormatException e) {
                printColoredText("The argument needs to be a number!", TextColor.RED);
            } catch (Exception e) {
                printColoredText("NOOOOOO!!! A fatal error occurred: " + e.getMessage(), TextColor.RED);
            } finally {
                System.out.println(LINE);
            }

        }
        scanner.close();
    }

}

// list ok
// issue: everything is to lower case
// issue: too many ----- lines
// issue: deadline/event/todo
