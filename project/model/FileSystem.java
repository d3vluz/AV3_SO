package model;

import java.io.File;
import java.util.Arrays;

public class FileSystem {
    private final Journal journal;

    public FileSystem(Journal journal) {
        this.journal = journal;
    }

    public void copyFile(String sourcePath, String destinationPath) {
        journal.logOperation("Iniciando cópia do arquivo: " + sourcePath + " para " + destinationPath);
        File sourceFile = new File(sourcePath);
        File destinationFile = new File(destinationPath);
        if (sourceFile.exists()) {
            if (sourceFile.renameTo(destinationFile)) {
                journal.logOperation("Arquivo copiado com sucesso de " + sourcePath + " para " + destinationPath);
                System.out.println("Arquivo copiado com sucesso.");
            } else {
                journal.logOperation("Erro ao copiar arquivo de " + sourcePath);
                System.out.println("Falha ao copiar o arquivo.");
            }
        } else {
            journal.logOperation("Arquivo de origem não existe: " + sourcePath);
            System.out.println("Arquivo de origem não existe.");
        }
    }

    public void deleteFile(String filePath) {
        journal.logOperation("Iniciando exclusão do arquivo: " + filePath);
        File file = new File(filePath);
        if (file.exists() && file.delete()) {
            journal.logOperation("Arquivo apagado com sucesso: " + filePath);
            System.out.println("Arquivo apagado com sucesso.");
        } else {
            journal.logOperation("Erro ao apagar o arquivo: " + filePath);
            System.out.println("Falha ao apagar o arquivo.");
        }
    }

    public void renameFile(String oldName, String newName) {
        journal.logOperation("Iniciando renomeação do arquivo: " + oldName + " para " + newName);
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        if (oldFile.exists() && oldFile.renameTo(newFile)) {
            journal.logOperation("Arquivo renomeado com sucesso de " + oldName + " para " + newName);
            System.out.println("Arquivo renomeado com sucesso.");
        } else {
            journal.logOperation("Erro ao renomear o arquivo: " + oldName);
            System.out.println("Falha ao renomear o arquivo.");
        }
    }

    public void createDirectory(String dirPath) {
        journal.logOperation("Iniciando criação do diretório: " + dirPath);
        File dir = new File(dirPath);
        if (dir.mkdir()) {
            journal.logOperation("Diretório criado com sucesso: " + dirPath);
            System.out.println("Diretório criado com sucesso.");
        } else {
            journal.logOperation("Erro ao criar diretório: " + dirPath);
            System.out.println("Falha ao criar diretório.");
        }
    }

    public void deleteDirectory(String dirPath) {
        journal.logOperation("Iniciando exclusão do diretório: " + dirPath);
        File dir = new File(dirPath);
        if (dir.exists() && dir.isDirectory() && dir.delete()) {
            journal.logOperation("Diretório apagado com sucesso: " + dirPath);
            System.out.println("Diretório apagado com sucesso.");
        } else {
            journal.logOperation("Erro ao apagar o diretório: " + dirPath);
            System.out.println("Falha ao apagar o diretório.");
        }
    }
    
    public void listDirectory(String dirPath) {
        journal.logOperation("Listando conteúdo do diretório: " + dirPath);
        File dir = new File(dirPath);
        if (dir.exists() && dir.isDirectory()) {
            String[] content = dir.list();
            if (content != null && content.length > 0) {
                System.out.println("Conteúdo do diretório " + dirPath + ":");
                Arrays.stream(content).forEach(System.out::println);
            } else {
                System.out.println("Diretório está vazio.");
            }
        } else {
            System.out.println("Diretório não existe.");
        }
    }
}