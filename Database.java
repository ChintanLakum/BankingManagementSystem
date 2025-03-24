import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Database {

    private static String csvFile = "accounts.csv";
    private static String balance = "Balance.csv";
    private static FileReader fr;
    private static BufferedReader br;
    private static String row;
    private static String[] rowDetails;

    public static Account getAccount(long accountNumber) {
        try {

            initializeReadersAndWriters();
            row = br.readLine();

            Account account = new Account();
            
            while (row != null) {

                rowDetails = row.split(","); // Split by comma
                String accountType = rowDetails[5];

                if (accountNumber == Long.parseLong(rowDetails[4])) {

                    account.setAccountNumber(accountNumber);
                    Person person = new Person(rowDetails[1], rowDetails[2], rowDetails[3]);
                    account.setAccountHolder(person);
                    account.setBalance(Database.readBalance(account), Task.NOTHING);

                    if (accountType.equals("CURRENT")) {
                        account.setAccountType(AccountType.CURRENT);
                    } else if (accountType.equals("FIXED_DEPOSITE")) {
                        account.setAccountType(AccountType.FIXED_DEPOSITE);
                    } else {
                        account.setAccountType(AccountType.SAVINGS);
                    }
                    return account;
                }
                row = br.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND WITH GIVEN PATH.");
        } catch (IOException e) {
            System.out.println("FILE NOT READABLE.");
        }
        return null;
    }

    
    public static void initializeReadersAndWriters() throws IOException, FileNotFoundException {

        fr = new FileReader(csvFile);
        br = new BufferedReader(fr);
        row = br.readLine();
    }

    public static void readAccount(Account account) {

        try {
            initializeReadersAndWriters();
            row = br.readLine();

            String acountHolderName = null;
            String mobileNumber = null;
            String address = null;
            String accountType = null;
            int balance = 0;
            long accountNumber = 0;
            String createdAt = null;

            while (row != null) {

                rowDetails = row.split(",");
                accountNumber = account.getaccountNumber();

                if (accountNumber == Long.parseLong(rowDetails[4])) {
                    createdAt = rowDetails[0];
                    acountHolderName = rowDetails[1];
                    address = rowDetails[2];
                    mobileNumber = rowDetails[3];
                    accountType = rowDetails[5];
                    // balance = Integer.parseInt(rowDetails[6]);
                    balance = Database.readBalance(account);

                }
                row = br.readLine();
            }

            System.out.println("----- Following Are the Details. -----");
            System.out.println("Account Holder's Name:\t\t" + acountHolderName);
            System.out.println("Account Holder's Mobile Number:\t" + mobileNumber);
            System.out.println("Account Holder's Address:\t" + address);
            System.out.println("Account Number:\t\t\t" + accountNumber);
            System.out.println("Account Type:\t\t\t" + accountType);
            System.out.println("Balance:\t\t\t" + balance);
            System.out.println("Account created At: \t\t" + createdAt);
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND WITH GIVEN PATH.");
        } catch (IOException e) {
            System.out.println("FILE NOT READABLE.");
        }
    }

    public static void writeAccount(Account account) {
        try {

            FileWriter csvWriter = new FileWriter(csvFile, true);
            PrintWriter pr = new PrintWriter(csvWriter);

            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String time = currentTime.format(fmt);

            pr.print(account.getcreatedAt());
            pr.print(",");
            pr.print(account.getaccountHolder().getname());
            pr.print(",");
            pr.print(account.getaccountHolder().getaddress());
            pr.print(",");
            pr.print(account.getaccountHolder().getmobileNo());
            pr.print(",");
            pr.print(String.valueOf(account.getaccountNumber()));
            pr.print(",");
            pr.print(String.valueOf(account.getaccountType()));
            pr.print(",");
            pr.print(String.valueOf(account.getBalance()));
            pr.print(",");
            pr.print(time);
            pr.print("\n");
            pr.flush();

        } catch (IOException e) {
            System.out.println("FILE NOT WRITEABLE.");
        }
    }


    public static void writeBalance(Account account) {
        try {

            FileWriter balanceWriter = new FileWriter(balance, true);
            PrintWriter pr = new PrintWriter(balanceWriter);

            pr.print(String.valueOf(account.getaccountNumber()));
            pr.print(",");
            pr.print(String.valueOf(account.getBalance()));
            pr.print("\n");
            pr.flush();

        } catch (IOException e) {
            System.out.println("FILE NOT WRITEABLE.");
        }
    }

    public static int readBalance(Account account) {

        try {
            fr = new FileReader(balance);
            br = new BufferedReader(fr);
            row = br.readLine();
            row = br.readLine();

            int balance = 0;
            long accountNumber = account.getaccountNumber();
            while (row != null) {

                rowDetails = row.split(",");
                if (accountNumber == Long.parseLong(rowDetails[0])) {
                    balance = Integer.parseInt(rowDetails[1]);
                }
                row = br.readLine();
            }
            account.setBalance(balance, Task.NOTHING);
            return balance;

        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND WITH GIVEN PATH.");
            return 0;
        } catch (IOException e) {
            System.out.println("FILE NOT READABLE.");
            return 0;
        }
    }
}
