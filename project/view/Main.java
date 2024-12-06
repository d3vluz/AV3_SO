package view;
import controller.FileSystemController;
import model.FileSystem;
import model.Journal;

public class Main {
    public static void main(String[] args) {
        Journal journal = new Journal();
        FileSystem fileSystem = new FileSystem(journal);
        ShellView shellView = new ShellView();
        FileSystemController controller = new FileSystemController(fileSystem, shellView);

        controller.startShell();
    }
}
