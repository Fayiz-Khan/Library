/**
 * Write a description of class Receipt here.
 * 
 * This class generates a reciept based on the books the user checks out
 * reciept is printed to a text file 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckoutRecord 
{
    // Make bytCounter static to be shared among all instances of the class
    private static byte bytCounter = 1;
    
    //string to store file name 
    String strFileName; 

    public void generateReceipt(UserProfile user, Date dueDate)
    {
        //setting file name 
        strFileName = user.getFirstName() + "_" + user.getLastName() + "_" + "receipt" + bytCounter++ + ".txt";

        try (PrintWriter writer = new PrintWriter(strFileName)) 
        {
            //Write user information to the file
            writer.println("Receipt for: " + user.getFirstName() + " " + user.getLastName());
            writer.println("Library ID: " + user.getID());

            //Write checked-out books information to the file
            writer.println("\nChecked-out Books:");

            for (Book book : user.getCheckedOutBooks()) 
            {
                writer.println(book.getTitle() + " by " + book.getAuthor());
            }

            //Calculate and write due date to the file
            writeDueDate(writer, dueDate);

            // Closing the writer 
            writer.close(); 

            System.out.println("Receipt generated successfully. File name: " + strFileName);
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error: Cannot open file for writing");
        } 
        catch (IOException e) 
        {
            System.out.println("Error: Cannot write to file");
        }
    }

    //helper method to write the due date 
    private static void writeDueDate(PrintWriter writer, Date dueDate) 
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        writer.println("\nDue Date: " + dateFormat.format(dueDate));
    }

    //calculates current date 
    public static Date calculateDueDate(Date currentDate) {
        long dueDateMillis = currentDate.getTime() + (14 * 24 * 60 * 60 * 1000); // 2 weeks in milliseconds
        return new Date(dueDateMillis);
    }
}
