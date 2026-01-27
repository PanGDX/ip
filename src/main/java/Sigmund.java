import java.util.Scanner;

public class Sigmund {
    public static String[] storage = new String[100];
    public static int itemCount = 0;

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
            String line = scanner.nextLine();
            System.out.println("____________________________________________________________");

            if (line.toLowerCase().equals("bye") || line.toLowerCase().equals("exit")) {
                System.out.println("BYE BYE! See you again!");
                System.out.println("____________________________________________________________");
                break;
            }
            else if (line.equals("list")) {
                for (int i = 0; i < itemCount; i++) {
                    System.out.println((i + 1) + ". " + storage[i]);
                }
            } 
            else {
                // Save the input to our class-wide variable
                storage[itemCount] = line;
                itemCount++;
                System.out.println("added: " + line);
            }
            System.out.println("____________________________________________________________");
        }
        scanner.close();
    }
}
