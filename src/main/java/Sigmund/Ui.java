package Sigmund;

/**
 * Utility class for handling console output formatting and UI elements.
 */
public class Ui {

   public Ui() {
   }

   public static final String ANSI_RESET = "\u001B[0m";
   public static final String LINE = "____________________________________________________________";

   /**
    * Prints a string to the console with a specified ANSI color.
    * 
    * @param text  The text to be printed.
    * @param color The TextColor enum representing the desired ANSI color.
    */
   public static void printColoredText(String text, TextColor color) {
      System.out.println(color.getAnsiCode() + text + ANSI_RESET);
   }

   /**
    * Helper functions
    */
   public void showLine() {
      System.out.println(LINE);
   }

   public void showError(String text) {
      printColoredText(text, TextColor.RED);
   }

   public void showResponse(String text) {
      printColoredText(text, TextColor.BLUE);
   }

   public void showDone(String text) {
      printColoredText(text, TextColor.GREEN);
   }

   public void showNotDone(String text) {
      printColoredText(text, TextColor.RED);
   }

   /**
    * Prints the initial welcome logo and greeting message to the user.
    */
   public void showWelcome() {
      String logo = """
            Hello! I'm Sigmund
            What can I do for you?
            I love Kopi C Kosong!""";
      System.out.println(LINE);
      printColoredText(logo, TextColor.BLUE);
      System.out.println(LINE);
   }

   /**
    * Prints a detailed list of available commands and their usage instructions.
    */
   public void showHelp() {
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

            mark (or tick) <task_number>
               Marks a task as done.
               Ex: mark 1

            unmark (or untick) <task_number>
               Marks a task as not done.
               Ex: unmark 1

            delete <task_number>
               Deletes a task from your list.
               Ex: delete 2

            find <keyword>
               Finds tasks matching the specified keyword in their description.
               Ex: find book

            --- OTHER ---
            help
               Shows this help message.

            bye (or exit)
               Exits the program.
            """;

      printColoredText(helpMessage, TextColor.BLUE);
   }
}
