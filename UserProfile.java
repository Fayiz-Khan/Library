
/**
 * Write a description of class UserProfile here.
 *
 *User end of the program. Once they have successfully logged in or created an account, they can checkout books, return them, and pay their 
 *late fees if necessary 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */
import java.util.Scanner; 
import java.util.ArrayList; 
import java.util.Date; 

public class UserProfile
{
    //private instance variables of type String to store the first name, last name, and password
    private String strFirstName, strLastName, strPassword;
    //private instance variables of type short to store library ID and late fees 
    private short shrLibraryID;
    private float fltFeesOwed;
    //private array list to store the books the user has checked out 
    private ArrayList<Book> checkedOutBooks;  
    //private static array list to store all the userProgfiles 
    private static ArrayList<UserProfile> userProfiles = new ArrayList<>();
    //private static variables used to generate unique user IDs 
    private static short shrNextID = 1;
    //boolean that checks whether the user is logged in or not 
    boolean bolLogin; 

    //constructor to initialize the instance variables 
    public UserProfile(String f, String l, String p) 
    {
        this.strFirstName = f;
        this.strLastName = l;
        this.strPassword = p;
        this.fltFeesOwed = 0f;
        this.bolLogin = false; 

        //generating unique id by incrementing by 1 each time
        this.shrLibraryID = shrNextID++;
        this.checkedOutBooks = new ArrayList<>(); 

        //adding this object to the userProfiles array list 
        userProfiles.add(this);
    }

    //method createUserProfile allows the user to create an account and then uses the inputted credentials, to create a new UserProfileObject
    public static UserProfile createUserProfile() {
        System.out.print("Enter your first name: ");
        String strFirstName = new Scanner(System.in).nextLine().trim();

        System.out.print("Enter your last name: ");
        String strLastName = new Scanner(System.in).nextLine().trim();

        System.out.print("Enter your password: ");
        String strPassword = new Scanner(System.in).nextLine();

        // Create and return a new UserProfile object
        return new UserProfile(strFirstName, strLastName, strPassword);
    }

    //method bookCheckout is used by the user to checkout books from the library. 
    //it ensures you can't get 2 of the same book and that you don't have any late fees to be able to checkout
    //allows user to checkout as many books as they want 
    public void bookCheckout() 
    {
        //Array list of type book with a list of all available books 
        //calls getBookList to access that information 
        ArrayList<Book> bookList = Book.getBookList();
        //boolean bolCheckout allows them to checkout as many books as they want whilst the condition is true
        boolean bolCheckout = true; 
        //shrBookIDCheckout takes input to detect what book the user wishes to check out 
        short shrBookIDCheckout; 

        //this will store the book (object) that the user wants to check out 
        Book bookToCheckout; 

        //exits the method if they have late fees that are unpayed
        if (this.fltFeesOwed > 0) 
        {
            System.out.println("You have late fees. Please pay your fees before checking out books.");
            return;
        }

        //exits the method if the list of books is empty 
        if (bookList.isEmpty()) {
            System.out.println("No books available for checkout.");
            return;  // Exit the method if the list is empty
        }

        //printing the available books in a for each loop  
        System.out.println("Available Books:");

        for (Book book : bookList) {
            System.out.println(book);
        }

        //continue checking out books until the user decides to stop
        do  
        {
            System.out.println("Enter the Book ID to checkout (0 to stop): ");
            //taking in input for shrBookIDInput
            shrBookIDCheckout = Input.shrBookIDInput();

            //check if the user wants to stop checking out books
            if (shrBookIDCheckout == 0)
            {
                bolCheckout = false;
                break; 
            }

            //calls the findBooksByID method to return the book object or null depending on if the book exists 
            bookToCheckout = Book.findBookByID(shrBookIDCheckout);

            //Check if the book is available
            if (bookToCheckout != null && bookToCheckout.getNumCopies() > 0) 
            {
                //Check if the user has already checked out a copy of the same book
                if (checkedOutBooks.contains(bookToCheckout)) {
                    System.out.println("You've already checked out a copy of this book.");
                } 

                else 

                {
                    //checking-out the book goes through 
                    //decreases inventory by 1 
                    bookToCheckout.decreaseNumCopies();
                    //adds the book object to the user's arraylist 
                    checkedOutBooks.add(bookToCheckout); 

                    System.out.println("Book successfully checked out.");

                    //Remove the book from the list if there are 0 copies
                    Book.removeBookIfNoCopies(shrBookIDCheckout);

                    //sets the dueDate 
                    Date currentDate = new Date();
                    bookToCheckout.setDueDate(CheckoutRecord.calculateDueDate(currentDate));

                    //Caclulate and generate receipt after checkout
                    generateReceiptAfterCheckout(bookToCheckout.getDueDate());
                }
            } else 
            {
                System.out.println("Book not available or invalid Book ID. Please try again: ");
            }
        }while(bolCheckout); //runs while bolCheckout is true 
    }

