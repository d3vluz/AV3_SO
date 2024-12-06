package view;

import java.util.Scanner;

public class ShellView {
    private final Scanner scanner = new Scanner(System.in);

    public String getShellInput() {
        System.out.print("shell> ");
        return scanner.nextLine();
    }

    public void showOutput(String message) {
        System.out.println(message);
    }
}
