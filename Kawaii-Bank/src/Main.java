package src;

/**
 * Contains most of the code
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

    /**
     * Method that is intially run to start the code
     * Creates new CSV object and calls the initial method to read the csv data
     * Welcomes the user and and calls the method to ask them 
    */
    public static void main(String[] args) {
        CSV CSV1 = new CSV();
        CSV1.readCSV(); // Read the CSV file and store to the ArrayList
        System.out.println("Hello, welcome to Kawaii-Bank. How can I help you? 1-6");
        askCustomer();
    }

    /**
     * This method is called without any parameters
     * 
     * It asks the user what they would like to do 
     * Calls the corosponding method to continue the code
    */
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

    /**
     * This method is called without any parameters
     * 
     * It is called after the user has already finished one task and asks if they would like to continue
     * If they want to continue it will call the first askCustomer() method where they re-enter 1-6
     * If they don't want to continue it will quit the program through the dailySummary
    */
    private static void askCustomer2() {
        System.out.println("Would you like any other assitance, y/n");
        while (true) {
            String input = keyboard.nextLine();
            if (input.toLowerCase().equals("y")) {
                askCustomer();
                break;
            } else if (input.toLowerCase().equals("n")) {
                dailySummary();
                break;
            } else {
                System.out.println("Please enter either y or n");
            }
        }
    }
    
    /**
     * This method is called with 2 int parameters
     * It returns a int
     * 
     * This method is a form of error checking 
     * The user must enter a int between the lower and upper parameter
     * If they don't it will loop until they enter a valid int
     * It will then return the valid int
    */
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
    
    /**
     * This method is called with 2 string and 1 boolean parameter
     * It returns a boolean
     * 
     * This method checks if a string is valid
     * The first parameter(text) is the string to check is valid
     * The second parameter(checker) is what is the the text parameter is, ie name or address
     * The third parater is if numbers are allowed in the string, ie in address they should be allowed but not in names
     * If the string fails one of the checks, it will return false meaning it is not valid
     * If the string fits all the parameters, it will return true meaning it is valid
    */
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

    /**
     * This method is called with one double parameter
     * It returns a boolean 
     * 
     * This method checks if the passed paramert is a valid double
     * A valid double does not have more than two dp
     * This avoids customers from depositing/withdrawing small amounts of money which aren't worthwhile for the bank
     * If the double has less than two dp it will return true
    */
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
    
    /**
     * This method is called without any parameters
     * 
     * It goes through the process of creating a new account
     * It contains references to other methods to check if account data is valid
     * At the end of the method it will add a new account by creating a new accounts object to the ArrayList 
    */
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
    
    /**
     * This method is called without any parameters
     * 
     * This method will delete an account by removing the object from the ArrayList
     * It asks the user for the account name/number and make sure it is a valid account and does not have multiple accounts under that name
     * At the end of the method it wil remove the account using the ID from above
    */
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
    
    /**
     * This method is called without any parameters
     * 
     * This method will print the balance for an account
     * It starts by asking for the name/number of the account and checks if it is valid and does not have multiple accounts under that name
     * It then prints the balance using that ID
    */
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

    /**
     * This method is called without any parameters
     * 
     * This method will allow the user to deposit to a account
     * It starts by asking for the name/number of the account and checks if it is valid and does not have multiple accounts under that name
     * It then asks how much they would like to deposit, and checks if it is valid for this bank through the DP checker
     * It then deposits to that account using the ID and with the amount entered
    */
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
        keyboard.nextLine();
        askCustomer2();
    }

    /**
     * This method is called without any parameters
     * 
     * This method will allow the user to withdrawl from a account
     * It starts by asking for the name/number of the account and checks if it is valid and does not have multiple accounts under that name
     * It then asks how much they would like to withdrawl, and checks if it is valid for this bank through the DP checker
     * It also checks that it is less that $5000, and checks using another method if the account type is allowed to go into debt.
     * It then withdraws from that account using the ID and with the amount entered
    */
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
        keyboard.nextLine();
        askCustomer2();
    }

    /**
     * This method is called without any parameters
     * 
     * This method is when the program is ready to be quit
     * It will:
        Show how much money the bank is holding
        Show the net deposits/withdrawals for the day
        And call to the CSV class to write to the CSV
     * The total balance held the banks is how much they are holding - debt (the banks money lent to customers)
    */
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
    
    /**
     * This method is called with one String parameter
     * It returns a String 
     * 
     * This method will check if the account name given has multiple accounts
     * Since a customer is able to open a savings and a everyday account, simply entering their name is not enought to differentiate the account
     * If there are multiple accounts they will need enter the account number
     * This will then return Acc ID as a new way to identify accounts
    */
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
                while (true) {
                    accName = keyboard.nextLine().toLowerCase();
                    if (accName.charAt(0) == '0' && (Accounts.returnIndex(accName) >= 0)) { // Since account numbers always start with 0
                        return accName;
                    } else {
                        System.out.println("This is not a account number, try again");
                    }
                }
            } 
        }
        return accName;
    }
    
    /**
     * This method is called with 1 String and 1 Double parameter
     * It returns a Boolean
     * 
     * This method checks if the final balance after the withdrawal will leave the account in debt
     * For Savings and Everyday accounts they cannot go into debt
     * For Current accounts they can go no more than -$1000 in debt
     * If the withdrawing amount is too great, the method will reuturn false saying this cannot occur
     * Otherwise the withdrawal is fine as is approved
    */
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
