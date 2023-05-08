import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class UI
{
    protected static User user;
    protected static Scanner input;


//Οι μέθοδοι αυτής της κλάσης δεν έχουν περιγραφή καθώς δεν έχουν κάποια γενική χρήση. Ο μόνος λόγος που αποτελούν ξεχωριστές
//μεθόδους είναι για να κάνουν τον κώδικα πιο ευανάγνωστο (και να μη βρίσκονται όλες σε μία μεγάλη main), οπότε ο
//σχολιασμός τους θα ήταν περιττός.

//----------------------------------------------------------------------------------------------------------------------
//LoadingScreen
//----------------------------------------------------------------------------------------------------------------------

    public static void loadingScreen() throws IOException
    {
        System.out.println("");
        System.out.print("FIREBNB");
        System.out.println('\u2122');
        System.out.println("");

        System.out.println("=======================================");
        System.out.print("Loading");
        User.initializeDatabase("cred.txt");
        System.out.print(".");
        Messages.initializeDatabase("mess.txt");
        System.out.print(".");
        Rental.initializeDatabase("rentals.txt");
        System.out.println(".");
        Date.initializeDatabase("dates.txt");
        System.out.println("=======================================");
    }


    //Welcome Screen and sub-screens
//----------------------------------------------------------------------------------------------------------------------
    private static void welcomeScreen()
    {
        System.out.println("");
        System.out.println("Welcome");
        System.out.println("");

        System.out.println("=======================================");
        System.out.println("0.Exit");
        System.out.println("1.Login");
        System.out.println("2.Sign Up");
        System.out.println("=======================================");
        int entry = UIMethods.validityCheck(0, 2);


        if (entry == 1)
        {
            loginScreen();
        }
        else if (entry == 2)
        {
            signupScreen();
        }
        else
        {
            System.exit(0);
        }


    }

    //  |
//  V
    private static void loginScreen()
    {


        System.out.println("=======================================");
        System.out.println("Username :");
        String username = UIMethods.inputUserName(false);
        System.out.println("=======================================");


        user = new User(username);


        System.out.println("Password :");
        UIMethods.inputPassword();
        System.out.println("=======================================");

    }

    private static void signupScreen()
    {
        System.out.println("=======================================");
        System.out.print("Welcome to FIREBNB");
        System.out.println('\u2122');
        System.out.println("=======================================");
        System.out.println("Please enter a Username :");
        String username = UIMethods.inputUserName(true);
        user = new User(username);
        System.out.println("=======================================");
        System.out.println("Please enter a Password :");
        String password = UIMethods.newPassword();
        user.addCredential("password", password);
        System.out.println("=======================================");
        System.out.println("Please enter your email :");
        String email = UIMethods.newEmail();
        user.addCredential("email", email);
        System.out.println("=======================================");

        user.addCredential("activated", "no");


        System.out.print("Thank you for signing up for FIREBNB");
        System.out.println('\u2122');
        System.out.println("=======================================");
    }


    //----------------------------------------------------------------------------------------------------------------------
//Main menu and sub-menus
    private static void mainMenu()
    {
        System.out.println("=======================================");
        System.out.println("What would you like to do?");
        System.out.println("");
        System.out.println("0.Exit");
        System.out.println("1.Account Settings");
        System.out.println("2.Messages");
        System.out.println("3.Rentals");

        if (user.is("provider"))
        {
            System.out.println("4.Rental Provider Mode");
        }

        if (user.is("admin"))
        {
            System.out.println("5.Admin Mode");
        }

        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 5);

        if (entry == 1)
        {
            accountMenu();
        }
        else if (entry == 2)
        {
            messageMenu();
        }
        else if (entry == 3)
        {
            rentalMenu();
        }
        else if (entry == 4)
        {
            if (user.is("provider"))
            {
                providerMenu();
            }
            else
            {
                System.out.println("You are not a Rental Provider");
                mainMenu();
            }
        }
        else if (entry == 5)
        {
            if (user.is("admin"))
            {
                adminMenu();
            }
            else
            {
                System.out.println("You are not an Admin");
                mainMenu();
            }
        }
        else
        {
            System.out.println("Goodbye :)");
            return;
        }
    }

    //  |
