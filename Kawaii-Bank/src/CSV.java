package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            Scanner myReader = new Scanner(MYCSV); // Define the scanner to read CSV I defined above
            while(myReader.hasNextLine()) { // Repeat for every line in csv
                String line = myReader.nextLine(); // Read the entire line
                String[] tempLine = line.split(","); // Split the line by the commas
                // Use parts of the line as parameters
                Main.accountsList.add(new Accounts(tempLine[0], tempLine[1], tempLine[2], tempLine[3], Double.parseDouble(tempLine[4]))); 
            }
        } catch(IOException e) { // The scanner cannot read the file
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
            FileWriter myWriter = new FileWriter(MYCSV); // Define the writer to write to the CSV
            StringBuilder tempAccountData =new StringBuilder(); // New StringBuilder to put together strings
            for (int i=0; i<Main.accountsList.size(); i++) { // For every object in ArrayList
                // Add together variables of object
                tempAccountData.append(Main.accountsList.get(i).getName()+",");
                tempAccountData.append(Main.accountsList.get(i).getAddress()+",");
                tempAccountData.append(Main.accountsList.get(i).getAccountNumber()+",");
                tempAccountData.append(Main.accountsList.get(i).getAccountType()+",");
                tempAccountData.append(Main.accountsList.get(i).getBalance());
                if (i<Main.accountsList.size()-1) { // Dont break line for last line
                    tempAccountData.append("\n");
                }
                myWriter.write(tempAccountData.toString()); // Write the long string on CSV
                tempAccountData.setLength(0); // Reset the StringBuilder
            }
            // Close the writer
            myWriter.flush();
            myWriter.close();

        } catch (IOException e) { // If the writer cannot write
            System.out.println("Error: "+e);
        }
    }
}
