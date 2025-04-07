package src;

/**
 * Write a description of class Main here.
 *
 * @Viraaj Ravji
 * 24.03.25
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
public class Main
{
    public static ArrayList<Accounts> accountsList = new ArrayList<Accounts>(); // Public so it can be referred in any class
    private static Scanner keyboard = new Scanner(System.in);
    private static DecimalFormat df = new DecimalFormat("0.00"); // Formats the output to 2dp
    private static double netDepWith; // Net Deposits and withdrawals

    public static void main(String[] args) {
        CSV CSV1 = new CSV();
        CSV1.readCSV(); // Read the CSV file and store to the ArrayList
        System.out.println("Hello, welcome to Kawaii-Bank. How can I help you? 1-6");
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
        System.out.println("Would you like any other assitance, y/n");
        String input = keyboard.nextLine();
        if (input.toLowerCase().equals("y")) {
            askCustomer();
        } else if (input.toLowerCase().equals("n")) {
            dailySummary();
        }
    }
    
    private static int intChecker(int lower, int upper) {
        int input = 0;
        while (true) {
            while(!keyboard.hasNextInt())  {
                keyboard.nextLine();
                System.out.println("Please enter a number");
            } 
            input = keyboard.nextInt(); 
            if (input >= lower && input <= upper) {
                break;
            } else {
                System.out.println("Please enter a number between "+lower+" and "+upper);
            }
        }
        return input;
    }
    
    private static boolean stringChecker(String text, String checker, boolean numAllowed) {
        if (text.isBlank()) {
            System.out.println(checker+" cannot be blank");
            return false;
        } else if (text.contains(",") || text.contains(".") || text.contains("/") && !numAllowed || text.contains(";") || text.contains("'")
                || text.contains("[") || text.contains("]") || text.contains("=") || text.contains("<") || text.contains("~")
                || text.contains(">") || text.contains("?") || text.contains(":") || text.contains("{") || text.contains("}")
                || text.contains("|") || text.contains("_") || text.contains("+") || text.contains("(") || text.contains(")")
                || text.contains("!") || text.contains("@") || text.contains("#") || text.contains("$") || text.contains("%")
                || text.contains("^") || text.contains("&") || text.contains("*") || text.contains("`")) {
                    System.out.println(checker+" cannot contain special characters");
                    return false;
        } else if (text.length() > 70) {
            System.out.println("Name is too long and does not obey a NZ legal name\nIt cannot be greater than 70 characters");
            return false;
        }
        for (int i=0; i<text.length(); i++) {
            if (Character.isDigit(text.charAt(i)) && !numAllowed) {
                System.out.println(text);
                System.out.println(checker+" cannot contain integers");
                return false;
            }
        }
        return true;
    }
    private static boolean doubleDPChecker(double number) {
        int dp = 0;
        String numberLine = Double.toString(number);
        for (int i=0; i<numberLine.length(); i++) {
            if (numberLine.charAt(i) == '.') { 
                dp = numberLine.length() - (i+1);
            }
        }
        if (dp > 2) {
            return false;
        } else {
            return true;
        }
        
    }
    
    private static void createAccount() {
        String name;
        String accountType = "Everday";
        String address;
        String city;
        keyboard.nextLine();
        System.out.println("What is your full name");
        name = keyboard.nextLine();
        while (!stringChecker(name, "Name", false)) {
            name = keyboard.nextLine();
        }
        
        System.out.println("What kind of account would you like to open, 1-3");
        System.out.println("1. Everyday\n2. Savings\n3. Current");
        int temp = intChecker(1,3);
        if (temp == 1) {accountType = "Everyday";}
        if (temp == 2) {accountType = "Savings";}
        if (temp == 3) {accountType = "Current";} 

        keyboard.nextLine();
        System.out.println("What is your address");
        address = keyboard.nextLine();
        while (!stringChecker(address, "Address", true)) {
            address = keyboard.nextLine();
        }
        System.out.println("What is your city");
        city = keyboard.nextLine();
        while (!stringChecker(city, "City", false)) {
            city = keyboard.nextLine();
        }
        System.out.println("Thank you for opening an account with us "+name);
        accountsList.add(new Accounts(name, address+" "+city, accountType));
        askCustomer2();
    }
    
    private static void closeAccount() {
        System.out.println("What is the account number/name of the account you would like to close");
        keyboard.nextLine();
        String accID = checkMultipleAccounts(keyboard.nextLine());
        while (!(Accounts.returnIndex(accID) >= 0)) {
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = keyboard.nextLine();
        }
        System.out.println("This account is now closed");
        accountsList.remove(Accounts.returnIndex(accID));    
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
        System.out.println("What is the account number/name of your account");
        String accID = checkMultipleAccounts(keyboard.nextLine());
        while (!(Accounts.returnIndex(accID) >= 0)) {
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = keyboard.nextLine();
        }
        System.out.println("Balance: $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance()));
        askCustomer2();
    }

    private static void deposit() {
        keyboard.nextLine();
        System.out.println("What is the account number/name of your account");
        String accID = checkMultipleAccounts(keyboard.nextLine());
        while (!(Accounts.returnIndex(accID) >= 0)) {
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = keyboard.nextLine();
        }
        System.out.println("How much would you like to deposit. You currently have $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance()));
        Double depAmount = 0.0;
        while (true) {
            if (keyboard.hasNextDouble()) {
                depAmount = keyboard.nextDouble();
                if (doubleDPChecker(depAmount)) {
                    break;  
                } else {
                    System.out.println("We only accept money with 2 decimal places or less");
                }
            } else {
                System.out.println("Please enter a integer/double");
                keyboard.nextLine();  // Consume the invalid input
            }
        }

        accountsList.get(Accounts.returnIndex(accID)).depositToAccount(depAmount);
        System.out.println("New balance: $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance()));
        netDepWith += depAmount;
        askCustomer2();
    }

    private static void withdraw() {
        keyboard.nextLine();
        System.out.println("What is the account number/name of your account");
        String accID = checkMultipleAccounts(keyboard.nextLine());
        while (!(Accounts.returnIndex(accID) >= 0)) {
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = keyboard.nextLine();
        }
        System.out.println("How much would you like to withdraw. You currently have $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance()));
        Double withAmount = 0.0;
        while(true) {
            if (keyboard.hasNextDouble()) {
                withAmount = keyboard.nextDouble();
                if (withAmount <= 5000) {
                    if (checkDebt(accID, withAmount)) {
                        if (doubleDPChecker(withAmount)) {
                            break;
                        } else {
                            System.out.println("We only withdraw money with 2 decimal places or less");
                        }
                    } else {
                        System.out.println("You cannot withdraw this much");
                    }
                } else {
                    System.out.println("You cannot withdraw more than $5000");
                }
            }
        }
        accountsList.get(Accounts.returnIndex(accID)).withdrawFromAccount(withAmount);
        System.out.println("New balance: $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance()));
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
        CSV.writeCSV();
    }
    
    private static String checkMultipleAccounts(String accName) {
        if (Character.isLetter(accName.charAt(0))) {
            int x=0;
            for (int i=0; i<Main.accountsList.size(); i++) {
                if (accName.toLowerCase().equals(Main.accountsList.get(i).getName().toLowerCase())) {
                    x++;
                } 
            }  
            if (x>1) { // If there is more than 1 account listed under this name
                System.out.println("You have multiple accounts under this name, you will need to enter the account number");
                accName = keyboard.nextLine().toLowerCase();
            } 
        }
        return accName;
    }
    
    private static boolean checkDebt(String accID, double withAmount) {
        if ((accountsList.get(Accounts.returnIndex(accID)).getBalance()) - withAmount < 0 &&  accountsList.get(Accounts.returnIndex(accID)).getAccountType().equals("Savings")
            || (accountsList.get(Accounts.returnIndex(accID)).getBalance()) - withAmount < 0 &&  accountsList.get(Accounts.returnIndex(accID)).getAccountType().equals("Everyday")
            || (accountsList.get(Accounts.returnIndex(accID)).getBalance()) - withAmount < -1000 &&  accountsList.get(Accounts.returnIndex(accID)).getAccountType().equals("Current")) { 
            // If the amount going to be withdrawn will make the balance negative for everyday or savings
            // Or if the amount withdrawn from a current account will go below a $1000 overdraft
            return false;
        } else {
            return true;
        }
    }
}
