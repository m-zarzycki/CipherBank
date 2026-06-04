# CipherBank

Prosta aplikacja bankowa w Javie demonstrująca mechanizmy bezpieczeństwa danych:
hashowanie haseł, szyfrowanie bazy danych oraz kopie zapasowe z odzyskiwaniem.

Projekt zaliczeniowy z przedmiotu **Bezpieczeństwo systemów informatycznych**.

## Funkcje

- Rejestracja i logowanie użytkowników
- Hashowanie haseł algorytmem **BCrypt** (z solą)
- Szyfrowanie całej bazy danych algorytmem **AES-256** (tryb CBC)
- Operacje na koncie: podgląd salda, wpłata, wypłata
- Tworzenie kopii zapasowej i odzyskiwanie bazy

## Architektura

Aplikacja zbudowana warstwowo:

| Pakiet     | Odpowiedzialność                                  |
|------------|---------------------------------------------------|
| `model`    | Klasa `User` (login, hash hasła, saldo)           |
| `security` | `PasswordHasher` (BCrypt), `AesCipher` (AES)      |
| `storage`  | `Database` — zapis/odczyt zaszyfrowanego pliku    |
| `service`  | `BankService` (logika), `BackupService` (kopie)   |
| `Main`     | Interfejs konsolowy                               |

## Technologie

- Java
- Apache Maven
- jBCrypt (org.mindrot)
- javax.crypto (AES z biblioteki standardowej JDK)

## Uruchomienie

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.example.Main"
```

Lub uruchom klasę `Main` bezpośrednio z poziomu IDE.