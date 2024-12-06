package controller;

import model.FileSystem;
import view.ShellView;

public class FileSystemController {
    private final FileSystem fileSystem;
    private final ShellView shellView;

    public FileSystemController(FileSystem fileSystem, ShellView shellView) {
        this.fileSystem = fileSystem;
        this.shellView = shellView;
    }

    public void startShell() {
        shellView.showOutput("Bem-vindo ao Shell do Sistema de Arquivos!");
        String command;

        do {
            command = shellView.getShellInput();
            handleCommand(command);
        } while (!command.equalsIgnoreCase("exit"));

        shellView.showOutput("Saindo do Shell do Sistema de Arquivos.");
    }

    private void handleCommand(String command) {
        String[] tokens = command.split(" ");
        String operation = tokens[0];

        try {
            switch (operation) {
                case "copy" -> {
                    String sourcePath = tokens[1];
                    String destinationPath = tokens[2];
                    fileSystem.copyFile(sourcePath, destinationPath);
                }
                case "delete" -> {
                    String filePath = tokens[1];
                    fileSystem.deleteFile(filePath);
                }
                case "rename" -> {
                    String oldName = tokens[1];
                    String newName = tokens[2];
                    fileSystem.renameFile(oldName, newName);
                }
                case "mkdir" -> {
                    String dirPath = tokens[1];
                    fileSystem.createDirectory(dirPath);
                }
                case "rmdir" -> {
                    String dirPath = tokens[1];
                    fileSystem.deleteDirectory(dirPath);
                }
                case "list" -> {
                    String dirPath = tokens[1];
                    fileSystem.listDirectory(dirPath);
                }
                case "exit" -> shellView.showOutput("Finalizando o shell...");
                default -> shellView.showOutput("Comando não reconhecido: " + command);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            shellView.showOutput("Parâmetros insuficientes para o comando: " + operation);
        }
    }
}