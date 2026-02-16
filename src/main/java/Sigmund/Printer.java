package Sigmund;

public class Printer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String LINE = "____________________________________________________________";

    public static void printColoredText(String text, TextColor color) {
        System.out.println(color.getAnsiCode() + text + ANSI_RESET);
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
}
