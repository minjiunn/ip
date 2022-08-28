import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ListResourceBundle;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Duke {

    private static String indent = "        ";
    private static String divider = " ___________________________________________________________________";
    private enum taskTypes {TODO, DEADLINE, EVENT}

    private static ArrayList<Task> tasks = new ArrayList<>();

    private static void addTask(taskTypes taskType, String taskToAdd) throws DukeException.EmptyTaskException {
        switch (taskType) {
            case TODO:
                String task = taskToAdd.substring(5);
                if (task.isBlank()) {
                    throw new DukeException.EmptyTaskException();
                } else {
                    tasks.add(new Todo(task));
                }
                break;

            case DEADLINE:
                int deadlineChar = taskToAdd.indexOf("/");
                String taskDesc = taskToAdd.substring(9, deadlineChar);
                String deadlineInput = taskToAdd.substring(deadlineChar + 4);
                if (taskDesc.isBlank() || deadlineInput.isBlank()) {
                    throw new DukeException.EmptyTaskException();
                } else {
                    try {
                        LocalDate deadline = LocalDate.parse(deadlineInput);
                        tasks.add(new Deadline(taskDesc, deadline.format(DateTimeFormatter.ofPattern("MMM d yyyy"))));
                    } catch (DateTimeParseException e) {
                        throw new DateTimeParseException("", "", 0);
                    }
                }
                break;

            case EVENT:
                int eventChar = taskToAdd.indexOf("/");
                String eventDesc = taskToAdd.substring(6, eventChar);
                String eventTimeInput = taskToAdd.substring(eventChar + 4);
                if (eventDesc.isBlank() || eventTimeInput.isBlank()) {
                    throw new DukeException.EmptyTaskException();
                } else {
                    try {
                        LocalDate eventTime = LocalDate.parse(eventTimeInput);
                        tasks.add(new Event(eventDesc, eventTime.format(DateTimeFormatter.ofPattern("MMM d yyyy"))));
                    } catch (DateTimeParseException e) {
                        throw new DateTimeParseException("", "", 0);
                    }
                }
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println("\n Hello there!\n"
                + "\n My name is Zelk, nice to meet you :D\n"
                + " What can I do for you?\n"
                + divider);

        Scanner s = new Scanner(System.in);
        String input = "";

        while ((input = s.nextLine()) != null) {
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println(indent + "These are the tasks in your list so far!");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(indent + (i+1) + ". " + tasks.get(i));
                }
                System.out.println(divider);
            } else if (input.contains("unmark")) {
                Integer taskNo = Integer.valueOf(input.substring(7));
                tasks.get(taskNo - 1).markAsUndone();
                System.out.println(indent + "Okay, I'll mark this task as undone: \n"
                        + indent + " " + tasks.get(taskNo - 1));
                System.out.println(divider);
            } else if (input.contains("mark")) {
                Integer taskNo = Integer.valueOf(input.substring(5));
                tasks.get(taskNo - 1).markAsDone();
                System.out.println(indent + "alright! I've marked this task as done :) \n"
                        + indent + " " + tasks.get(taskNo - 1));
                System.out.println(divider);
            } else if (input.contains("delete")) {
                Integer taskNo = Integer.valueOf(input.substring(7));
                Task taskToRemove = tasks.get(taskNo - 1);
                System.out.println(indent + "got it, i'll remove this task from your list: \n"
                        + indent + " " + taskToRemove);
                tasks.remove(taskToRemove);
                System.out.println(indent + "You now have " + tasks.size() + " total tasks in your list \n"
                        + divider);
            } else {
                if (input.contains("todo")) {
                    try {
                        addTask(taskTypes.TODO, input);
                    } catch (DukeException.EmptyTaskException | IndexOutOfBoundsException e) {
                        System.out.println(indent + "oops, the description of your todo seems to be incomplete!\n"
                                + divider);
                        continue;
                    }
                } else if (input.contains("deadline")) {
                    try {
                        addTask(taskTypes.DEADLINE, input);
                    } catch (DukeException.EmptyTaskException | IndexOutOfBoundsException e) {
                        System.out.println(indent + "oops, the description of the deadline seems to be incomplete!\n"
                            + divider);
                        continue;
                    } catch (DateTimeParseException e) {
                        System.out.println(indent + "Sorry :( You have given me an invalid date, please check"
                                + " your date format again!\n" + divider);
                        continue;
                    }
                } else if (input.contains("event")) {
                    try {
                        addTask(taskTypes.EVENT, input);
                    } catch (DukeException.EmptyTaskException | IndexOutOfBoundsException e) {
                        System.out.println(indent + "oops, the description of the event seems to be incomplete!\n"
                            + divider);
                        continue;
                    } catch (DateTimeParseException e) {
                        System.out.println(indent + "Sorry :( You have given me an invalid date, please check"
                                + " your date format again!\n" + divider);
                        continue;
                    }
                } else {
                    System.out.println(indent + "I'm sorry, I'm not sure I understand what that means :(\n" + divider);
                    continue;
                }

                System.out.println(indent + "new task added: " + tasks.get(tasks.size() - 1)
                        + "\n" + indent + "You now have " + tasks.size() + " total tasks in your list"
                        + "\n" + divider);
            }
        }

        System.out.println(indent + "Bye! Hope to see you again soon! Thank you for chatting with me :)");
    }
}
