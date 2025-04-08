package src;

import java.io.*;
import java.util.Scanner;
public class CSV
{
    private static final File MYCSV = new File("bankData.csv"); // Using bankData.csv file
    
    /**
     * This method is called without any parameters
     * 
     * It is called when the program starts to read the initial CSV file using Scanner
     * It takes each line of the CSV and splits the string by the commas
     * It takes each part of the String and creates an accounts object 
    */
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

    /**
     * This method is called without any parameters
     * 
     * It is called an the end of the program to write all the data stored in the ArrayList to the CSV
     * It uses a StringBuilder to build up a long String to write to the CSV using FileWriter
     * Each smaller String is seperated by a comma before being added to CSV
     * It uses a for loop make sure it writes every object in the ArrayList
    */
    public static void writeCSV() {
        try {
            FileWriter myWriter = new FileWriter(MYCSV);
            StringBuilder tempAccountData =new StringBuilder();
            for (int i=0; i<Main.accountsList.size(); i++) {
                tempAccountData.append(Main.accountsList.get(i).getName()+",");
                tempAccountData.append(Main.accountsList.get(i).getAddress()+",");
                tempAccountData.append(Main.accountsList.get(i).getAccountNumber()+",");
                tempAccountData.append(Main.accountsList.get(i).getAccountType()+",");
                tempAccountData.append(Main.accountsList.get(i).getBalance());
                if (i<Main.accountsList.size()-1) { // Dont break line for last line
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
