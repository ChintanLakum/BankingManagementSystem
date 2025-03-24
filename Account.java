import java.util.*;
import java.text.*;
import java.time.*;

enum AccountType {
    CURRENT,
    SAVINGS,
    FIXED_DEPOSITE
}

enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    SEND,
    RECIEVE
}

public class Account {
    private long accountNumber;
    private AccountType accountType;
    private Person accountHolder;
    private int balance;
    private String createdAt;

    private static Scanner input = new Scanner(System.in);

    public Account(Person accountHolder, AccountType accountType, int initialDeposite) {
        this.accountType = accountType;
        this.accountHolder = accountHolder;
        this.balance = initialDeposite;
        this.accountNumber = Helper.generateUniqueAccountNumber();

        Date date = new Date();
        SimpleDateFormat frt = new SimpleDateFormat("dd/MM/yyyy");
        this.createdAt = frt.format(date);
    }

    public Account() {
    }

    public long getaccountNumber() {
        return this.accountNumber;
    }

    public AccountType getaccountType() {
        return this.accountType;
    }

    public Person getaccountHolder() {
        return this.accountHolder;
    }

    public int getBalance() {
        return this.balance;
    }

    public String getcreatedAt() {
        return this.createdAt;
    }

 
    public void setBalance(int amount, Task taskType) {
        if (taskType == Task.DEPOSIT) {
            this.balance += amount;
        } 
        else if (taskType == Task.WITHDRAW) {
            this.balance -= amount;
        } 
        else {
            this.balance = amount;
        }
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setAccountHolder(Person accountHolder) {
        this.accountHolder = accountHolder;
    }
    private void setDate(Date date) {
        this.createdAt = String.valueOf(date);
    }


    public static void createAccount() {
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.print("Enter Your mobile Number: ");
        String mobileNumber = input.nextLine();

        while (true) {
            if (mobileNumber.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("invalid Mobile No...!\nReenter the mobile number:");
                mobileNumber = input.nextLine();
            }
        }

        System.out.print("Enter Your Address: ");
        String address = input.nextLine();

        System.out.println("Enter Which type of Account You want to open: ");
        System.out.println("\t1. Saving Account\t\t Minimum Initial Deposite = 500₹. ");
        System.out.println("\t2. Current Account\t\t Minimum Initial Deposite = 500₹.");
        System.out.println("\t3. Fixed Deposite Account\t Minimum Initial Deposite = 10000₹.");
        char type = input.nextLine().charAt(0);

        AccountType accountType = null;
        int minimumInitialDeposit = 0;
        switch (type) {

            case '1':
                accountType = AccountType.SAVINGS;
                minimumInitialDeposit = 500;
                break;

            case '2':
                accountType = AccountType.CURRENT;
                minimumInitialDeposit = 500;
                break;

            case '3':
                accountType = AccountType.FIXED_DEPOSITE;
                minimumInitialDeposit = 10000;
                break;

            default:
                while (type > '3' || type < '0') {
                    System.out.println("Wrong Choice...! ");
                    System.out.println("Renter Your Choice: ");
                    type = input.next().charAt(0);
                    input.nextLine();
                }
                break;
        }

        System.out.print("Enter your Deposite Amount: ");
        int initialDeposite = input.nextInt();
        input.nextLine();

        while (initialDeposite < minimumInitialDeposit) {

            System.out.println(
                    "Minimum Initial Deposite For" + accountType + " Account is " + minimumInitialDeposit + "...!");
            System.out.print("Renter your Initial Deposite: ");
            initialDeposite = input.nextInt();
            input.nextLine();
        }

        Person accountHolder = new Person(name, address, mobileNumber);
        Account account = new Account(accountHolder, accountType, initialDeposite);
        Database.writeAccount(account);
        Database.writeBalance(account);
        System.out.println("Account is sucessfully created your Account Number is: " + account.getaccountNumber());
    }

    public static void viewAccountDetails() {

        System.out.println("Enter Account Number: ");
        long accountNumber = input.nextLong();
        input.nextLine();

        Account account = Database.getAccount(accountNumber);

        while (account == null) {
            System.out.println("No such Account Found with account number " + accountNumber + " ...!");
            System.out.println("Enter Account Number: ");
            accountNumber = input.nextLong();
            account = Database.getAccount(accountNumber);
           
        }
        Database.readAccount(account);
       

    }

    public static void depositeOrWithdraw(Task taskType) {

        System.out.println("Enter Account Number:");
        long accountNumber = input.nextLong();
        Account account = Database.getAccount(accountNumber);
        int balance;

        if (account != null) {

            System.out.println("Enter Amount of Rupee: ");
            int amount = input.nextInt();
            input.nextLine();

            
            if (taskType == Task.DEPOSIT) {
                account.setBalance(amount, taskType);
                System.out.println(amount + "Rs is Sucessfully credited to Account " + account.getaccountNumber());
                System.out.println("Total balance: Rs " + account.getBalance() + " CR");
                Transaction.writeTransaction(account, account, TransactionType.DEPOSIT, amount);
                Database.writeBalance(account);
                
            } 
            else {
                if (amount < account.getBalance()) {
                    account.setBalance(amount, taskType);
                    System.out.println(amount + "Rs is Sucessfully Withdrawn From  Account " + account.getaccountNumber());
                    System.out.println("Total balance: Rs " + account.getBalance() + " CR");
                    Transaction.writeTransaction(account, account, TransactionType.WITHDRAW, amount );
                    Database.writeBalance(account);
                } 
                else {
                    System.out.println("You have not sufficient balance...!");
                    System.out.println("Available Balance: Rs " + account.getBalance() + "CR");
                }
            }
        }
        else {
            System.out.println("Entered wrong Account number...!");
            input.nextLine();
        }

    }

    public static void transfer() {
        long sourceAccountNumber;
        long destinationAccountNumber;
        int amount;
        Account sourceAccount;
        Account destinationAccount;

        System.out.println("Enter Source Account Number:");
        sourceAccountNumber = input.nextLong();
        input.nextLine();

        sourceAccount = Database.getAccount(sourceAccountNumber); // getaccountfromtransaction method me error hai arrayindexout of bound index3 is out of bound for length 1
        if (sourceAccount == null) {
            System.out.println("Entered wrong Account Number...!");
        }
        else {
            System.out.println("Enter Destination Account Number:");
            destinationAccountNumber = input.nextLong();
            input.nextLine();

            destinationAccount = Database.getAccount(destinationAccountNumber); // same as above
            destinationAccount.setBalance(Database.readBalance(destinationAccount), Task.NOTHING);

            if(destinationAccount == null) {
                System.out.println("No such account Found...!");
            }
            else if(destinationAccountNumber==sourceAccountNumber) {
                System.out.println("Source and Destination Account Number are same...!");
            }
            else {
        
                sourceAccount = Database.getAccount(sourceAccountNumber); // same as above
                sourceAccount.setBalance(Database.readBalance(sourceAccount), Task.NOTHING);
                System.out.println("Enter Amount:");
                amount = input.nextInt();
                input.nextLine();

                if (sourceAccount.getBalance() < amount) {

                    System.out.println("You have not sufficient balance...!");
                    System.out.println("Available Balance: Rs " + sourceAccount.getBalance() + "CR");
                } 
                else {
                    sourceAccount.setBalance(amount, Task.WITHDRAW);
                    destinationAccount.setBalance(amount, Task.DEPOSIT);
                    Transaction.writeTransaction(sourceAccount, destinationAccount, TransactionType.SEND, amount);
                    Transaction.writeTransaction(destinationAccount, sourceAccount, TransactionType.RECIEVE, amount);
                    Database.writeBalance(sourceAccount);
                    Database.writeBalance(destinationAccount);
                    System.out.println("Amount " +amount+ "Rs transffered from Account " +sourceAccountNumber+ " to Account " + destinationAccountNumber);
                    System.out.println("Total balance of Account " + sourceAccountNumber + ": Rs "+ sourceAccount.getBalance() + "CR");
                }
            }
        }
    }

    public static void getHistory(){
        System.out.println("Enter Account Number:");
        Scanner input = new Scanner(System.in);
        long accountNumber = input.nextLong();
        Account account= Database.getAccount(accountNumber);
        if(account != null){
            Transaction.transactionHistory(accountNumber);
        }
        else{
            System.out.println("Entered Wrong Account Number...!");
        }
        
        input.nextLine();
    }
}
