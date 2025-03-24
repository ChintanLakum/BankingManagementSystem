import java.util.Random;

public class Helper {

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        }
        catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static long generateUniqueAccountNumber() {
        Random random = new Random();
        long accountNumber = 243000000000L + (random.nextLong(999999999 - 10000000 + 1));
        Account account = Database.getAccount(accountNumber);

        while(account!= null) {
                accountNumber = 2430000000000L + (random.nextLong(99999999 - 10000000 + 1));
                account = Database.getAccount(accountNumber);
        }
        return accountNumber;
    }
}