
/**
 * Write a description of class Book here.
 *
 *Book class creates books and manages the inventory when they are referenced in the other classes 
 *methods find the ID of certain books and increase/decrease copies 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */
import java.util.ArrayList; 
import java.util.Date; 

public class Book
{
    //private instance variables of type String to hold the title and author of the book 
    private String strTitle, strAuthor;  
    //private instance variable of type short to hold the ID of the book 
    private short shrBookID;

    //private instance variable to store the number of copies 
    private byte bytNumCopies; 

    //private instance variavle of type date that will be initialized later on when the user checks out a book 
    private Date dueDate;

    //private static variable that will generate a unique ID for each user 
    private static short shrNextID = 1; 

    //private static ArrayList to store all the existing books in the library 
    private static ArrayList<Book> bookList = new ArrayList<Book>();

    //this is a constructor to initialize the instance variables for when a new book object is created 
    public Book (String t, String a, byte c)

    {
        this.strTitle = t;     
        this.strAuthor = a; 
        this.bytNumCopies = c; 

        //creating a new bookID for the book object by incrementind shrNextID by 1 
        this.shrBookID = shrNextID++; 

        //adding the newly created book object to the static array list of all existing books 
        bookList.add(this); 
    }

    //findBookByID method takes in bookID as a parameter and searches for a book with that ID 
    //if the ID is found, then the book object is returned, and if it is not found, then null is returned
    //uses binary search as the data is sorted already by bookID
    public static Book findBookByID(short bookID) 
    {
        //getting the book list 
        ArrayList<Book> bookList = Book.getBookList();

        short shrLow = 0;
        short shrHigh = (short) (bookList.size() - 1);
        short shrMid; 
        while (shrLow <= shrHigh) {
            shrMid = (short) ((shrLow + shrHigh) / 2);
            Book midBook = bookList.get(shrMid);

            if (midBook.getBookID() == bookID) {
                // Returns the book if found
                return midBook;
            } else if (midBook.getBookID() < bookID) {
                shrLow = (short) (shrMid + 1);
            } else {
                shrHigh = (short) (shrMid - 1);
            }
        }
        
        
        // Returns null if the book ID does not exist
        return null;
    }

    //method increaseNumCopies increases the number of copies of a book by 1 
    // it is used in the UserProfile class when a book is returned using the returnSingleBook method 
    //this way when a book is returned, the copies of that book now available increases by 1
    public void increaseNumCopies() 
    {
        this.bytNumCopies++;
    }

    //method decreaseNumCopies decreases the number of copies of that book available by 1 when a book is checked out 
    //used in the bookCheckout method when the user checks out a copy of a book 
    public void decreaseNumCopies() 
    {
        if (this.bytNumCopies > 0) 
        {
            this.bytNumCopies--;
        }
    }

    //method removeBookifNoCopies gets passed in a bookID and checks to see if that book is still available in the library 
    //if the book is not available , it is removed from the bookList 
    public static void removeBookIfNoCopies(short bookID)
    {
        //calling the findBooksByID method to search for the book 
        Book bookToRemove = findBookByID(bookID);

        if (bookToRemove != null && bookToRemove.getNumCopies() == 0) 
        {
            bookList.remove(bookToRemove);
        }
    }

    //getter to return strTitle
    public String getTitle()
    {
        return this.strTitle; 
    }

    //getter to return strAuthor
    public String getAuthor()

    {
        return this.strAuthor; 
    }

    //getter to return shrBookID
    public short getBookID ()

    {
        return this.shrBookID; 
    }

    //getter to return bytNumCopies 
    public byte getNumCopies ()

    {
        return this.bytNumCopies; 
    }

    //static getter to return the bookList 
    public static ArrayList<Book> getBookList()

    {
        return bookList; 
    }

    //getter to return the dueDate 
    public Date getDueDate()

    {
        return this.dueDate; 
    }

    //setter to modify strTitle
    public String setTitle(String t)
    {
        return this.strTitle; 
    }

    //setter to modify strAuthor
    public void setAuthor (String a)

    {
        this.strAuthor = a; 
    }

    //setter to modify shrBookID
    public void setBookID (short i)

    {
        this.shrBookID = i; 
    }

    //setter to modify bytNumCopies 
    public void setNumCopies (byte n)

    {
        this.bytNumCopies = n;  
    }

    //setter to modify dueDate
    public void setDueDate (Date d)

    {
        this.dueDate = d; 
    }

    //toString
    //this is used in my code to print the information about my book so the user knows stuff about if, in case they want to checkout 
    public String toString() 
    {
        return "Book ID: " + shrBookID + ", Title: " + strTitle + ", Author: " + strAuthor + ", Number of Copies: " + bytNumCopies;
    }
}
