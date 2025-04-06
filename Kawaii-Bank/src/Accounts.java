
/**
 * Write a description of class Accounts here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Accounts
{
    private String customerName;
    private String accountType;
    private String customerAddress;
    private String accountNumber;
    private double accountBalance;
    public Accounts(String customerName, String customerAddress, String accountNumber, String accountType, double accountBalance) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    public Accounts(String customerName, String customerAddress, String accountType) { // For creating new acc, 2 less parameters
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.accountType = accountType;
        this.accountBalance = 0;
        this.accountNumber= createAccNum();
    }
    private String createAccNum() {
        int endInt = 0;
        if (accountType.equals("Savings")) {endInt = 0;}
        else if (accountType.equals("Everyday")) {endInt = 1;}
        else if (accountType.equals("Current")) {endInt = 2;}
        int midInt = (int)Math.floor(Math.random()*1000000);
        return("08-0101-"+midInt+"-"+"0"+endInt);
    }
    
    public static int returnIndex(String id) {
        for (int i=0; i<Main.accountsList.size(); i++) {
            if (id.toLowerCase().equals(Main.accountsList.get(i).getName().toLowerCase()) ||
                id.toLowerCase().equals(Main.accountsList.get(i).getAddress().toLowerCase()) ||
                id.toLowerCase().equals(Main.accountsList.get(i).getAccountNumber().toLowerCase())) {
                return i;
            } 
        }
        return 0;
    }
    
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
    public void depositToAccount(double depAmount) {
        this.accountBalance += depAmount;
    }
    public void withdrawFromAccount(double withAmount) {
        this.accountBalance -= withAmount;
    }

    
}
