/**
 * Write a description of class FayizTestClass here.
 *
 *provides the entry point for the program and handles user interactions.
 *the class includes methods for user login, signup, and admin login, as well as
 *Creates initial profiles, books, and allows users and admins to interact
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */

public class FayizTestClass {
    public static void main(String[] args) 
    {
        final byte USER = 1; 
        final byte ADMIN = 2; 
        final byte LOGIN = 1; 
        final byte SIGNUP = 2;
        final byte EXIT = 3; 
        byte bytChoice, bytChoice2;

        // Create some initial admin and user profiles
        AdminProfile admin1 = new AdminProfile("admin", "adminpass");
        UserProfile user1 = new UserProfile("John", "Doe", "userpass");
        ScienceFictionBook sciFiBook = new ScienceFictionBook("Fahrenheit 451", "Ray Bradbury", (byte) 35, "Dystopia");
        HistoryBook historyBook = new HistoryBook ("History of Hippos", "Fayiz Khan", (byte) 47, "1900s"); 
        
        //nested if statements running in a do while loop 
        // if user/administrator successfully logs OR if new account is made, they will then be able to have menu options 
        do {
            System.out.print('\u000c');
            bytChoice = Input.introUser();

            if (bytChoice == USER) {
                bytChoice2 = Input.loginOrSignup();

                if (bytChoice2 == LOGIN) 
                {
                    UserProfile loggedInUser = UserProfile.userLogin();
                    if (loggedInUser != null) {
                        loggedInUser.userOptions();
                    }
                } else if (bytChoice2 == SIGNUP)
                {
                    UserProfile newUser = UserProfile.createUserProfile();
                    newUser.userOptions();
                }
            } else if (bytChoice == ADMIN) {
                AdminProfile loggedInAdmin = AdminProfile.adminLogin();
                if (loggedInAdmin != null) {
                    loggedInAdmin.adminOptions();
                }
            }
        } while (bytChoice != EXIT);

        exitMessage();
    }

    //Displays an exit message when the program concludes.
    public static void exitMessage() {
        System.out.println("Thanks for using Fayiz's library program!");
    }
}
