package Sigmund;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Todo> load() {
        ArrayList<Todo> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list (first run)
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Todo task = parseLineToTask(line);
                    tasks.add(task);
                } catch (IllegalArgumentException e) {
                    System.out.println("Warning: Corrupted line skipped -> " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves the current list of tasks to the file.
     * Creates directories if they don't exist.
     */
    public void save(ArrayList<Todo> tasks) {
        try {
            // Ensure directory exists
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (Todo task : tasks) {
                writer.write(task.toFileFormat() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Helper to convert a file line back into a Todo object.
     * Throws exception if data is malformed (Stretch Goal).
     */
    private Todo parseLineToTask(String line) {
        // Splitting by " | "
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid format");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Todo task;
        switch (type) {
            case "Todo":
                task = new Todo(description);
                break;
            case "Deadline":
                if (parts.length < 4)
                    throw new IllegalArgumentException("Missing deadline date");
                task = new Deadline(description, parts[3]);
                break;
            case "Event":
                if (parts.length < 4)
                    throw new IllegalArgumentException("Missing event dates");

                String[] times = parts[3].split("-");
                if (times.length < 2)
                    task = new Event(description, parts[3], "unknown");
                else
                    task = new Event(description, times[0], times[1]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
        task.setDone(isDone);
        return task;
    }
}