//  V
    private static void accountMenu()
    {
        System.out.println("=======================================");
        System.out.println("Username :" + user.getUserName());
        System.out.println("");
        System.out.println("0.Back");
        System.out.println("1.Change Password");
        System.out.println("2.Change email");
        if (!user.is("admin"))
        {
            System.out.println("3.Request Admin Privileges");
        }
        if (!user.is("provider"))
        {
            System.out.println("4.Request Rental Provider Privileges");
        }
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 4);


        if (entry == 1)
        {
            changePassword();
            accountMenu();
        }
        else if (entry == 2)
        {
            changeEmail();
            accountMenu();
        }
        else if (entry == 3)
        {
            if (user.is("admin"))
            {
                System.out.println("You already have Admin Privileges");
                System.out.println("=======================================");
            }
            else
            {
                trialOfTheAdmin();
            }
            accountMenu();
        }
        else if (entry == 4)
        {
            if (user.is("provider"))
            {
                System.out.println("You already have Rental Provider Privileges");
                System.out.println("=======================================");
            }
            else
            {
                providerRequest();
            }
            accountMenu();
        }
        else
        {
            mainMenu();
        }

    }

    private static void messageMenu()
    {
        System.out.println("=======================================");
        System.out.println("Messages");
        System.out.println("");
        System.out.println("0.Back");
        System.out.println("1.View Messages");
        System.out.println("2.Send a Message");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 2);

        if (entry == 1)
        {
            Messages.showMessages(user.getUserName());
            messageMenu();
        }
        else if (entry == 2)
        {
            sendMessage();
            messageMenu();
        }
        else
        {
            mainMenu();
        }
    }

    private static void rentalMenu()
    {
        System.out.println("=======================================");
        System.out.println("Welcome to our listings");
        System.out.println("");
        System.out.println("0.Back");
        System.out.println("1.Search");
        System.out.println("2.View your reservations");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 2);

        if (entry == 1)
        {
            searchRentals();
            rentalMenu();
        }
        if (entry == 2)
        {
            viewMyReservations(user.getUserName());
            rentalMenu();
        }
        else
        {
            mainMenu();
        }


    }

    private static void providerMenu()
    {
        System.out.println("=======================================");
        System.out.println("0.Back");
        System.out.println("1.View your Properties"); //when viewing you should also be able to edit characteristics
        System.out.println("2.Add a Listing");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 2);

        if (entry == 1)
        {
            viewMyListings();
            providerMenu();
        }
        else if (entry == 2)
        {
            addListing();
            providerMenu();
        }
        else
        {
            mainMenu();
        }
    }

    private static void adminMenu()
    {
        System.out.println("=======================================");
        System.out.println("0.Back");
        System.out.println("1.Review requests");
        System.out.println("2.View User Info");
        System.out.println("3.View Rental Info");
        System.out.println("4.View all Reservations");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 4);

        if (entry == 1)
        {
            reviewRequests();
            adminMenu();
        }
        else if (entry == 2)
        {
            userInfo();
            adminMenu();
        }
        else if (entry == 3)
        {
            rentalInfo();
            adminMenu();
        }
        else if (entry == 4)
        {
            HashSet<String> usernames = User.getAll();
            for (String username : usernames)
            {
                System.out.println("=======================================");
                System.out.println("User: " + username);
                System.out.println("=======================================");
                viewMyReservations(username);
            }
            adminMenu();
        }
        else
        {
            mainMenu();
        }
    }

    //  |