    //method returnBook is used for the user to return a book they have previously checked out. They can choose to return as many books as they want
    //after the book has been returned, inventory is then updated as a new book can now be checked out by others 
    public void returnBook() {
        // Flag to control the loop
        boolean bolReturnBooks = true;
        // Variable to store the Book ID to return
        short shrIDToReturn;

        // Loop to allow the user to return books
        do {
            // Check if the user has any books checked out
            if (checkedOutBooks.isEmpty()) {
                System.out.println("No books checked out.");
                return;
            }

            // Display the books currently checked out
            System.out.println("Books Checked Out:");
            for (Book checkedOutBook : checkedOutBooks) {
                System.out.println("Book ID: " + checkedOutBook.getBookID() + ", Title: " + checkedOutBook.getTitle() + ", Author: " + checkedOutBook.getAuthor());
            }

            // Prompt the user to enter the Book ID to return
            System.out.println("Enter the Book ID to return (or enter 0 to stop returning books): ");
            shrIDToReturn = Input.shrBookIDInput();

            // Check if the user wants to stop returning books
            if (shrIDToReturn == 0) {
                bolReturnBooks = false;
            } else {
                boolean bookFound = false;

                // Check if the entered Book ID matches one of the user's checked-out books
                for (Book checkedOutBook : checkedOutBooks) {
                    if (checkedOutBook.getBookID() == shrIDToReturn) {
                        bookFound = true;
                        // Perform the book return
                        returnSingleBook(shrIDToReturn);
                        System.out.println("Book returned successfully.");
                        break;
                    }
                }

                // Inform the user if the entered Book ID is not among their checked-out books
                if (!bookFound) {
                    System.out.println("You don't currently have the book with ID " + shrIDToReturn + " checked out.");
                }
            }
        } while (bolReturnBooks);  // Continue the loop as long as bolReturnBooks is true
    }

    //returnSingleBook is a helped method that gets passed an ID of a book 
    //the book that the user has checked out with that corresponding ID is then returned 
    private void returnSingleBook(short shrIDToReturn) {
        // Find the Book object with the specified ID in the checkedOutBooks list
        Book bookToReturn = null;
        for (Book checkedOutBook : checkedOutBooks) {
            if (checkedOutBook.getBookID() == shrIDToReturn) {
                bookToReturn = checkedOutBook;
                break;
            }
        }

        // Check if the book was found in the checkedOutBooks list
        if (bookToReturn != null) {
            // Short to hold the number of days the book was possibly returned late
            short shrDaysLate;
            // Float to calculate the potential late fee
            float fltLateFee;

            // Increase the number of copies available of that book
            bookToReturn.increaseNumCopies();
            // Remove the returned book from the user's list
            checkedOutBooks.remove(bookToReturn);

            // Check if dueDate is not null before accessing it just in case
            if (bookToReturn.getDueDate() != null) {
                // Calculate late fees if the book is returned after the due date
                Date currentDate = new Date();
                if (currentDate.after(bookToReturn.getDueDate())) {
                    // Calculating the amount of days late
                    shrDaysLate = (short) ((currentDate.getTime() - bookToReturn.getDueDate().getTime()) / (24 * 60 * 60 * 1000));
                    // Charging $0.25 per day late
                    fltLateFee = shrDaysLate * 0.25f;
                    this.fltFeesOwed += fltLateFee;
                    System.out.printf("Late fee charged: $%.2f", fltLateFee);
                }
            }

            System.out.println("Book successfully returned.");
        } else {
            System.out.println("Book with ID " + shrIDToReturn + " not found in the checked-out books list.");
        }
    }

    //payLateFees is a method that allows the user to pay off their existing debts to the library 
    public void payLateFees() 
    {
        //variable of type float to hold how much the user pays 
        float fltPayment; 

        //checks first to see if the user has late fees to pay 
        if (fltFeesOwed <= 0)
        {
            System.out.println("You don't have any late fees to pay.");
            return;
        }

        System.out.printf("Late Fees Owed: $%.2f", fltFeesOwed);
        //taking in user input 
        fltPayment = Input.lateFeesInput(); 

        //if payed in full, fltFeesOwed resets to 0
        if (fltPayment == fltFeesOwed) 
        {
            System.out.println("Thank you for your payment. Late fees fully paid.");
            //Reset late fees after payment
            fltFeesOwed = 0;  

        } 

        //amount payed is subtracted from their debts 
        else if (fltPayment < fltFeesOwed)

        {
            fltFeesOwed -= fltPayment;
            System.out.printf("Thank you for your payment. Remaining late fees: $%.2f", fltFeesOwed);
        }

        //money back if they overpaid 
        else 

        {
            System.out.printf("You paid too much! You have been refunded: $%.2f", fltPayment - fltFeesOwed);
        }
    }

    //private method used to generate a receipt after the checkout in the bookCheckout method
    //calls the methods in the CheckOutRecord Class 
    private void generateReceiptAfterCheckout(Date dueDate) 
    {
        CheckoutRecord checkoutRecord = new CheckoutRecord();
        checkoutRecord.generateReceipt(this, dueDate);
    }

