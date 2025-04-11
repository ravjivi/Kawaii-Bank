package src;

/**
 * Contains most of the code
 *
 * @Viraaj Ravji
 * 24.03.25
 */

/*LIBRARY*/
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
public class Main
{
    /*CLASS VARIABLES*/
    public static ArrayList<Accounts> accountsList = new ArrayList<Accounts>(); // Public so it can be referred in any class
    private static Scanner keyboard = new Scanner(System.in); // Scanner to read user inputs
    private static DecimalFormat df = new DecimalFormat("0.00"); // Formats the output to 2dp
    private static double netDepWith; // Net Deposits and withdrawals

    /*CONSTANTS*/
    // Can be changed by bankteller for the banks liking
    private static final int OVERDRAFTLIMIT = -1000;
    private static final int DEBTLIMMIT = 0;
    private static final String[] ACCOUNTTYPES = {"Everyday", "Savings", "Current"}; // More account types can be added by adding a new index


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
                            "5. Withdraw from an account\n6. Print all accounts\n7. Quit");
        int input = intChecker(1,7); // Uses scanner to read input and checks it is valid

        switch(input) { // Determines result based of input
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
        case 6: // Print accounts
            printAccounts();
            break;
        case 7: // Quit
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
        while (true) { // Creates a infinite loop until broken
            String input = keyboard.nextLine();
            if (input.toLowerCase().equals("y") || input.toLowerCase().equals("yes")) { // If input is yes
                askCustomer(); // Ask the user again
                break; // End the loop
            } else if (input.toLowerCase().equals("n") || input.toLowerCase().equals("no")) { // If input is no
                dailySummary(); // Begins closing the brogram
                break;
            } else { // User did not enter yes or no
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
        while (true) { // Creates a infinite loop until broken
            while(!keyboard.hasNextInt())  { // If the user did not input a integer
                keyboard.nextLine();
                System.out.println("Please enter a number");
            } 
            input = keyboard.nextInt(); 
            if (input >= lower && input <= upper) { // Checks the user entered a integer between the 2 set parameters
                break;
            } else { // The integer is outside the defined range
                System.out.println("Please enter a number between "+lower+" and "+upper);
            }
        }
        return input; // Returns the valid integer
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
        if (text.isBlank()) { // If the text is just blank
            System.out.println(checker+" cannot be blank");
            return false;
        } else if (text.contains(",") || text.contains(".") || text.contains("/") && !numAllowed || text.contains(";") || text.contains("'")
                || text.contains("[") || text.contains("]") || text.contains("=") || text.contains("<") || text.contains("~")
                || text.contains(">") || text.contains("?") || text.contains(":") || text.contains("{") || text.contains("}")
                || text.contains("|") || text.contains("_") || text.contains("+") || text.contains("(") || text.contains(")")
                || text.contains("!") || text.contains("@") || text.contains("#") || text.contains("$") || text.contains("%")
                || text.contains("^") || text.contains("&") || text.contains("*") || text.contains("`")) { // If the text contains special characters
                    System.out.println(checker+" cannot contain special characters");
                    return false; // Does not meet the string requirements
        } else if (text.length() > 70 && !numAllowed) { // If text is smaller bigger than legal name requirements (for names since numAllowed is false)
            System.out.println("Name is too long and does not obey a NZ legal name\nIt cannot be greater than 70 characters");
            return false;
        }
        for (int i=0; i<text.length(); i++) { // Looping through every character in the string
            if (Character.isDigit(text.charAt(i)) && !numAllowed) { // Checks if the int contains a digit when it should not
                System.out.println(checker+" cannot contain integers");
                return false;
            }
        }
        return true; // Return true to show the string must be valid
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
        String numberLine = Double.toString(number); // Convers the double into a string so it can be split into characters(digits)
        for (int i=0; i<numberLine.length(); i++) { // Loop through every digit
            if (numberLine.charAt(i) == '.') {  // If the double contains a decimal point
                dp = numberLine.length() - (i+1); // Sets dp to the number of digits after the decimal point
            }
        }
        if (dp > 2) { // If there are more than 2 digits after the dp
            return false;
        } else { // If there are 2 or less
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
        /* Define local variables for this method */
        String name;
        String accountType = "Everday";
        String address;
        String city;
        keyboard.nextLine();
        System.out.println("What is your full name");
        name = keyboard.nextLine(); // Name of the account is what the user inputed
        while (!stringChecker(name, "Name", false)) { // Checks it is valid for a name
            name = keyboard.nextLine(); // Enter another name
        }
        
        System.out.println("What kind of account would you like to open, 1-"+ACCOUNTTYPES.length);
        for (int i=0; i< ACCOUNTTYPES.length; i++) {
            System.out.println((i+1)+". "+ACCOUNTTYPES[i]);
        }
        int temp = intChecker(1,ACCOUNTTYPES.length); // Checks int is between 1-3
        // Defines the account type based on what user chose
        accountType = ACCOUNTTYPES[temp-1];

        // Checking for multiple accounts that will have the same name and account type
        while (Accounts.returnIndex(name) > 0) { // While an account already exists under the new name
            while (accountsList.get(Accounts.returnIndex(name)).getAccountType().equals(accountType)) { // While the account type of the old account is the same as the new account
                System.out.println("You cannot create another account with the same account type under the same name");
                temp = intChecker(1,ACCOUNTTYPES.length); // Pick new account type
                accountType = ACCOUNTTYPES[temp-1]; 
            } 
            break; // Otherwise they are different so 2 accounts are allowed
        }
        
        keyboard.nextLine();
        System.out.println("What is your address. Enter your city in the next line");
        address = keyboard.nextLine();
        while (!stringChecker(address, "Address", true)) { // Checks address is valid, also numbers are allowed
            address = keyboard.nextLine();
        }
        System.out.println("What is your city"); // Asks for city seperately
        city = keyboard.nextLine();
        while (!stringChecker(city, "City", false)) { // Checks city is valid 
            city = keyboard.nextLine();
        }

        System.out.println("Thank you for opening an account with us "+name);
        accountsList.add(new Accounts(name, address+" "+city, accountType)); //Creates a new account object with 3 given parameters
        askCustomer2(); // Will check if the user wants futher assistance
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
        String accID = checkMultipleAccounts(keyboard.nextLine()); // Checks there aren't multiple accounts for that name
        while (!(Accounts.returnIndex(accID) >= 0)) { // While the account does not exist
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = checkMultipleAccounts(keyboard.nextLine()); //Lets the user re-enter their account and checks there aren't mulitple
        }
        System.out.println("This account is now closed");
        accountsList.remove(Accounts.returnIndex(accID)); //Removes the account from the ArrayList. It is removed from the CSV at the end of the program
        askCustomer2(); // Will check if the user wants futher assistance
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
        String accID = checkMultipleAccounts(keyboard.nextLine()); // Checks if the user has multiple accounts 
        while (!(Accounts.returnIndex(accID) >= 0)) { // While the account does not exit
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = checkMultipleAccounts(keyboard.nextLine()); // Lets the user re-enter their account and checks there aren't mulitple
        }
        System.out.println("Balance: $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance())); // Prints the balance formatting it to 2dp
        askCustomer2(); // Will check if the user wants futher assistance
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
        String accID = checkMultipleAccounts(keyboard.nextLine()); // Checks if the user has multiple accounts 
        while (!(Accounts.returnIndex(accID) >= 0)) { // While the account does not exist
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = keyboard.nextLine();
        }
        System.out.println("How much would you like to deposit. You currently have $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance())); // Shows their balance
        Double depAmount = 0.0;
        while (true) { // Infinite loop
            if (keyboard.hasNextDouble()) { // It the input is a double
                depAmount = keyboard.nextDouble();
                if (doubleDPChecker(depAmount)) { // If the deposit amount is valid
                    if (depAmount > 0) { // Checks you are depositing at least $0.01
                        break; // Break the loop because deposit is valid
                    } else { // Deposit amount is negative or 0
                        System.out.println("You must deposit at least $0.01");
                    }
                    
                } else { // The deposit amount is not valid
                    System.out.println("We only accept money with 2 decimal places or less");
                }
            } else { // The input is not a doubble
                System.out.println("Please enter a integer/double");
                keyboard.nextLine();  
            }
            // Repeats the loop
        }

        accountsList.get(Accounts.returnIndex(accID)).depositToAccount(depAmount); // Make the deposit with the method in the Accounts class
        System.out.println("New balance: $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance())); // Print the new balance
        netDepWith += depAmount; // Update the net transactions
        keyboard.nextLine();
        askCustomer2(); // Will check if the user wants futher assistance
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
        String accID = checkMultipleAccounts(keyboard.nextLine()); // Checks if the user has multiple accounts 
        while (!(Accounts.returnIndex(accID) >= 0)) { // While the account does not exist
            System.out.println("This account does not exist, Please re-enter the name of the account");
            accID = keyboard.nextLine();
        }
        System.out.println("How much would you like to withdraw. You currently have $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance())); // Prints the balance 
        Double withAmount = 0.0;
        while(true) { // Infinite loop
            if (keyboard.hasNextDouble()) { // If input is a double
                withAmount = keyboard.nextDouble();
                if (withAmount <= 5000) { // If the withdrawal amount is smaller than the allowed $5000
                    if (checkDebt(accID, withAmount)) { // If the amount withdrawn wont put the account in debt
                        if (doubleDPChecker(withAmount)) { // If double is valid for money
                            break; // Withdrawl amount is valid so break loop
                        } else { // Dobule is not valid for money
                            System.out.println("We only withdraw money with 2 decimal places or less");
                        }
                    } else { // The amount withdrawn with put the account in debt
                        System.out.println("You cannot withdraw this much");
                    }
                } else { // Withdrawal is greater than $5000
                    System.out.println("You cannot withdraw more than $5000");
                }
            }
        }
        accountsList.get(Accounts.returnIndex(accID)).withdrawFromAccount(withAmount); // Make the deposit with withdrawal in the Accounts class
        System.out.println("New balance: $"+df.format(accountsList.get(Accounts.returnIndex(accID)).getBalance())); // Print the new balance
        netDepWith -= withAmount; // Update the net transactions
        keyboard.nextLine();
        askCustomer2(); // Will check if the user wants futher assistance
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
        for (int i=0; i<accountsList.size(); i++) { // For every account in the bank
            balTotal += accountsList.get(i).getBalance(); // Add the balances to the total
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
     * There can only be 1 unique account number
    */
    private static String checkMultipleAccounts(String accName) {
        if (Character.isLetter(accName.charAt(0))) { // If the first character is a letter (means it is an account name not a account number)
            int x=0;
            for (int i=0; i<Main.accountsList.size(); i++) { // Check through every account
                if (accName.toLowerCase().equals(Main.accountsList.get(i).getName().toLowerCase())) { // If there are 2 account with the same account name
                    x++;
                } 
            }  
            if (x>1) { // If there is more than 1 account listed under this name
                System.out.println("You have multiple accounts under this name, you will need to enter the account number");
                while (true) {
                    accName = keyboard.nextLine().toLowerCase();
                    if (accName.charAt(0) == '0' && (Accounts.returnIndex(accName) >= 0)) { // Since account numbers always start with 0
                        return accName; // This is the new account number to identify the account
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
        if ((accountsList.get(Accounts.returnIndex(accID)).getBalance()) - withAmount < DEBTLIMMIT &&  accountsList.get(Accounts.returnIndex(accID)).getAccountType().equals("Savings")
            || (accountsList.get(Accounts.returnIndex(accID)).getBalance()) - withAmount < DEBTLIMMIT &&  accountsList.get(Accounts.returnIndex(accID)).getAccountType().equals("Everyday")
            || (accountsList.get(Accounts.returnIndex(accID)).getBalance()) - withAmount < OVERDRAFTLIMIT &&  accountsList.get(Accounts.returnIndex(accID)).getAccountType().equals("Current")) { 
            // If the amount going to be withdrawn will make the balance negative for everyday or savings
            // Or if the amount withdrawn from a current account will go below a $1000 overdraft
            return false;
        } else {
            return true; // The withdrawal is fine
        }
    }

    /**
     * This method is called without any parameters
     * 
     * This method will print a liitle spreadsheet of call the accounts
     * This may make it easier for the bankteller to read information rather than use the CSV
     * It uses variables so it is very flexible depending on the lengths of some of the variables
     * The last 3 are constant variables because they cannot exceed that length for any account
    */
    private static void printAccounts() {
        // Method variables
        int longestName = 0;
        int longestAdd = 0;
        int longestBal = 0;
        // Constant because it is the same for every account
        final int ACCNUMWIDTH = 15; 
        final int ACCTYPEWIDTH = 8;
        for (int x=0; x<Main.accountsList.size(); x++) { // For every account
            if (accountsList.get(x).getName().length() > longestName) { // If this account name is longer than the previos longest
                longestName = accountsList.get(x).getName().length(); // Make this the longest so far
            } 
            if (accountsList.get(x).getAddress().length() > longestAdd) { // If this address is longer than the previos longest
                longestAdd = accountsList.get(x).getAddress().length(); // Make this the longest so far
            }
            String numberLine = Double.toString(accountsList.get(x).getBalance()); // Convert the double to a temporary string so I can get length
            if (numberLine.length() > longestBal) { // If this balance is longer than the previos longest
                longestBal = numberLine.length(); // Make this the longest so far
                if (accountsList.get(x).getBalance() < 0) { // If balance is negative
                    longestBal += 1; // Because the negative adds a extra character
                } 
            }
        }
        /* Header */
        String format = String.format("| %%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds |", // ds means the string input is dependant on the digit(int) given
        longestName, longestAdd, ACCNUMWIDTH, ACCTYPEWIDTH, longestBal); // Format template for the string. The 5 variables are the character spacing between '|'
        for (int x=0; x<(longestName+longestAdd+longestBal+ACCNUMWIDTH+ACCTYPEWIDTH+(3*5)+(2*2)); x++) { // Since there are 5 3 character spacers and 2 end 2 character spaces that need to accouted for 
            System.out.print("-"); 
        }
        System.out.println(); // Move down to next line

        /* Rows */
        for (int i=0; i<Main.accountsList.size(); i++) {
            System.out.println(String.format(format, accountsList.get(i).getName(), accountsList.get(i).getAddress(),
                accountsList.get(i).getAccountNumber(), accountsList.get(i).getAccountType(), "$"+accountsList.get(i).getBalance())); // Using the same format as above
            for (int x=0; x<(longestName+longestAdd+longestBal+ACCNUMWIDTH+ACCTYPEWIDTH+(3*5)+(2*2)); x++) { 
                System.out.print("-");
            }
            System.out.println();
        }
        keyboard.nextLine(); // Clear int left in scanner from before
        askCustomer2(); // Asks if they need any more assistance
    }
}