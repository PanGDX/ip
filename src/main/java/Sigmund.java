import java.util.Scanner;

public class Sigmund {
    public static final String ANSI_RESET = "\u001B[0m";
    public static Task[] storage = new Task[100];
    public static int itemCount = 0;

    public static void printColoredText(String text, TextColor color) {
        System.out.println(color.getAnsiCode() + text + ANSI_RESET);
    }

    public static String TaskObjToStringFormat(Task task) {
        String typeBracket = "";
        String suffixTimeString = "";

        switch (task.getType()) {
            case "todo":
                typeBracket = "[ToDo]";
                break;
            case "deadline":
                typeBracket = "[Deadline]";
                suffixTimeString = " (by: %s)";
                suffixTimeString = String.format(suffixTimeString, ((Deadline) task).getDeadlineTime());
                break;
            case "event":
                typeBracket = "[Event]";
                suffixTimeString = " (from: %s to: %s)";
                suffixTimeString = String.format(suffixTimeString, ((Event) task).getEventStartTime(),
                        ((Event) task).getEventStartTime());
                break;
            default:
                typeBracket = "";
        }
        String tickBracket = task.getDoneStatus() ? "[X]" : "[ ]";
        String listItem = typeBracket
                + tickBracket
                + " "
                + task.getTaskDescription().strip().replaceFirst("deadline", "")
                + suffixTimeString;
        return listItem;
    }

    public static void printList() {

        for (int i = 0; i < itemCount; i++) {
            String listItem = TaskObjToStringFormat(storage[i]);
            if (storage[i].getDoneStatus()) {
                printColoredText((i + 1) + ". " + listItem, TextColor.GREEN);
            } else {
                printColoredText((i + 1) + ". " + listItem, TextColor.RED);
            }
        }
    }

    public static void markLogic(String line) {
        int taskNumber = Integer.parseInt(line.split(" ")[1]);
        if (taskNumber <= itemCount && taskNumber != 0) {
            if (line.toLowerCase().startsWith("unmark")) {
                storage[taskNumber - 1].setDoneStatus(false);
                printColoredText("OK, I've marked this task as not done yet:", TextColor.BLUE);
                printList();
            } else {
                storage[taskNumber - 1].setDoneStatus(true);
                printColoredText("GREAT JOB! I've marked this task as done::", TextColor.BLUE);
                printList();
            }
        } else {
            printColoredText("THAT DOESN'T EXIST! CHILL OUT", TextColor.BLUE);
        }
    }

    public static void main(String[] args) {

        String logo = """
                ____________________________________________________________
                Hello! I'm Sigmund
                What can I do for you?
                I love Kopi C Kosong!
                ____________________________________________________________""".strip();
        printColoredText(logo, TextColor.BLUE);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine().strip();
            String lowerCaseLine = line.toLowerCase();
            System.out.println("____________________________________________________________");

            if (lowerCaseLine.equals("bye") || lowerCaseLine.equals("exit")) {
                printColoredText("BYE BYE! See you again!", TextColor.BLUE);
                System.out.println("____________________________________________________________");
                break;
            } else if (lowerCaseLine.equals("list")) {
                if (itemCount == 0) {
                    printColoredText("No tasks! Time to take a break!", TextColor.BLUE);
                } else {
                    printColoredText("Here are the tasks in your list!", TextColor.BLUE);
                    printList();
                }
            } else if (lowerCaseLine.startsWith("mark") || lowerCaseLine.startsWith("unmark")) {
                markLogic(line);
            } else {
                Task newTask;
                if (lowerCaseLine.startsWith("deadline")) {
                    System.out.print("By: ");
                    String getDeadlineLine = scanner.nextLine().strip();
                    newTask = new Deadline(false, line, getDeadlineLine);
                } else if (lowerCaseLine.startsWith("event")) {
                    System.out.print("From: ");
                    String getEventStartString = scanner.nextLine().strip();
                    System.out.print("To: ");
                    String getEventEndString = scanner.nextLine().strip();
                    newTask = new Event(false, line, getEventStartString, getEventEndString);
                } else if (lowerCaseLine.startsWith("todo")) {
                    newTask = new Todo(false, line);
                } else {
                    newTask = new Task(false, line);
                }
                storage[itemCount] = newTask;
                itemCount++;
                printColoredText("Got it! Added: ", TextColor.BLUE);
                printColoredText("    " + TaskObjToStringFormat(newTask), TextColor.BLUE);
                printColoredText(String.format("You now have %d tasks in the list", itemCount), TextColor.BLUE);
            }
            System.out.println("____________________________________________________________");
        }
        scanner.close();
    }
}

// list ok
// issue: everything is to lower case
// issue: too many ----- lines
// issue: deadline/event/todo
