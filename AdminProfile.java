
/**
 * Write a description of class AdminProfile here.
 *
 *Admin end of the program. Once they have signed up or logged in, they can track late fees, manipulate books, add books, and remove books 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */

import java.util.Scanner;
import java.util.ArrayList; 

public class AdminProfile
{
    //private variables of type String to store the username and password 
    private String strAdminUsername; 
    private String strAdminPassword; 
    //private instance variable of type boolean to store whether the user is logged in or not
    private boolean bolLogin; 
    //private static arraylist to store all the admins 
    private static ArrayList <AdminProfile> adminList = new ArrayList<AdminProfile>();  

    
    //default constructor to meet the requirements of the assignment 
    public AdminProfile()

    {
        this.strAdminUsername = null; 
        this.strAdminPassword = null; 
        this.bolLogin = false; 
        adminList.add(this); 
    }
    
    // constructor to initialize the instance varaibles 
    public AdminProfile(String u, String p)

    {
        this.strAdminUsername = u; 
        this.strAdminPassword = p; 
        this.bolLogin = false; 
        adminList.add(this); 
    }

    //adminLogin method allows the user to enter credentials to login and then verifies to see if they are correct so that they
    //can use the rest of the program 
    //also gives them an option to exit 
    public static AdminProfile adminLogin()
    {
        //String variables to store username and password 
        String strUsername;
        String strPassword;
        //boolean to see whether the user has successfully logged in or not 
        boolean bolIsLoggedIn = false; 

        do {
            //prompting and taking input 
            System.out.println("Enter username (or type 'exit' to go back): ");
            strUsername = new Scanner(System.in).nextLine().trim();

            //exiting the method if they enter the right keyword
            if (strUsername.equalsIgnoreCase("exit")) {
                System.out.println("Exiting admin login.");
                bolIsLoggedIn = true; 
                break; 
            }
            
            //prompting and getting input 
            System.out.println("Enter password: ");
            strPassword = new Scanner(System.in).nextLine();

            //Check if entered username and password match any of the entries in the adminList
            AdminProfile loggedInAdmin = checkAdminCredentials(strUsername, strPassword);

            if (loggedInAdmin != null) {
                System.out.println("Login successful");
                return loggedInAdmin;
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } while (!bolIsLoggedIn); // runs as long as bolIsLogged is false 

        return null; 
    }

    //private helped method to iterate through the admin profiles and see if a username and password matches what the user inputted
    //returns true or null correspondingly 
    private static AdminProfile checkAdminCredentials(String username, String password) 
    {
        for (AdminProfile admin : AdminProfile.getAdminList())
        {
            if (username.equals(admin.getAdminUsername()) && password.equals(admin.getAdminPassword()))
            {
                admin.setBolLogin(true);
                return admin;
            }
        }
        return null;
    }

    //listUsersWithFees is a method designed to print the users who have late fees 
    public static void listUsersWithFees() 
    {
        //array list of type UserProfile to get all user profiles and store them 
        ArrayList<UserProfile> userProfiles = UserProfile.getUserProfiles();
        //boolean used as a condition of true or false depending on if there are users with late fees
        boolean usersWithFeesExist = false; 

        //checks if array list is empty first 
        if (userProfiles.isEmpty()) 
        {
            System.out.println("No user profiles available.");
            return;  // Exit the method if the list is empty
        }

        //iterates through the array 
        for (UserProfile user : userProfiles) 
        {
            if (user.getFeesOwed() > 0) 
            {
                System.out.println(user.getFirstName() + " " + user.getLastName() + " - $" + user.getFeesOwed());
                usersWithFeesExist = true;
            }
        }

        //if there are no users who owe fees, it will print this 
        if (!usersWithFeesExist) {
            System.out.println("No users owe money.");
        }
    }

    //method to add book to the library 
    //allows user to exit by typing the keywork 'exit' 
    public static void addBookToLibrary()
    {
        //variables of type String to store title and author 
        String strTitle, strAuthor;
        //variable of type Book that will be used to check if a book of that title and author already exists 
        Book existingBook;
        //byte to store the amount of copies of that book at the library 
        byte bytInitialCopies; 
        //boolean. admin will be able to add books as long as this is true 
        boolean bolAddBooks = true; 
        //this will store the new book added by the admin 
        Book newBook; 

        do{
            //prompting the admin for the title  
            System.out.println("Enter the title of the book (or type 'exit' to stop adding books): ");
            strTitle = new Scanner (System.in).nextLine();

            //checks to see if they typed 'exit' to exit the method 
            if (strTitle.equalsIgnoreCase("exit")) 
            {
                bolAddBooks = false;
                return; 
            }

            //prompting the admin for the user 
            System.out.println("Enter the author of the book: ");
            strAuthor = new Scanner (System.in).nextLine();

            //check to see of the book already exists 
            existingBook = findBookByTitleAndAuthor(strTitle, strAuthor);

            //if book exists, this iteration of the loop will end, and it will move on to the next one 
            //this still allows the admin to add more books 
            if (existingBook != null) 
            {
                System.out.println("A book with the same title and author already exists in the library.");
                continue;
            }
            //prompting for number of books and getting input 
            System.out.print("Enter the initial number of copies (min is 1 and max is 127): ");
            bytInitialCopies = Input.bytBookInput(); 
            
            //adding the new book 
            newBook = new Book(strTitle, strAuthor, bytInitialCopies);
            System.out.println("Book added to the library: " + newBook.getTitle());
        }while (bolAddBooks); //runs while bolAddBooks is true  
    }

    //helper method that checks if the book trying to be added already exists
    //if it does, that book is returned letting the admin know that the book is already in the library 
    private static Book findBookByTitleAndAuthor(String title, String author)
    {
        for (Book book : Book.getBookList())
        {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;
    }

    //method to remove books from the library 
    //can remove as many titles as you want! 
    public static void removeBookFromLibrary()
    {
        //boolean that will serve as the condition for the do while to run 
        boolean bolRemoveBooks = true;
        //short that will store the id of the book the admin wants removed 
        short shrIDToRemove; 
        //array list storing the list of books in the library 
        ArrayList<Book> bookList; 
        //variable of type book to represent the book the admin wants removed 
        Book bookToRemove; 

        do
        {
            //getting and storing the bookList 
            bookList = Book.getBookList();

            //checks to see if the library has books or now 
            if (bookList.isEmpty()) {
                System.out.println("No books available to remove.");
                break;
            }

            //prints available books 
            System.out.println("Available Books:");

            for (Book book : bookList) 
            {
                System.out.println(book.toString());
            }

            //prompts the user and stored input for the book id they want removed
            System.out.print("Enter the Book ID to remove (or enter '0' to stop removing books): ");
            shrIDToRemove = Input.shrBookIDInput();

            //lets the user exit the method 
            if (shrIDToRemove == 0)
            {
                bolRemoveBooks = false;
                return;
            }

            //checks to see if the book exists 
            bookToRemove = Book.findBookByID(shrIDToRemove);

            //removes book 
            if (bookToRemove != null)
            {
                Book.getBookList().remove(bookToRemove);
                System.out.println("Book removed from the library: " + bookToRemove.getTitle());
            }
            else
            {
                System.out.println("Book not found or invalid Book ID. Please try again.");
            }

        } while (bolRemoveBooks); //runs while the bolRemoveBooks is true 
    }

    // manipulateNumberOfCopies allows for the admin to change the number of copies of a certain book available 
    public static void manipulateNumberOfCopies() 
    {
        //variable of type short to store the ID of the book that needs to be manipulated 
        short shrIDToManipulate; 
        //variable of type byte to store the amount of copies the book should now have 
        byte bytNewCopies; 
        //boolean that will serve as the condition to allow the admin to manipulate as many books as they want 
        boolean bolModifyBooks = true; 
        //array list of type book to store the list of books in the library
        ArrayList<Book> bookList; 
        //variable of type Book that represents the book that needs to be manipulated 
        Book bookToManipulate; 

        do{
            //stores the list of books in the library currently 
            bookList = Book.getBookList();

            //checks to see if there are books available to manipulate 
            if (bookList.isEmpty()) {
                System.out.println("No books available to manipulate.");
                break;
            }

            //prints the books available 
            System.out.println("Available Books:");

            for (Book book : bookList) 
            {
                System.out.println(book.toString());
            }

            //prompts the user and stores the ID of the book they want to manipulate 
            System.out.print("Enter the Book ID to manipulate the number of copies or enter '0' to stop: ");
            shrIDToManipulate = Input.shrBookIDInput();

            //if the user enters 0, they can exit the program 
            if (shrIDToManipulate == 0) 
            {
                bolModifyBooks = false;
                break;
            }

            //gets either null or the book depending on if the ID is valid 
            bookToManipulate = Book.findBookByID(shrIDToManipulate);

            //allows the admin to manipulate the number of copies 
            if (bookToManipulate != null) {
                System.out.print("Enter the new number of copies (has to be between 1 and 127): ");
                bytNewCopies = Input.bytBookInput(); 

                //prints the update 
                bookToManipulate.setNumCopies(bytNewCopies);
                System.out.println("Number of copies updated for " + bookToManipulate.getTitle());
            } else {
                //lets the user know that it is invalid 
                System.out.println("Book not found or invalid Book ID.");
            }
        }while(bolModifyBooks); // runs while bolModifyBooks is true 
    }

    //method adminOptions allows the user to have a menu scree where they can choose what they want to do from an admin end 
    public void adminOptions() 
    {
        //variable of type byte to represent the user's choice 
        byte bytChoice;

        //loops through the menu
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. List users with fees");
            System.out.println("2. Add book to library");
            System.out.println("3. Remove book from library");
            System.out.println("4. Manipulate number of copies of a certain book");
            System.out.println("5. Logout");

            //System.out.println("Enter your choice: ");
            //gets input 
            bytChoice = Input.userMenuInput();

            //switch statement 
            switch (bytChoice) 
            {
                case 1:
                listUsersWithFees();
                break;
                case 2:
                addBookToLibrary();
                break;
                case 3:
                removeBookFromLibrary();
                break;
                case 4:
                manipulateNumberOfCopies();
                break;
                case 5:
                bolLogin = false; // Logout
                System.out.println("Logout successful");
                break;
                //default:
                //System.out.println("Invalid choice. Please try again.");
            }
        } while (bytChoice != 5); //runs until the admin types 5 to logout 
    }

    //getter to return the username of the admin 
    public String getAdminUsername ()

    {
        return this.strAdminUsername; 
    }

    //getter to return the password of the admin
    public String getAdminPassword ()

    {
        return this.strAdminPassword; 
    }

    //getter to return bolLogin
    public boolean getBolLogin ()

    {
        return this.bolLogin; 
    }

    //getter to return the admin list 
    public static ArrayList<AdminProfile> getAdminList() 
    {
        return adminList;
    }

    //setter to modify the admin username 
    public void setAdminUsername (String strUsername)

    {
        this.strAdminUsername = strUsername; 
    }

    //setter to modify the password  
    public void setAdminPassword (String strPassword)

    {
        this.strAdminPassword = strPassword; 
    }

    //setter to mofdify bolLogin
    public void setBolLogin(boolean bolLogin)

    {
        this.bolLogin = bolLogin; 
    }
}