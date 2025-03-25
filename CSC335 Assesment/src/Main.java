/**
 * Write a description of class Main here.
 *
 * @Viraaj Ravji
 * 24.03.25
 */
import java.util.Scanner;
public class Main
{
    private static Scanner keyboard = new Scanner(System.in);
    public static void main(String[] args) {
        CSV.readCSV(); // Read the CSV file and store to the ArrayList
        System.out.println("Hello, welcome to Kawaii-Bank. How can I help you? 1-5");
        System.out.println("1. Create an account for a new customer\n2. Close an account for an existing customer\n"+
                            "3. Get the balance of a customers's account\n4. Deposit into an account\n5. Withdraw from an account");
        int input = intChecker(1,5);
        
        switch(input) {
            case 1: // Create acc
                createAccount();
                break;
            case 2: // Close acc
                break;
            case 3: // Get balance
                break;
            case 4: // Deposit
                break;
            case 5: // Withdraw
                break;
            default:
                System.out.println("Error with input");
                input = intChecker(1,5);
        }
        
        
        //System.out.println(Accounts.accountsList.get(2).getName());

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
                return(input);
            } else {
                System.out.println("Please enter a number between "+lower+" and "+upper);
            }
        }
        return (0);
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
        while(!keyboard.hasNextLine())  {
                keyboard.nextLine();
                System.out.println("Please enter a String");
        } 
        address = keyboard.nextLine();
        Accounts.accountsList.add(new Accounts(name, accountType, address));
        
    }
    
}
