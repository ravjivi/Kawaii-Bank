/**
 * Write a description of class Main here.
 *
 * @Viraaj Ravji
 * 24.03.25
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.plaf.basic.BasicSplitPaneUI;
public class Main
{
    public static ArrayList<Accounts> accountsList = new ArrayList<Accounts>(); // Public so it can be referred in any class
    private static Scanner keyboard = new Scanner(System.in);
    private static DecimalFormat df = new DecimalFormat("0.00"); // Formats the output to 2dp
    private static double netDepWith; // Net Deposits and withdrawals

    public static void main(String[] args) {
        CSV.readCSV(); // Read the CSV file and store to the ArrayList
        System.out.println("Hello, welcome to Kawaii-Bank. How can I help you? 1-5");
        askCustomer();
    }

    private static void askCustomer() {
        System.out.println("1. Create an account for a new customer\n2. Close an account for an existing customer\n"+
                            "3. Get the balance of a customers's account\n4. Deposit into an account\n"+
                            "5. Withdraw from an account\n6. Quit");
        int input = intChecker(1,6);

        switch(input) {
        case 1: // Create acc
            createAccount();
            break;
        case 2: // Close acc
            closeAccount();
            break;
        case 3: // Get balance
            findBalance();
            break;
        case 4: // Deposit
            deposit();
            break;
        case 5: // Withdraw
            withdraw();
            break;
        case 6: // Quit
            dailySummary();
            break;
        }
    }

    private static void askCustomer2() {
        keyboard.nextLine();
        System.out.println("Would you like any other assitance, y/n");
        String input = keyboard.nextLine();
        if (input.equals("y")) {
            askCustomer();
        } else if (input.equals("n")) {
            dailySummary();
        }
    }
    
    private static int intChecker(int lower, int upper) {
        boolean found = false; // found valid int
        int input;
        while (!found) {
            while(!keyboard.hasNextInt())  {
                keyboard.nextLine();
                System.out.println("Please enter a number");
            } 
            input = keyboard.nextInt(); 
            if (input >= lower && input <= upper) {
                found = true;
                return input;
            } else {
                System.out.println("Please enter a number between "+lower+" and "+upper);
            }
        }
        return 0;
    }
    
    private static void createAccount() {
        String name;
        String accountType;
        String address;
        System.out.println("What is your full name");
        keyboard.nextLine();
        while(!keyboard.hasNextLine())  {
                keyboard.nextLine();
                System.out.println("Please enter a String");
        } 
        name = keyboard.nextLine();
        
        System.out.println("What kind of account would you like to open, 1-3");
        System.out.println("1. Everyday\n2. Savings\n3. Current");
        int temp = intChecker(1,3);
        if (temp == 1) {accountType = "Everyday";}
        if (temp == 2) {accountType = "Savings";}
        if (temp == 3) {accountType = "Current";} 
        else {accountType = "Everyday";} // In case accountType is null
        
        System.out.println("What is your address");
        keyboard.nextLine();
        while (!keyboard.hasNextLine())  {
                keyboard.nextLine();
                System.out.println("Please enter a String");
        } 
        address = keyboard.nextLine();
        System.out.println("Thank you for opening an account with us "+name);
        accountsList.add(new Accounts(name, accountType, address));
        askCustomer2();
    }
    
    private static void closeAccount() {
        System.out.println("What is the full name of the account you would like to close");
        String accName = keyboard.nextLine().toLowerCase();
       
        System.out.println("This account is now closed");
        accountsList.remove(Accounts.returnIndex(accName));    
        askCustomer2(); 
    }
    
    /* 
    public static boolean verifyAccount(String name) {
        System.out.println("I need to verify if this is your account");
        System.out.println("What is the address you have listed for this account");
        String address = keyboard.nextLine().toLowerCase();
        for (int i=0; i<accountsList.size(); i++) {
            if (name.equals(accountsList.get(i).getName().toLowerCase())) {
               if (address.equals(accountsList.get(i).getAddress().toLowerCase())) {
                return true;
                } 
            }
        }        
        return false;
    } */

    private static void findBalance() {
        keyboard.nextLine();
        System.out.println("What is the name of your account");
        String accName = keyboard.nextLine().toLowerCase();
        System.out.println("Balance: $"+df.format(accountsList.get(Accounts.returnIndex(accName)).getBalance()));
        askCustomer2();
    }

    private static void deposit() {
        keyboard.nextLine();
        System.out.println("What is the name of your account");
        String accName = keyboard.nextLine().toLowerCase();
        System.out.println("How much would you like to deposit");
        Double depAmount = keyboard.nextDouble();
        accountsList.get(Accounts.returnIndex(accName)).depositToAccount(depAmount);
        System.out.println("New balance: $"+df.format(accountsList.get(Accounts.returnIndex(accName)).getBalance()));
        netDepWith += depAmount;
        askCustomer2();
    }

    private static void withdraw() {
        keyboard.nextLine();
        System.out.println("What is the name of your account");
        String accName = keyboard.nextLine().toLowerCase();
        System.out.println("How much would you like to withdraw");
        Double withAmount = keyboard.nextDouble();
        accountsList.get(Accounts.returnIndex(accName)).withdrawFromAccount(withAmount);
        System.out.println("New balance: $"+df.format(accountsList.get(Accounts.returnIndex(accName)).getBalance()));
        netDepWith -= withAmount;
        askCustomer2();
    }

    private static void dailySummary() {
        //Print total balance of all accounts
        double balTotal = 0;
        for (int i=0; i<accountsList.size(); i++) {
            balTotal += accountsList.get(i).getBalance();  
        }
        System.out.println("Total cash in the bank: $"+df.format(balTotal));

        //net dep/withdral
        System.out.println("Net cash deposited/withdrawn: $"+df.format(netDepWith));

        //write to csv
        CSV.wrtieCSV();
    }
    
    
}
