
/**
 * Write a description of class CSV here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.Scanner;
public class CSV
{
    private static final File MYCSV = new File("bankData.csv"); // Using bankData.csv file
    public static void readCSV() {
        
        try{
            Scanner myReader = new Scanner(MYCSV);
            while(myReader.hasNextLine()) { // Repeat for every line in csv
                String line = myReader.nextLine();
                String[] tempLine = line.split(",");
                Main.accountsList.add(new Accounts(tempLine[0], tempLine[1], tempLine[2], tempLine[3], Double.parseDouble(tempLine[4]))); 
                //myReader.close();
            }
        } catch(IOException e) {
            System.out.println("Error: "+e);
        }
    }

    public static void writeCSV() {
        try {
            FileWriter myWriter = new FileWriter(MYCSV);
            StringBuilder tempAccountData =new StringBuilder();
            for (int i=0; i<Main.accountsList.size(); i++) {
                tempAccountData.append(Main.accountsList.get(i).getName());
                tempAccountData.append(",");
                tempAccountData.append(Main.accountsList.get(i).getAddress());
                tempAccountData.append(",");
                tempAccountData.append(Main.accountsList.get(i).getAccountNumber());
                tempAccountData.append(",");
                tempAccountData.append(Main.accountsList.get(i).getAccountType());
                tempAccountData.append(",");
                tempAccountData.append(Main.accountsList.get(i).getBalance());
                if (i<Main.accountsList.size()-1) {
                    tempAccountData.append("\n");
                }
                myWriter.write(tempAccountData.toString());
                tempAccountData.setLength(0);
            }
            myWriter.flush();
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Error: "+e);
        }
    }
    
}
