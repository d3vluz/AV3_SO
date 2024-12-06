package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Journal {
    private static final String JOURNAL_FILE = "file_system_journal.log";

    public void logOperation(String operation) {
        try (FileWriter writer = new FileWriter(JOURNAL_FILE, true)) {
            writer.write(new Date() + " - " + operation + "\n");
        } catch (IOException e) {
            System.err.println("Falha ao escrever no journal: " + e.getMessage());
        }
    }
}