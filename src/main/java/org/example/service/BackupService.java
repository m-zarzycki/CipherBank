package org.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService
{
    private static final Path SOURCE = Path.of("bank.dat");
    private static final Path BACKUP_DIR = Path.of("backup");

    public static void backup() throws IOException {
        if (!Files.exists(SOURCE)) {
            System.out.println("Brak bazy do backupu.");
            return;
        }


        Files.createDirectories(BACKUP_DIR);

        String stamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path target = BACKUP_DIR.resolve("bank_" + stamp + ".dat");

        Files.copy(SOURCE, target, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Backup utworzony: " + target);
    }

    public static void restore(String backupFileName) throws IOException
    {
        Path source = BACKUP_DIR.resolve(backupFileName);
        if (!Files.exists(source))
        {
            System.out.println("Nie ma takiego backupu.");
            return;
        }
        Files.copy(source, SOURCE, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Baza przywrócona z: " + backupFileName);
    }
}
