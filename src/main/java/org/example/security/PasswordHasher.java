package org.example.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher
{
    public static String hash(String plainPassword)
    {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean verify(String plainpassword, String hashed)
    {
        return BCrypt.checkpw(plainpassword, hashed);
    }
}
