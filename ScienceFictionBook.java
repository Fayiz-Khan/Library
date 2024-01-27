
/**
 * Write a description of class ScienceFictionBook here.
 * 
 * subclass of book where there is an additional futuristic setting variable 
 *
 * @author (Fayiz Khan)
 * @version (Jan. 18, 2024)
 */

class ScienceFictionBook extends Book
{
    //private instance variable of type string to store the futuristic setting 
    private String strFuturisticSetting;

    //constructor to initialize the values 
    public ScienceFictionBook(String strTitle, String strAuthor, byte bytNumCopies, String strFuturisticSetting)
    {
        super(strTitle, strAuthor, bytNumCopies);
        this.strFuturisticSetting = strFuturisticSetting;
    }
    
    
    //getter to return the futuristic setting
    public String getFuturisticSetting()
    {
        return this.strFuturisticSetting;
    }

    //setter to modify the futuristic setting 
    public void setFuturisticSetting(String f)
    {
        this.strFuturisticSetting = f;
    }

    //overidden toString to print the contents if ScienceFictionBook 
    @Override
    public String toString() 
    {
        return super.toString() + ", Futuristic Setting: " + strFuturisticSetting;
    }
}
