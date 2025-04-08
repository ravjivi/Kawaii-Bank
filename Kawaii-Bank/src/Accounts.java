package src;

public class Accounts
{
    private String customerName;
    private String accountType;
    private String customerAddress;
    private String accountNumber;
    private double accountBalance;

    /**
     * This method is called 4 String and 1 double parameter
     * 
     * it creates a account object if all account information is known
     * This is when the program is run and it is reading from the CSV
     * The parameters are used to create the object properties
    */
    public Accounts(String customerName, String customerAddress, String accountNumber, String accountType, double accountBalance) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    /**
     * This method is called with 3 String parameters
     * 
     * It creates a account object when there is only some account information
     * This is when a new account is created through the program
    */
    public Accounts(String customerName, String customerAddress, String accountType) { // For creating new acc, 2 less parameters
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.accountType = accountType;
        this.accountBalance = 0;
        this.accountNumber= createAccNum();
    }

    /**
     * This method is called without any parameters
     * It returns a String
     * 
     * It creates a account number that is appropriate for the account
     * It uses the account type to determine the final int of the number
     * The middle interger is a large random number
     * The 08-0101 is unique to the bank so a;; Kawaii-Bank account have this
     * It then returns a account number for a account as a String
    */
    private String createAccNum() {
        int endInt = 0;
        if (this.accountType.equals("Savings")) {endInt = 0;}
        else if (this.accountType.equals("Everyday")) {endInt = 1;}
        else if (this.accountType.equals("Current")) {endInt = 2;}
        int midInt = (int)Math.floor(Math.random()*1000000);
        return("08-0101-"+midInt+"-"+"0"+endInt);
    }

    /**
     * This method is called with 1 String parameter
     * It returns a Int
     * 
     * It uses the inputed parameter to dethermine if an account exsits
     * The input can either be the name, account number, or address
     * It then returns the index of that account in the ArrayList
     * This is useful when you use the ArrayList.get() function but don't know the index
    */
    public static int returnIndex(String id) {
        for (int i=0; i<Main.accountsList.size(); i++) {
            if (id.toLowerCase().equals(Main.accountsList.get(i).getName().toLowerCase()) ||
                id.toLowerCase().equals(Main.accountsList.get(i).getAddress().toLowerCase()) ||
                id.toLowerCase().equals(Main.accountsList.get(i).getAccountNumber().toLowerCase())) {
                return i;
            } 
        }
        return -1;
    }
    
    /**
     * These methods are called without any parameters
     * 
     * They are getters that return their respective value for an object
    */
    public String getName() {
        return(this.customerName);
    }
    public String getAccountType() {
        return(this.accountType);
    }
    public String getAddress() {
        return(this.customerAddress);
    }
    public String getAccountNumber() {
        return(this.accountNumber);
    }
    public double getBalance() {
        return(this.accountBalance);
    }

    /**
     * These method are called with 1 String paratmer
     * 
     * They are setters and are used to change the balance of an account
     * The use the parameter to determine how much to deposit/withdrawl
    */
    public void depositToAccount(double depAmount) {
        this.accountBalance += depAmount;
    }
    public void withdrawFromAccount(double withAmount) {
        this.accountBalance -= withAmount;
    }
}
