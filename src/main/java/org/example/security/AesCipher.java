package org.example.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class AesCipher
{
    private static final String SECRET = "mojeTajneHaslo123"; // hasło główne, z którego wyprowadzany jest klucz szyfrujący
    private static final byte[] IV = new byte[16];  // wektor inicjujący (IV) wymagany przez tryb CBC, 16 = rozmiar bloku AES

    private static SecretKeySpec getKey() throws Exception
    {
        byte[] key = MessageDigest.getInstance("SHA-256") // SHA-256 zamienia hasło na 32 bajty = 256 bitów
                        .digest(SECRET.getBytes(StandardCharsets.UTF_8)); // hasło -> bajty -> skrót
        return new SecretKeySpec(key, "AES"); // 32 bajty jako klucz przeznaczony dla algorytmu AES
    }

    public static String encrypt(String plainText) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // PKCS5 dopina dane do pełnego bloku
        cipher.init(Cipher.ENCRYPT_MODE, getKey(), new IvParameterSpec(IV)); // ustawienie trybu szyfrowania
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)); // zamiana tekstu na bajty i zastosowanie szyfru (bajty)
        return Base64.getEncoder().encodeToString(encrypted); // bajty bywają niedrukowalne, Base64 konwertuje do bezpiecznego tekstu
    }

    public static String decrypt(String encryptedText) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getKey(), new IvParameterSpec(IV)); // tryb odszyfrowania, ten sam klucz i IV co przy szyfrowaniu
        byte[] decoded = Base64.getDecoder().decode(encryptedText); // odwracanie na bajty
        byte[] decrypted = cipher.doFinal(decoded); // odszyfrowujemy na oryginalne bajty
        return new String(decrypted, StandardCharsets.UTF_8); // bajty na string
    }
}
