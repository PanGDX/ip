import java.util.Scanner;

public class Sigmund {
    public static void main(String[] args) {
        String logo = """
____________________________________________________________
Hello! I'm Sigmund
What can I do for you?
I love Kopi C Kosong!
____________________________________________________________""".strip();
        System.out.println(logo);

        Scanner scanner = new Scanner(System.in);

        while(true){
            String line = scanner.nextLine(); 
            System.out.println("____________________________________________________________");
            
            if(line.equals("bye")){
                System.out.println("BYE BYE! See you again!");
                System.out.println("____________________________________________________________");
                break;
            }
            System.out.println(line);

            System.out.println("____________________________________________________________");
        }
        scanner.close(); 
    }
}