//  V
    //AccountMenu()
    private static void changePassword()
    {
        System.out.println("=======================================");
        System.out.println("Please enter your new Password");
        String newPassword = UIMethods.newPassword();
        user.setCredential("password", newPassword);
        System.out.println("New Password successfully set");
        System.out.println("=======================================");
    }

    private static void changeEmail()
    {
        System.out.println("=======================================");
        System.out.println("Please enter your new email");
        String newEmail = UIMethods.newEmail();
        user.setCredential("email", newEmail);
        System.out.println("New email successfully set");
        System.out.println("=======================================");
    }

    private static void providerRequest()
    {
        System.out.println("=======================================");
        System.out.println("Rental Providers have the ability to List their own Rentals");
        System.out.println("Would you like to Become a Rental Provider?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(1, 2);

        if (entry == 1)
        {
            System.out.println("=======================================");
            System.out.println("Please enter your phone number :");
            String newPhone = UIMethods.newPhone();

            user.addCredential("provider", "no");

            System.out.println("A request has been sent to give you Provider Privileges");
            System.out.println("You will be able to list Rentals as soon as one of our Admins gets to your request");
            System.out.println("Thank you");
        }
        else
        {
            System.out.println("Ok.");
        }
        System.out.println("=======================================");

    }

    private static void trialOfTheAdmin()
    {
        System.out.println("=======================================");
        System.out.println("Please enter the SPECIAL PASSWORD");
        System.out.println("=======================================");
        String spPassword = input.next();
        if (spPassword.equals("admin"))
        {
            user.addCredential("admin", "yes");

            System.out.println("=======================================");
            System.out.println("YOU DID IT!!!");
            System.out.println("You are now an admin (or as soon as the program restarts :/ )");
            System.out.println("=======================================");
        }
        else
        {
            System.out.println("=======================================");
            System.out.println("YOU HAVE FAILED THE TRIAL OF THE ADMIN AND CANNOT BECOME ONE OF US");
            System.out.println("=======================================");
        }
    }

    //messageMenu()
    private static void sendMessage()
    {
        System.out.println("=======================================");
        System.out.println("Enter the Username of the recipient");
        System.out.println("=======================================");
        String recipient = UIMethods.inputUserName(false);
        System.out.println("Write the body of your message (CAN ONLY BE ONE LINE)");
        System.out.println("=======================================");


        Scanner message = new Scanner(System.in);

        Messages.send(user.getUserName(), recipient, message.nextLine());


        System.out.println("=======================================");


    }

    //rentalMenu()
    private static void searchRentals()
    {
        System.out.println("=======================================");
        System.out.println("Welcome to your travel Planner");
        System.out.println("When are you traveling?");
        System.out.println("=======================================");

        System.out.println("From :");
        Date startDate = UIMethods.inputDate();

        System.out.println("=======================================");

        System.out.println("Until :");
        Date endDate = UIMethods.inputDate();

        System.out.println("=======================================");

        HashSet<String> availableIds = Date.getAvailableBetween(startDate, endDate);


        System.out.println("=======================================");

        System.out.println("Would you like to set additional filters?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("=======================================");


        int entry = UIMethods.validityCheck(1, 2);

        if (entry == 1)
        {
            availableIds = UIMethods.addFilters(availableIds);
        }


        if (availableIds.size() > 0)
        {
            System.out.println(availableIds.size() + "Available Rentals");
            System.out.println("=======================================");
            for (String id : availableIds)
            {
                Rental rental = new Rental(id);
                UIMethods.showRental(rental);

                System.out.println("=======================================");
                System.out.println("Would you like to reserve this?");
                System.out.println("1.Yes");
                System.out.println("2.No");
                System.out.println("=======================================");

                entry = UIMethods.validityCheck(1, 2);

                if (entry == 1)
                {
                    for (Date date : Date.getBetween(startDate, endDate))
                    {
                        Date.addDate(id, user.getUserName(), date);
                    }

                    System.out.println("Thank you");
                    System.out.println("=======================================");
                }
            }
        }
        else
        {
            System.out.println("=======================================");
            System.out.println("Sorry there are no available rentals for your specifications");
            System.out.println("Try different combinations of filters :)");
            System.out.println("=======================================");
        }

    }

    /**
     * Εμφανίζει όλες τις κρατήσεις ενός χρήστη
     *
     * @param username του χρήστη
     */
    private static void viewMyReservations(String username)
    {
        HashSet<String> allRentals = Rental.getAll();

        boolean has = false;

        for (String id : allRentals)
        {
            if (Date.getDatesFor(id, username).size() != 0)
            {
                has = true;
                break;
            }
        }


        if (!has)
        {
            System.out.println("=======================================");
            System.out.println("No reservations");
            System.out.println("=======================================");
            return;
        }

        for (String id : allRentals)
        {
            Rental rental = new Rental(id);
            HashSet<Date> dates = Date.getDatesFor(id, username);

            if (!dates.isEmpty())
            {
                Date first = Date.getFirst(dates); //This first-last system would actually be inaccurate if the same user booked the same rental
                Date last = Date.getLast(dates);  //for different time periods. We could easily fix this but because of the structure of our database
                //the dates would be sorted Alphabetically and not sequentially as dates
                //since this is a small issue, for demonstration purposes we decided to go with the former method

                System.out.println("=======================================");
                System.out.println(rental.getCharacteristic("name") + " (id: " + id + ")");
                System.out.println("");
                System.out.print("From: ");
                first.print();
                System.out.print("Until: ");
                last.print();
                System.out.println("=======================================");
            }
        }
    }

    //providerMenu()
    private static void viewMyListings()
    {
        HashSet<String> myIds = Rental.strongFilter("owner", user.getUserName());

        for (String id : myIds)
        {
            Rental rental = new Rental(id);
            UIMethods.showRental(rental);

            System.out.println("=======================================");
            System.out.println("Would you like to edit this listing?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            System.out.println("=======================================");

            int entry = UIMethods.validityCheck(1, 2);

            if (entry == 1)
            {
                UIMethods.editRental(rental);
            }
        }
    }

    private static void addListing()
    {
        System.out.println("=======================================");
        System.out.println("Let's create a new Listing");
        Rental rental = new Rental(Rental.getAvailableID());

        Scanner lineScanner = new Scanner(System.in);

        System.out.println("Enter Name of Listing :");
        String name = lineScanner.nextLine();
        rental.addCharacteristic("name", name);

        System.out.println("Enter type (hotel,bnb...) :");
        String type = lineScanner.nextLine();
        rental.addCharacteristic("type", type);

        System.out.println("Enter location :");
        String location = lineScanner.nextLine();
        rental.addCharacteristic("location", location);

        System.out.println("Enter Price Per Day :");
        String price = lineScanner.nextLine();
        rental.addCharacteristic("price", price);

        System.out.println("Enter Square Meters (m^2) :");
        String sqMet = lineScanner.nextLine();
        rental.addCharacteristic("m2", sqMet);

        rental.addCharacteristic("owner", user.getUserName());

        System.out.println("Additional Info :");
        System.out.println("Does your Rental Provide :");

        System.out.println("WiFi?:");
        UIMethods.boolCharacteristicEntry(rental, "wifi");
        System.out.println("Parking?:");
        UIMethods.boolCharacteristicEntry(rental, "parking");
        System.out.println("Pool?:");
        UIMethods.boolCharacteristicEntry(rental, "pool");


        rental.updateCharacteristics();
        Date.addDate(rental.getId(), user.getUserName(), new Date(1, 1, 2021));
        System.out.println("Listing added for other users to see");
        System.out.println("=======================================");
    }

    //adminMenu
    private static void reviewRequests()
    {
        HashSet<String> unverifiedUsers = User.findAllWith("activated", "no");
        for (String uname : unverifiedUsers)
        {
            System.out.println("=======================================");
            System.out.println("Activate User: " + uname + " ?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            System.out.println("=======================================");
            int entry = UIMethods.validityCheck(1, 2);

            if (entry == 1)
            {
                User tuser = new User(uname);
                tuser.setCredential("activated", "yes");
                tuser.updateCredentials();
                System.out.println("User " + tuser.getUserName() + " Activated");
            }
        }

        HashSet<String> unverifiedProviders = User.findAllWith("provider", "no");
        for (String uname : unverifiedProviders)
        {
            if (!uname.equals(user.getUserName()))
            {
                System.out.println("=======================================");
                System.out.println("Give Provider Privileges to User: " + uname + " ?");
                System.out.println("1.Yes");
                System.out.println("2.No");
                System.out.println("=======================================");

                int entry = UIMethods.validityCheck(1, 2);

                if (entry == 1)
                {
                    User tuser = new User(uname);
                    tuser.setCredential("provider", "yes");
                    tuser.updateCredentials();
                    System.out.println("User " + tuser.getUserName() + " Provider");
                }
            }
        }
    }

    private static void userInfo()
    {
        System.out.println("=======================================");
        System.out.println("0.Back");
        System.out.println("1.View a User's credentials");
        System.out.println("2.Search users with certain credentials");
        System.out.println("3.View all user info");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(0, 3);

        if (entry == 1)
        {
            singleUserInfo();
        }
        else if (entry == 2)
        {
            searchUsers();
        }
        else if (entry == 3)
        {
            User.showAll();
        }

    }

    private static void rentalInfo()
    {
        System.out.println("0.Back");
        System.out.println("1.View a Rental's info");
        System.out.println("2.View all Rental info");

        int entry = UIMethods.validityCheck(0, 2);

        if (entry == 1)
        {
            singleRentalInfo();
        }
        else if (entry == 2)
        {
            Rental.showAll();
        }
    }

    //  |
//  V
    //userInfo()
    private static void singleUserInfo()
    {
        System.out.println("=======================================");
        System.out.println("Enter the name of a user:");

        String userName = UIMethods.inputUserName(false);

        User tuser = new User(userName);

        System.out.println("=======================================");
        System.out.println("Username: " + tuser.getUserName());
        System.out.println("Email: " + tuser.getCredential("email"));
        System.out.println("Activated: " + tuser.is("activated"));
        System.out.println("Provider: " + tuser.is("provider"));
        System.out.println("Admin :" + tuser.is("admin"));
        System.out.println("=======================================");

    }

    private static void searchUsers()
    {
        System.out.println("=======================================");
        System.out.println("Search based on: ");
        System.out.println("1.Email");
        System.out.println("2.Activated");
        System.out.println("=======================================");

        int entry = UIMethods.validityCheck(1, 2);

        if (entry == 1)
        {
            System.out.println("Enter an email");
            System.out.println("=======================================");
            String email = input.next();

            String username = User.findWith("email", email);

            if (username.equals("-1"))
            {
                System.out.println("=======================================");
                System.out.println("No user found");
                System.out.println("=======================================");
            }
            else
            {
                System.out.println("=======================================");
                System.out.println("User: " + username);
                System.out.println("=======================================");
            }
        }
        else
        {
            System.out.println("=======================================");
            System.out.println("1.Users who are activated");
            System.out.println("2.Users who aren't activated");
            System.out.println("=======================================");
            String option;

            int entry2 = UIMethods.validityCheck(1, 2);
            if (entry2 == 1)
            {
                option = "yes";
            }
            else
            {
                option = "no";
            }

            HashSet<String> users = User.findAllWith("activated", option);

            for (String name : users)
            {
                System.out.println("=======================================");
                System.out.println(name);
                System.out.println("=======================================");
            }
        }
    }

    //rentalInfo()
    private static void singleRentalInfo()
    {
        System.out.println("=======================================");
        System.out.println("Enter the id of a Rental:");
        System.out.println("=======================================");

        String id = UIMethods.inputValidId();
        Rental tRental = new Rental(id);

        UIMethods.showRental(tRental);

    }

//----------------------------------------------------------------------------------------------------------------------


    public static void runUI() throws IOException
    {
        input = new Scanner(System.in);

        loadingScreen(); //Edw exoume dedomena

        welcomeScreen(); //Edw exoume xrhsth


        if (!user.is("activated"))
        {
            System.out.println("Your Account has not been activated yet");
            System.out.println("Please wait for an Admin to activate it");
            System.out.println("Thank you");
            System.out.println("=======================================");
        }
        else
        {
            System.out.println("Welcome " + user.getUserName());
            System.out.println("=======================================");

            mainMenu();
        }


        UIMethods.updateDatabases();

    }


}

