
/**
 * Write a description of class HistoryBook here.
 *
 *Subclass of book where there is an additonal instance variable for time period 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */

public class HistoryBook extends Book
{
    //private instance variable of type string to store the time period 
    private String strTimePeriod;

    //constructor to initialize the values 
    public HistoryBook(String strTitle, String strAuthor, byte bytNumCopies, String strTimePeriod)
    {
        super(strTitle, strAuthor, bytNumCopies);
        this.strTimePeriod = strTimePeriod;
    }

    //getter to return the time period of the book 
    public String getTimePeriod() 
    {
        return this.strTimePeriod;
    }

    //setter to modify the time period 
    public void setTimePeriod (String t)

    {
        this.strTimePeriod = t; 
    }

    //overidden toString to print the contents of HistoryBook 
    @Override
    public String toString() 
    {
        return super.toString() + ", Time Period: " + strTimePeriod;
    }
}

