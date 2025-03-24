import java.util.*;
import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.lang.*;

public class Transaction {
    private static String transaction = "TransactionHistory.csv";
    private static FileReader fr;
    private static BufferedReader br;
    private static String row;
    private static String[] rowDetails;

    public static void writeTransaction(Account sourceAccount, Account destinationAccount, TransactionType value,
            int amount) {
        try {

            FileWriter transactionWriter = new FileWriter(transaction, true);
            PrintWriter pr = new PrintWriter(transactionWriter);

            Date date = new Date();
            SimpleDateFormat frt = new SimpleDateFormat("dd/MM/yyyy");
            String transactionDate = frt.format(date);

            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String time = currentTime.format(fmt);
        
            pr.print(sourceAccount.getaccountNumber());
            pr.print(",");
            pr.print(transactionDate);
            pr.print(",");
            pr.print(destinationAccount.getaccountHolder().getname());
            pr.print(",");
            pr.print(destinationAccount.getaccountNumber());
            pr.print(",");

            if (value.equals(TransactionType.SEND) || value.equals(TransactionType.WITHDRAW)) {
                pr.print("0");
                pr.print(",");
                pr.print(amount);

            } else {
                pr.print(amount);
                pr.print(",");
                pr.print("0");
            }

            pr.print(",");
            pr.print(destinationAccount.getBalance());
            pr.print(",");
            pr.print(time);
            pr.print("\n");
            pr.flush();

        } catch (IOException e) {
            System.out.println("FILE NOT WRITEABLE.");
        }
    }

    public static void transactionHistory(long accountNumber) {
        try {

            // this is writter for account.txt file
            String filename = (accountNumber + ".txt");
            FileWriter historyWriter = new FileWriter(filename, true);
            historyWriter.write("Transaction History Of Account Number: " + accountNumber + ".\n");
            historyWriter.write("Date\t\t\t\tTime\t\t\t\t\t\t\t\tDetails\t\t\t\t\t\t\t\t\t\t\tDebit\t\t\t   Credit\t\t\t  Balance\t\n");

            // read created balance and write it
            String csvFile = "accounts.csv";
            FileReader fromAccount = new FileReader(csvFile);
            BufferedReader bufferedReader = new BufferedReader(fromAccount);
            String accountReader = bufferedReader.readLine();
            accountReader = bufferedReader.readLine();
            String[] accountDetails;

            while (accountReader != null) {
                accountDetails = accountReader.split(",");
                if (accountNumber == Long.parseLong(accountDetails[4])) {
                    historyWriter.write(accountDetails[0] + "\t\t\t"+String.valueOf(accountDetails[7])+"\t\t\t\t\tAccount Created \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+ accountDetails[6] + "\n");
                }
                accountReader = bufferedReader.readLine();
            }

            // read history
            FileReader fr = new FileReader(transaction);
            BufferedReader br = new BufferedReader(fr);
            String row = br.readLine();
            row = br.readLine();
            String[] rowDetails;

            while (row != null) {

                rowDetails = row.split(",");
                long sourceAccountNumber = Long.parseLong(rowDetails[0]);
                long destinationAccountNumber = Long.parseLong(rowDetails[3]);
                String date = rowDetails[1];
                int debit = Integer.parseInt(rowDetails[4]);
                int credit = Integer.parseInt(rowDetails[5]);
                int balance = Integer.parseInt(rowDetails[6]);

                if ((accountNumber == destinationAccountNumber)) {

                    historyWriter.write(date + "  \t\t");
                    historyWriter.write(rowDetails[7]+"\t\t\t\t");
                    if (destinationAccountNumber == sourceAccountNumber) {
                        if ((debit == 0) && (destinationAccountNumber == sourceAccountNumber)) {
                            historyWriter.write(
                                    "By Cash withdrawal\t\t\t\t\t\t\t\t\t\t\t" + credit + "\t\t\t\t\t\t\t\t\t\t");
                        } else {
                            historyWriter
                                    .write("By Cash Deposite\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   " + debit + "\t\t\t\t\t");
                        }

                    } else {

                        if ((debit == 0) && (destinationAccountNumber != sourceAccountNumber)) {
                            historyWriter.write("By Transfer Account " + sourceAccountNumber
                                    + " to Your Account\t\t\t\t\t\t\t\t" + credit + "\t\t\t\t\t");
                        } else {
                            historyWriter.write("By Transfer Your Account to " + sourceAccountNumber + " Account\t\t\t"
                                    + debit + "\t\t\t\t\t\t\t\t\t\t");
                        }

                    }
                    historyWriter.write(balance + " \n");
                }
                row = br.readLine();
            }
            historyWriter.close();
            System.out.println(
                    "Your transaction history file is created.\n Go to this pc > d drive >JAVA programming >BankingManagementSystem > your accountNumber.txt");
        } catch (IOException e) {
            System.out.println("FILE NOT CREATED...!");
        }
    }
}