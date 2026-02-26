package Sigmund;

import java.util.Scanner;

/**
 * Deals with making sense of the user command.
 */
public class Parser {
    private Ui ui = new Ui();

    /**
     * Validates that a task description is not empty or whitespace only.
     * 
     * @param description The string to check.
     * @throws SigmundException If the description is empty.
     */
    private static void checkEmptyDescription(String description) throws SigmundException {
        if (description.strip().isEmpty()) {
            throw new SigmundException("The description cannot be empty!");
        }
    }

    /**
     * Parses the user input string and executes the corresponding logic.
     * 
     * @param line     The full raw input line from the user.
     * @param scanner  The scanner instance to read additional details for
     *                 multi-line inputs.
     * @param taskList The task list to be modified based on the command.
     * @throws SigmundException If the command is unknown or arguments are invalid.
     * @throws BreakSignal      If the user requests to exit the program.
     */
    public void handleCommand(String line, Scanner scanner, TaskList taskList)
            throws SigmundException, BreakSignal {

        String[] lineParts = line.split(" ", 2);
        String command = lineParts[0].toLowerCase();
        String description = (lineParts.length == 2) ? lineParts[1].strip() : "";

        Todo newTask = null;

        switch (command) {
            case "bye":
            case "exit":
                ui.showLine();
                ui.showResponse("BYE BYE! See you again!");
                throw new BreakSignal("Exit requested");

            case "list":
            case "ls":
                taskList.printList();
                break;

            case "help":
                ui.showHelp();
                break;

            case "mark":
            case "unmark":
            case "tick":
            case "untick":
                taskList.markLogic(line);
                break;

            case "delete":
                taskList.deleteTask(description);
                break;

            case "deadline":
                checkEmptyDescription(description);
                System.out.print("By: ");
                String deadlineBy = scanner.nextLine().strip();
                newTask = new Deadline(description, deadlineBy);
                break;

            case "event":
                checkEmptyDescription(description);
                System.out.print("From: ");
                String from = scanner.nextLine().strip();
                System.out.print("To: ");
                String to = scanner.nextLine().strip();
                newTask = new Event(description, from, to);
                break;

            case "todo":
                checkEmptyDescription(description);
                newTask = new Todo(description);
                break;

            default:
                throw new SigmundException("I'm sorry, but I don't know what that means :-(");
        }

        // Only adds to list if a new task object was actually instantiated
        if (newTask != null) {
            taskList.addTask(newTask);
        }
    }
}
