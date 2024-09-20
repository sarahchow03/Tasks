package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Task manager (using streams)");
        Datamanager dataManager = new Datamanager("./data/data.txt");
        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);
        printDataWithStreams(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);
        printDeadlinesWithStreams(tasksData);

        System.out.println("Total number of deadlines (iterating): " + countDeadlines(tasksData));
        System.out.println("Total number of deadlines (streams): " + countDeadlinesWithStreams(tasksData));

        ArrayList<Task> filteredList = filterTasksByString(tasksData, "11");
        printAllData(filteredList);

    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesWithStreams(ArrayList<Task> tasks) {
        int count = (int) tasks.stream()
                .filter(t -> t instanceof Deadline)
                .count(); // terminal operation; aggregate operation
        return count;
    }

    public static void printAllData(ArrayList<Task> tasksData) {
        System.out.println("Printing data with iteration");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataWithStreams(ArrayList<Task> tasks) {
        System.out.println("Printing data with streams");
        tasks.stream()                         // create a stream
                .forEach(System.out::println); // terminal operator
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        System.out.println("Printing deadline with iteration");
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesWithStreams(ArrayList<Task> tasks) {
        System.out.println("Print deadlines using streams");
        tasks.stream()
                .filter(t -> t instanceof Deadline)
                .sorted((t1, t2) -> t1.getDescription().compareTo(t2.getDescription()))
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasks, String filterString) {
        ArrayList<Task>  filteredList = (ArrayList<Task>) tasks.stream()
                .filter(t -> t.getDescription().contains(filterString))
                .collect(toList());
        return filteredList;
    }
}
