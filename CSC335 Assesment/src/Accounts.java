
/**
 * Write a description of class Accounts here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.ArrayList;
public class Accounts
{
    private String customerName;
    private String accountType;
    private String customerAddress;
    private String accountNumber;
    private double accountBalance;
    public static ArrayList<Accounts> accountsList = new ArrayList<Accounts>(); // Public so it can be referred in any class
    public Accounts(String customerName, String accountType, String customerAddress, String accountNumber, double accountBalance) {
        this.customerName = customerName;
        this.accountType = accountType;
        this.customerAddress = customerAddress;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }

    public Accounts(String customerName, String accountType, String customerAddress) { // For creating new acc, 2 less parameters
        this.customerName = customerName;
        this.accountType = accountType;
        this.customerAddress = customerAddress;
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
}
