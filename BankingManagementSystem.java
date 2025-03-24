import java.util.*;

enum Task {
    DEPOSIT,
    WITHDRAW,
    NOTHING
}

public class BankingManagementSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // Task task;

        while (true) {
            System.out.println(" ----- Enter your choice ----- ");
            System.out.println("\t Enter 1 For Creating a new Account ");
            System.out.println("\t Enter 2 For Account Details ");
            System.out.println("\t Enter 3 For Deposit Money To Account ");
            System.out.println("\t Enter 4 For Withdrow Money From Account ");
            System.out.println("\t Enter 5 For Money Transfer ");
            System.out.println("\t Enter 6 For Transaction History Of Account ");
            System.out.println("\t Enter 7 For Exit");
            
            char ch = input.next().charAt(0);
            input.nextLine();

            switch (ch) {

                case '1':
                    Account.createAccount();
                    Helper.delay(2000);
                    break;

                case '2':
                    Account.viewAccountDetails();
                    Helper.delay(3500);
                    break;

                case '3':
                    Account.depositeOrWithdraw(Task.DEPOSIT);
                    Helper.delay(2000);
                    break;

                case '4':
                    Account.depositeOrWithdraw(Task.WITHDRAW);
                    Helper.delay(2000);
                    break;

                case '5':
                    Account.transfer();
                    Helper.delay(2000);
                    break;

                case '6':
                    Account.getHistory();
                    break;

                case '7':
                    System.exit(0); 
                   break;

                default:
                    System.out.println("Entered wrong Choice...!\nPlease enter valid Choice.");
                    break;
            }
        }
    }
}