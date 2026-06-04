package org.example.storage;

import org.example.model.User;
import org.example.security.AesCipher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Database
{
    private static final Path FILE = Path.of("bank.dat");

    public static void save(List<User> users) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        for (User u : users)
        {
            sb.append(u.getLogin()).append(":")
                    .append(u.getPasswordHash()).append(":")
                    .append(u.getBalance()).append("\n");

        }

        String encrypted = AesCipher.encrypt(sb.toString());
        Files.writeString(FILE, encrypted);
    }

        public static List<User> load() throws Exception
        {
            List<User> users = new ArrayList<>();

            if (!Files.exists(FILE))
            {
                return users;
            }

            String encrypted = Files.readString(FILE);
            String decrypted = AesCipher.decrypt(encrypted);

            String[] lines = decrypted.split("\n");

            for (String line : lines)
            {
                if (line.isBlank()) continue;
                String[] parts = line.split(":");
                String login = parts[0];
                String hash = parts[1];
                double balance = Double.parseDouble(parts[2]);
                users.add(new User(login, hash, balance));
            }

            return users;
        }
}
