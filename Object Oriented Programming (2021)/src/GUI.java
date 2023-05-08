import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * This class does basically nothing, it just launches the log in window and then the main window
 * It was supposed to handle a lot more interactions but swing is too complex...
 */
public class GUI
{
    /**
     * this window handles login and signup
     */
    private static WelcomeWindow welcomeWindow;
    /**
     * this window handles everything else
     */
    private static MainWindow mainWindow;
    /**
     * this is the user that is logged into the program
     */
    static User user;


    /**
     * this runs the welcome window and loads the databases (it also adds some delay to the loading screen so people can see the dots...)
     * @return A user that is valid for logging in
     */
    public static boolean runWelcome() throws IOException, InterruptedException
    {
        welcomeWindow = new WelcomeWindow();

        User.initializeDatabase("cred.txt");
        welcomeWindow.addDot();
        TimeUnit.MILLISECONDS.sleep(300);
        Messages.initializeDatabase("mess.txt");
        welcomeWindow.addDot();
        TimeUnit.MILLISECONDS.sleep(300);
        Rental.initializeDatabase("rentals.txt");
        welcomeWindow.addDot();
        TimeUnit.MILLISECONDS.sleep(300);
        Date.initializeDatabase("dates.txt");
        welcomeWindow.addDot();
        TimeUnit.MILLISECONDS.sleep(300);

        welcomeWindow.changePanel();


        //This is probably not a great way of doing this...
        while (welcomeWindow.getDone() == 0)
        {
            TimeUnit.SECONDS.sleep(1);
        }

        if (welcomeWindow.getDone() == -1)
        {
            System.exit(0);
        }

        user = welcomeWindow.getUser();

        if (welcomeWindow.getDone() == 2)
        {
            return false;
        }

        return true;
    }

    /**
     * this just launches the main window with the user from the previous method
     */
    public static void runGui() throws IOException, InterruptedException
    {
        if (runWelcome())
        {
            mainWindow = new MainWindow(user);

            while (mainWindow.getDone() != 1)
            {
                TimeUnit.SECONDS.sleep(5);
            }

        }



        GUIMethods.updateDatabases();
        System.exit(0);
    }

}
