import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        System.out.println("\n Hello there! \n"
                + "\n My name is Zelk, nice to meet you :D \n"
                + " What can I do for you?\n"
                + " ___________________________________________________________________");
        Scanner s = new Scanner(System.in);
        String input = "";
        while (!(input = s.nextLine()).equals("bye")) {
            System.out.println("        " + input
                    + "\n ___________________________________________________________________");
        }

        System.out.println("        " + "Bye! Hope to see you again soon! Thank you for chatting with me :)");



    }
}
