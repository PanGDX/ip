package Sigmund;

import java.nio.file.Paths;
import java.util.Scanner;
import Sigmund.Printer;
import static Sigmund.Printer.*;

public class Sigmund {
    private static final String FILE_PATH = Paths.get("data", "tasks.txt").toString();
    private static Storage storage = new Storage(FILE_PATH);
    private static TaskList taskList = new TaskList(storage);

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

        Todo newTask = null;
        switch (command.toLowerCase()) {
            case "bye":
            case "exit":
                Printer.printColoredText(LINE, TextColor.BLUE);
                printColoredText("BYE BYE! See you again!", TextColor.BLUE);
                throw new BreakSignal("");

            case "list":
            case "ls":
                taskList.printList();
                break;

            case "help":
                printHelp();
                break;

            case "mark":
            case "unmark":
                taskList.markLogic(line);
                break;

            case "delete":
                deleteTask(description);
                break;

            case "deadline":
                checkEmptyDescription(description);
                System.out.print("By: ");
                String getDeadlineLine = scanner.nextLine().strip();

                newTask = new Deadline(description, getDeadlineLine);
                break;
            case "event":
                checkEmptyDescription(description);
                System.out.print("From: ");
                String getEventStartString = scanner.nextLine().strip();
                System.out.print("To: ");
                String getEventEndString = scanner.nextLine().strip();

                newTask = new Event(description, getEventStartString, getEventEndString);
                break;

            case "todo":
                checkEmptyDescription(description);
                newTask = new Todo(description);
                break;
            default:
                throw new SigmundException("I'm sorry, but I don't know what that means :-(");
        }
        taskList.addTask(newTask);
        System.out.println(LINE);
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
