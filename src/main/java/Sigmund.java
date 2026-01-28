import java.util.Scanner;

public class Sigmund {
    public static Task[] storage = new Task[100];
    public static int itemCount = 0;

    public static void printList() {
        for (int i = 0; i < itemCount; i++) {
            String tick = storage[i].getDoneStatus() ? "[X]" : "[ ]";
            String listItem = "%d." + tick + " %s";
            String result = String.format(listItem, i + 1, storage[i].getTaskDescription());
            System.out.println(result);
        }
    }

    public static void main(String[] args) {

        String logo = """
                ____________________________________________________________
                Hello! I'm Sigmund
                What can I do for you?
                I love Kopi C Kosong!
                ____________________________________________________________""".strip();
        System.out.println(logo);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine().toLowerCase();
            System.out.println("____________________________________________________________");

            if (line.equals("bye") || line.equals("exit")) {
                System.out.println("BYE BYE! See you again!");
                System.out.println("____________________________________________________________");
                break;
            } else if (line.equals("list")) {
                System.out.println("Here are the tasks in your list!");
                printList();
            } else if (line.contains("mark")) {
                int taskNumber = Integer.parseInt(line.split(" ")[1]);
                if (taskNumber <= itemCount && taskNumber != 0) {
                    if (line.contains("unmark")) {
                        storage[taskNumber - 1].setDoneStatus(false);
                        System.out.println("OK, I've marked this task as not done yet:");
                        printList();
                    } else {
                        storage[taskNumber - 1].setDoneStatus(true);
                        System.out.println("GREAT JOB! I've marked this task as done::");
                        printList();
                    }
                } else {
                    System.out.println("Aww you haven't added THAT many tasks!");
                }
            } else {
                Task newTask = new Task(false, line);
                storage[itemCount] = newTask;
                itemCount++;
                System.out.println("added: " + line);
            }
            System.out.println("____________________________________________________________");
        }
        scanner.close();
    }
}
