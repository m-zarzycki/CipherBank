package org.example.model;

public class User
{
    private String login;
    private String passwordHash;
    private double balance;

    public User(String login, String passwordHash, double balance)
    {
        this.login = login;
        this.passwordHash = passwordHash;
        this.balance = balance;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }
}
