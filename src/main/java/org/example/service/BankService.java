package org.example.service;

import org.example.model.User;
import org.example.security.PasswordHasher;
import org.example.storage.Database;

import java.util.List;

public class BankService
{
    private List<User> users;

    public BankService() throws Exception
    {
        this.users = Database.load();
    }

    private User findUser(String login)
    {
        for (User u : users)
        {
            if (u.getLogin().equals(login))
            {
                return u;
            }
        }
        return null;
    }

    public boolean register(String login, String password) throws Exception
    {
        if (findUser(login) != null)
        {
            return false;
        }

        String hash = PasswordHasher.hash(password);
        User u = new User(login, hash, 0);
        users.add(u);
        Database.save(users);
        return true;
    }

    public User login(String login, String password)
    {
        User u = findUser(login);

        if (u == null)
        {
            return null;
        }

        if (PasswordHasher.verify(password, u.getPasswordHash()))
        {
            return u;
        }
        else
        {
            return null;
        }
    }

    public void deposit(User u, double amount) throws Exception
    {
        u.setBalance(u.getBalance() + amount);
        Database.save(users);
    }

    public boolean withdraw(User u, double amount) throws Exception
    {
        if (amount > u.getBalance())
        {
            return false;
        }

        u.setBalance(u.getBalance() - amount);
        Database.save(users);
        return true;
    }
}
