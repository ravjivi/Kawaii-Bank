
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
    public static void readCSV() {
        File myCSV = new File("bankData.csv"); // Using bankData.csv file
        try{
            Scanner myReader = new Scanner(myCSV);
            while(myReader.hasNextLine()) { // Repeat for every line in csv
                String line = myReader.nextLine();
                String[] tempLine = line.split(",");
                Main.accountsList.add(new Accounts(tempLine[0], tempLine[1], tempLine[2], tempLine[3], Double.parseDouble(tempLine[4]))); 
            }
        } catch(IOException e) {
            System.out.println("Error: "+e);
        }
    }
    
}