    //userLogin method is used for the user to login into the program provided they enter the proper credentials 
    //programs runs until they enter valid credentials or if they choose to exit 
    public static UserProfile userLogin() 
    {
        //String variables to hold first name, last name, and password
        String strFirstName;
        String strLastName;
        String strPassword;
        //while this is false, the user will not be logged in and so they won't be able to access the rest of the program 
        boolean bolIsLoggedIn = false; 

        //runs while the condition is false 
        do {
            System.out.println("Enter your first name, last name, and password to login.");
            System.out.println("Type 'exit' instead of a first name to leave.");

            System.out.print("Enter your first name: ");
            strFirstName = new Scanner(System.in).nextLine().trim();

            //exits the method if the user types in the keyword 
            if (strFirstName.equalsIgnoreCase("exit"))
            {
                System.out.println("Leaving login.");
                bolIsLoggedIn = true;
                break; 
            }

            System.out.print("Enter your last name: ");
            strLastName = new Scanner(System.in).nextLine().trim();

            System.out.print("Enter your password: ");
            strPassword = new Scanner(System.in).nextLine();

            // Check if entered first name and last name match any of the entries in the userProfiles
            UserProfile loggedInUser = checkUserCredentials(strFirstName, strLastName, strPassword);

            if (loggedInUser != null) {
                System.out.println("Login successful!");
                return loggedInUser;
            } else {
                System.out.println("Invalid name or password, please try again.");
            }
        } while (!bolIsLoggedIn);  //runs while condition is false;

        return null; 
    }

    //private helped method used to check the credentials of the user trying to login and whether they are accurate
    //if they are accurate, true is returned. Elsewise, null is returned 
    //iterates through the userProfiles array list to try and find a match 
    private static UserProfile checkUserCredentials(String firstName, String lastName, String password) 
    {
        for (UserProfile user : userProfiles) {
            if (firstName.equalsIgnoreCase(user.getFirstName()) && lastName.equalsIgnoreCase(user.getLastName()) && password.equals(user.getPassword())) {
                user.setLogin(true);
                return user;
            }
        }
        return null;
    }

    //this method prints out all the books that have been checked out by the user in a for each loop 
    public void printCheckedOutBooks() 
    {
        //checks to see if they first have any books checked out 
        if (checkedOutBooks.isEmpty()) 
        {
            System.out.println("You have no books checked out.");
        } else 
        {
            //prints checked out books 
            System.out.println("Books Checked Out:");

            for (Book checkedOutBook : checkedOutBooks) 
            {
                System.out.println("Book ID: " + checkedOutBook.getBookID() +", Title: " + checkedOutBook.getTitle() + ", Author: " + checkedOutBook.getAuthor()); 
            }
        }
    }

    //this method allows for the user to have options and a menu to decide what they want to do in the library 
    public void userOptions() 
    {
        //byte to store their choice 
        byte bytChoice = 0;
        do {

            System.out.println("\nLibrary Menu:");
            System.out.println("1. Check out a book");
            System.out.println("2. Return a book");
            System.out.println("3. Pay late fees");
            System.out.println("4. Print your currently checked out books"); 
            System.out.println("5. Logout");

            //getting input 
            bytChoice = Input.userMenuInput(); 

            //switch statement 
            switch (bytChoice) 
            {
                case 1:
                bookCheckout();
                break;
                case 2:
                returnBook();
                break;
                case 3:
                payLateFees();
                break;
                case 4:
                printCheckedOutBooks(); 
                break; 
                case 5:
                bolLogin = false; 
                System.out.println("Logout successful");
                break;
                //default:
                //System.out.println("Invalid choice. Please try again.");
            }
        } while (bytChoice != 5); // runs until the user types in 5 to logout  
    }

    //getter for the list of checked out books a user has 
    public ArrayList<Book> getCheckedOutBooks() 
    {
        return checkedOutBooks;
    }

    //getter for a user's first name 
    public String getFirstName ()
    {
        return this.strFirstName; 
    }

    //getter for a user's last name 
    public String getLastName ()
    {
        return this.strLastName; 
    }

    //getter for a user's password 
    public String getPassword ()
    {
        return this.strPassword; 
    }

    // getter for a user's ID 
    public short getID ()
    {
        return this.shrLibraryID; 
    }

    //getter for the fees owed of a user
    public float getFeesOwed()

    {
        return this.fltFeesOwed; 
    }

    //getter for bolLogin
    public boolean getLogin ()

    {
        return this.bolLogin; 
    }

    //static getter to return the array list of all user profiles 
    public static ArrayList<UserProfile> getUserProfiles()

    {
        return userProfiles; 
    }

    //setter to modify first name 
    public void setFirstName (String f)
    {
        this.strFirstName = f; 
    }

    //setter to modify last name 
    public void setLastName (String l)
    {
        this.strLastName = l; 
    }

    //setter to modify password 
    public void setPassword (String p)
    {
        this.strPassword = p; 
    }

    //setter ot modify id 
    public void setID (short i)
    {
        this.shrLibraryID = i; 
    }

    //setter to modify fees 
    public void setFeesOwed (float f)

    {
        this.fltFeesOwed = f; 
    }

    //setter for bolLogin
    public void setLogin (boolean l)

    {
        this.bolLogin = l; 
    }
}

