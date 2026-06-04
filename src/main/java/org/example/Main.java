package org.example;

import org.example.model.User;
import org.example.service.BackupService;
import org.example.service.BankService;

import java.util.Scanner;

public class Main
{
    private static final Scanner scanner = new Scanner(System.in);
    private static BankService bank;

    public static void main(String[] args) throws Exception
    {
        bank = new BankService();
        mainMenu();
    }

    private static void mainMenu() throws Exception
    {
        while(true)
        {
            System.out.println("\n=== MiniBank ===");
            System.out.println("1. Rejestracja");
            System.out.println("2. Logowanie");
            System.out.println("0. Wyjście");
            System.out.print("Wybor: ");
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1" -> register();
                case "2" -> login();
                case "0" -> {
                    System.out.println("Do widzenia.");
                    return;
                }
                default -> System.out.println("Nieznana opcja.");
            }
        }
    }

    private static void register() throws Exception
    {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Hasło: ");
        String password = scanner.nextLine();

        if (bank.register(login, password))
        {
            System.out.println("Zarejestrowano.");
        }
        else
        {
            System.out.println("Login zajety.");
        }
    }

    private static void login() throws Exception
    {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Haslo: ");
        String password = scanner.nextLine();

        User u = bank.login(login, password);

        if (u == null)
        {
            System.out.println("Bledny login lub haslo.");
            return;
        }
        else
        {
            System.out.println("Zalogowano.");
            System.out.println("[DEBUG] Hash hasla: " + u.getPasswordHash());
            userMenu(u);
        }
    }

    private static void userMenu(User u) throws Exception
    {
        while(true)
        {
            System.out.println("\n--- Zalogowano jako " + u.getLogin() + " ---");
            System.out.println("1. Saldo");
            System.out.println("2. Wplata");
            System.out.println("3. Wyplata");
            System.out.println("4. Backup");
            System.out.println("5. Restore");
            System.out.println("0. Wyloguj");
            System.out.print("Wybor: ");
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1" -> System.out.println("Saldo: " + u.getBalance());
                case "2" -> deposit(u);
                case "3" -> withdraw(u);
                case "4" -> BackupService.backup();
                case "5" -> restore();
                case "0" -> {
                    System.out.println("Wylogowano.");
                    return;
                }
                default -> System.out.println("Nieznana opcja.");
            }
        }
    }

    private static void deposit(User u) throws Exception
    {
        System.out.println("Kwota wplaty: ");
        double amount = Double.parseDouble(scanner.nextLine());
        bank.deposit(u, amount);
        System.out.println("Wplacono. Saldo: " + u.getBalance());
    }

    private static void withdraw(User u) throws Exception
    {
        System.out.print("Kwota wyplaty: ");
        double amount = Double.parseDouble(scanner.nextLine());

        boolean ok = bank.withdraw(u, amount);

        if (ok)
        {
            System.out.println("Wyplacono. Saldo: " + u.getBalance());
        }
        else
        {
            System.out.println("Brak srodkow.");
        }
    }

    private static void restore() throws Exception
    {
        System.out.print("Nazwa pliku backup (np. bank_2026-06-04_15-30-00.dat): ");
        String fileName = scanner.nextLine();
        BackupService.restore(fileName);
        System.out.println("Uwaga: zrestartuj aplikacje, aby wczytac przywrocona baze.");
    }
}
