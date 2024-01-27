
/**
 * Write a description of class Input here.
 *
 *manages input for all the classes 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */
import java.util.Scanner; 
public class Input
{
    //introUser takes input for the user's choices as to what they want to do in the program 
    //error trapping is included
    public static byte introUser() 
    {
        //byte to store their response
        byte bytResponse = 0;
        //boolean to error catch
        boolean bolErrorCatch = false;

        do {
            System.out.println("Type 1 if you are you seeking to use this program as a library patron, 2 if you are an administrator, and 3 if you wish to end the program");
            //try block taking input 
            try {
                bytResponse = new Scanner(System.in).nextByte();
                bolErrorCatch = true;
                //checks to see if input is valid 
                if (bytResponse != 1 && bytResponse != 2 && bytResponse != 3) {
                    System.out.println("Invalid input!");
                    bolErrorCatch = false;
                } 

            }
            //catches exceptions 
            catch (Exception e) 
            {
                System.out.println("Invalid input!");
                bolErrorCatch = false;
            }
        } while (!bolErrorCatch); //loops until a valid byte is answered 

        //returns the user's response 
        return bytResponse;
    }

    //loginOrSignup checks whether the user wants to loginOrSignUp 
    //makes them type 1 or 2, error trapping is included
    public static byte loginOrSignup ()

    {
        //bytResponse is a byte that will store their response 
        byte bytResponse = 0; 
        //boolean to error catch 
        boolean bolErrorCatch = false; 

        //prompting the user: 
        System.out.println("Type 1 if you want to login, or 2 if you want to sign up:"); 

        do{
            //trying input 
            try{
                bytResponse = new Scanner (System.in).nextByte(); 
                bolErrorCatch = true; 

                //bolErroCatch is false if invalid response is given 
                if(bytResponse != 1 && bytResponse!= 2)
                {
                    System.out.println("Invalid input! \nPlease type 1 if you want to login or 2 if you want to sign up:");   
                    bolErrorCatch = false; 
                }
            }

            catch (Exception e)

            {
                System.out.println("Invalid input! \nPlease type 1 if you want to login or 2 if you want to sign up:"); 
                bolErrorCatch = false; 
            }
        } while(!bolErrorCatch); //loops until 1 or 2 is entered 

        //returns the response 
        return bytResponse; 
    }  

    //userMenuInput takes input for the userMenu for both admin and user 
    public static byte userMenuInput ()
    {
        //byte to store choice 
        byte bytChoice = 0; 
        //boolean for error trapping purposes
        boolean bolTryCatch = false; 

        do{
            //promptign 
            System.out.print("Enter your choice: ");
            try
            {
                bytChoice = new Scanner (System.in).nextByte();
                bolTryCatch = true; 

                //ensures valid response
                if (bytChoice != 1 && bytChoice != 2 && bytChoice != 3 && bytChoice != 4 && bytChoice != 5 )
                {
                    System.out.println("Invalid input! Enter 1,2,3,4 or 5"); 
                    bolTryCatch = false; 
                }

            }
            //catch block 
            catch (Exception e)

            {
                System.out.println("Invalid input! Enter 1,2,3,4 or 5"); 
                bolTryCatch = false; 
            }
        } while (!bolTryCatch);//runs until 1 2 3 4 o4 5 is entered

        //returns response 
        return bytChoice; 
    }

    //lateFeesInput take the input for when the user is paying late fees 
    public static float lateFeesInput()

    {
        //boolean for error trapping 
        boolean bolErrorCatch = false; 
        //fltPayment to store payment
        float fltPayment = 0.0f; 

        do{
            //prompting 
            System.out.print("Enter the amount you want to pay: $");
            try
            {
                fltPayment = new Scanner(System.in).nextFloat();
                bolErrorCatch = true;
                
                //ensusring valid payment amount 
                if (fltPayment <= 0)
                {
                    System.out.println("Invalid payment amount. Please try again!");
                    bolErrorCatch = false; 

                }
            }

            //ensuring vakud payment amount 
            catch (Exception e) 

            {
                System.out.println("Invalid payment amount. Please try again!");
                bolErrorCatch = false; 
            }
        } while (!bolErrorCatch);//runs until valid amount is entered 

        return fltPayment; //returns the payment 

    }

    //shrBookIDInput takes input for bookID 
    public static short shrBookIDInput()

    {
        //short to hold ID
        short shrBookIDCheckout = 0; 
        //boolean for error trapping 
        boolean bolErrorCatch; 
        do{
            try {
                shrBookIDCheckout = new Scanner (System.in).nextShort();
                bolErrorCatch = true; 

                //makes sure valid short is answered 
                if (shrBookIDCheckout < 0)

                {
                    System.out.println("Can't enter a negative value! Please try again: "); 
                    bolErrorCatch = false; 

                }

            }
            //ensures valid input 
            catch (Exception e)
            {
                System.out.println("Invalid input! Please try again: "); 
                bolErrorCatch = false; 
            }
        } while(!bolErrorCatch); //runs till valid input is entered 

        return shrBookIDCheckout; //retuns short 

    }

    //this method takes input for the amount of books that need to be added/manipulated
    public static byte bytBookInput()
    {
        //byte to store numbooks 
        byte bytNumBooks = 0; 
        //boolean for error trapping 
        boolean bolTryCatch = false; 

        do{
            try
            {
                bytNumBooks = new Scanner (System.in).nextByte(); 
                bolTryCatch = true; 

                //ensures valid number is typed 
                if (bytNumBooks <1 || bytNumBooks > 127)

                {
                    System.out.println("You can't have that number of books!"); 
                    bolTryCatch = false; 

                }
            }
        
             //ensures valid number is typed 
            catch (Exception e)

            {
                System.out.println("You can't have that number of books!"); 
                bolTryCatch = false; 

            }
        } while (!bolTryCatch); //runs till bolTryCacth is true (valid input is provided) 

        return bytNumBooks; //returns numBooks 

    }
}
