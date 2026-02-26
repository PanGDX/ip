package Sigmund;

import java.nio.file.Paths;
import java.util.Scanner;
import static Sigmund.Ui.*;

/**
 * The main entry point for the Sigmund chatbot application.
 * Handles the input loop and command delegation.
 */
public class Sigmund {
    private static final String FILE_PATH = Paths.get("data", "tasks.txt").toString();
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    public Sigmund() {
        this.ui = new Ui();
        this.storage = new Storage(FILE_PATH);
        this.taskList = new TaskList(storage);
        this.parser = new Parser();
    }

    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                String line = scanner.nextLine().strip();
                System.out.println(LINE);
                parser.handleCommand(line, scanner, taskList);
            } catch (SigmundException e) {
                ui.showError(e.toString());
            } catch (BreakSignal e) {
                isRunning = false;
            } catch (NumberFormatException e) {
                ui.showError("The argument needs to be a number!");
            } catch (Exception e) {
                ui.showError("NOOOOOO!!! A fatal error occurred: " + e.getMessage());
            } finally {
                System.out.println(LINE);
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new Sigmund().run();
    }

}
