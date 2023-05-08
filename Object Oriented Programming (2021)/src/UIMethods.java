import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public interface UIMethods
{

    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος σε όλες τις βάσεις δεδομένων που χρησιμοποιούνται
     */
    static void updateDatabases() throws IOException
    {
        User.updateDatabase(UI.user);
        Messages.updateDatabase();
        Rental.updateDatabase();
        Date.updateDatabase();
    }

    /**
     * Διαβάζει έναν αριθμό από το terminal και σιγουρεύεται πως βρίσκεται σε ένα εύρος ακεραίων
     *
     * @param from αρχή εύρους
     * @param to   τέλος εύρους(μπορεί να παραληφθεί)
     * @return τον ακεραιο που διάβασε
     */
    static int validityCheck(int from, int to)
    {

        boolean go = false;
        int entry = 0;

        while (!go)
        {
            entry = UI.input.nextInt();
            if (entry >= from && entry <= to)
            {
                go = true;
            }
            else
            {
                System.out.println("=======================================");
                System.out.println("Please enter a number from " + from + " to " + to);
                System.out.println("=======================================");
            }
        }


        return entry;
    }

    /**
     * @see #validityCheck(int, int)
     */
    static int validityCheck(int from)
    {

        boolean go = false;
        int entry = 0;

        while (!go)
        {
            entry = UI.input.nextInt();
            if (entry >= from)
            {
                go = true;
            }
            else
            {
                System.out.println("=======================================");
                System.out.println("Please enter a number larger than " + from);
                System.out.println("=======================================");
            }
        }


        return entry;
    }

    /**
     * Διαβάζει ένα username, και ανάλογα με τη μεταβλητή available επιτρέπει usernames που υπάρχουν ή δεν υπάρχουν στο database
     *
     * @param available αν true, σιγουρεύεται πως το username δεν ανήκει σε κανέναν χρήστη <p>
     *                  αν false, σιγουρεύεται πως το username ανήκει σε κάποιον χρήστη
     * @return το username που διάβασε
     */
    static String inputUserName(boolean available)
    {
        boolean go = false;
        String username = "";

        while (!go)
        {
            username = UI.input.next();
            if (User.checkUsernameAvailability(username) != available)
            {
                if (available)
                {
                    System.out.println("=======================================");
                    System.out.println("Username taken");
                    System.out.println("Username :");
                }
                else
                {
                    System.out.println("=======================================");
                    System.out.println("User doesn't exist");
                    System.out.println("Username :");
                }
            }
            else
            {
                go = true;
            }
        }

        return username;
    }

    /**
     * Διαβάζει ένα id που αντιστοιχεί σε κάποιο κατάλυμα
     *
     * @return το id που διάβασε
     */
    static String inputValidId()
    {
        boolean go = false;
        String id = "";

        while (!go)
        {
            id = UI.input.next();
            if (Rental.checkIdAvailability(id))
            {
                System.out.println("=======================================");
                System.out.println("Id doesn't correspond to an existing rental");
                System.out.println("Input id:");
                System.out.println("=======================================");
            }
            else
            {
                go = true;
            }
        }

        return id;
    }

    /**
     * Διαβάζει και ελέγχει το password του χρήστη που προσπαθεί να κάνει login
     * Αν βάλει 5 φορές λάθος password τότε κλείνει την εφαρμογή
     *
     * @return το έγκυρο password του χρήστη
     */
    static String inputPassword()
    {
        String password = "";

        boolean go = false;
        int tries = 0;

        while (!go)
        {
            password = UI.input.next();
            if (!password.equals(UI.user.getCredential("password")))
            {
                tries++;
                if (tries == 5)
                {
                    System.out.println("=======================================");
                    System.out.println("Failed to enter password too many times");
                    System.out.println("Goodbye :)");
                    System.out.println("=======================================");
                    System.exit(0);
                }
                else
                {
                    System.out.println("=======================================");
                    System.out.println("Incorrect password");
                    System.out.println(5 - tries + " tries remaining");
                    System.out.println("Password :");
                }
            }
            else
            {
                go = true;
            }
        }
        return password;

    }

    /**
     * Διαβάζει ένα καινούργιο password από το terminal
     *
     * @return το νέο password
     */
    static String newPassword()
    {
        boolean go = false;
        String pass1 = " ";

        while (!go)
        {
            pass1 = UI.input.next();
            if (pass1.equals("password") || pass1.equals("Password"))
            {
                System.out.println("=======================================");
                System.out.println("Your Password cant be '" + pass1 + "'");
                System.out.println("Try something else");
                System.out.println("Password :");
            }
            else
            {
                go = true;
            }
        }
        System.out.println("=======================================");
        System.out.println("Please re-enter your Password :");

        String pass2 = "";
        go = false;

        while (!go)
        {
            pass2 = UI.input.next();
            if (!pass2.equals(pass1))
            {
                System.out.println("=======================================");
                System.out.println("Passwords don't match");
                System.out.println("Re-enter Password :");
            }
            else
            {
                go = true;
            }
        }

        return pass1;

    }

    /**
     * Διαβάζει ένα καινούργιο email από το terminal
     *
     * @return το νέο email
     */
    static String newEmail()
    {
        boolean go = false;
        String email = "";

        while (!go)
        {
            email = UI.input.next();
            if (!email.contains(".") || !email.contains("@"))
            {
                System.out.println("=======================================");
                System.out.println("Invalid email address");
                System.out.println("Enter email :");
            }
            else if (!User.checkCredentialAvailability("email", email))
            {
                System.out.println("=======================================");
                System.out.println("Email already in use");
                System.out.println("Enter email :");
            }
            else
            {
                go = true;
            }
        }
        return email;
    }

    /**
     * Διαβάζει ένα καινούργιο τηλεφωνικό αριθμό από το terminal
     *
     * @return το νέο αριθμό
     */
    static String newPhone()
    {
        boolean go = false;
        String newPhone = "";

        while (!go)
        {
            newPhone = UI.input.next();
            if (newPhone.length() != 10)
            {
                System.out.println("=======================================");
                System.out.println("Invalid Phone Number");
                System.out.println("Phone :");
            }
            else
            {
                go = true;
            }
        }
        System.out.println("=======================================");
        return newPhone;
    }

    /**
     * Επιτρέπει την επεξεργασία όλων των χαρακτηριστικών ενός καταλύματος από το terminal
     *
     * @param rental το κατάλυμα προς επεξεργασία
     */
    static void editRental(Rental rental)
    {
        System.out.println("=======================================");
        System.out.println("What would you like to edit?");
        System.out.println("0.Nothing");
        System.out.println("1.Name");
        System.out.println("2.Type");
        System.out.println("3.Location");
        System.out.println("4.m^2");
        System.out.println("5.Price");
        System.out.println("6.Wifi");
        System.out.println("7.Parking");
        System.out.println("8.Pool");
        System.out.println("=======================================");

        int entry = validityCheck(0,8);

        Scanner lineScanner = new Scanner(System.in);

        if (entry == 1)
        {
            System.out.println("Please enter new Name:");
            String name = lineScanner.nextLine();
            rental.setCharacteristic("name", name);
            System.out.println("Name changed");
            System.out.println("=======================================");
        }
        else if (entry == 2)
        {
            System.out.println("Please enter new Type:");
            String type = lineScanner.nextLine();
            rental.setCharacteristic("type", type);
            System.out.println("Type changed");
            System.out.println("=======================================");
        }
        else if (entry == 3)
        {
            System.out.println("Please enter new Location:");
            String location = lineScanner.nextLine();
            rental.setCharacteristic("location", location);
            System.out.println("Location changed");
            System.out.println("=======================================");
        }
        else if (entry==4)
        {
            System.out.println("Please enter new m^2");
            String m2 = lineScanner.nextLine();
            rental.setCharacteristic("m2",m2);
            System.out.println("m^2 changed");
            System.out.println("=======================================");
        }
        else if (entry == 5)
        {
            System.out.println("Please enter new price:");
            String price = lineScanner.nextLine();
            rental.setCharacteristic("price", price);
            System.out.println("Price changed");
            System.out.println("=======================================");
        }
        else if (entry == 6)
        {
            System.out.println("Does your Rental Provide Wifi?:");
            boolCharacteristicEntry(rental,"wifi");
            System.out.println("Wifi changed");
            System.out.println("=======================================");
        }
        else if (entry == 7)
        {
            System.out.println("Does your Rental Provide Parking?:");
            boolCharacteristicEntry(rental,"parking");
            System.out.println("Parking changed");
            System.out.println("=======================================");
        }
        else if (entry == 8)
        {
            System.out.println("Does your Rental Provide Pool?:");
            boolCharacteristicEntry(rental,"pool");
            System.out.println("Pool changed");
            System.out.println("=======================================");
        }

        rental.updateCharacteristics();
    }

    /**
     * Εκτυπώνει τα στοιχεία ενός καταλύματος στο terminal
     *
     * @param rental to κατάλυμα προς εκτύπωση
     */
    static void showRental(Rental rental)
    {
        System.out.println("=======================================");
        System.out.println("Id: " + rental.getId());
        System.out.println("Name: " + rental.getCharacteristic("name"));
        System.out.println("Type: " + rental.getCharacteristic("type"));
        System.out.println("Location: " + rental.getCharacteristic("location"));
        System.out.println("m^2 :" + rental.getCharacteristic("m2"));
        System.out.println("Per Day Price: " + rental.getCharacteristic("price"));
        System.out.println("Wifi: " + rental.getCharacteristic("wifi"));
        System.out.println("Parking :" +  rental.getCharacteristic("parking"));
        System.out.println("Pool :" + rental.getCharacteristic("pool"));
        System.out.println("=======================================");
    }

    /**
     * Διαβάζει μία ημερομηνία από το terminal
     *
     * @return την ημερομηνία
     */
    static Date inputDate()
    {
        System.out.println("Year:");
        int year = validityCheck(2021);
        System.out.println("Month:");
        int month = validityCheck(1, 12);
        System.out.println("Day:");
        int day = validityCheck(1, 30);

        Date date = new Date(day, month, year);
        return date;
    }

    /**
     * Διαβάζει δυαδικά χαρακτηριστικά καταλυμάτων (πχ wifi, ναι ή οχι)
     * @param rental το κατάλυμα του οποίου το χαρακτηριστικό διαβάζουμε
     * @param type το χαρακτηριστικό που θα διαβάσουμε
     */
    static void boolCharacteristicEntry(Rental rental, String type)
    {
        System.out.println("1.Yes");
        System.out.println("2.No");
        int entry = validityCheck(1,2);
        if(entry==1)
        {
            rental.addCharacteristic(type,"yes");
        }
        else
        {
            rental.addCharacteristic(type,"no");
        }
    }

    /**
     * Συγκεκριμένος τύπος φίλτρου για αριθμητικά φάσματα
     * @param ids τα ids που είναι διαθέσιμα πριν το φιλτράρισμα
     * @param type ο τύπος χαρακτηριστικού που φιλτράρουμε
     * @return τα ids που είναι διαθέσιμα μετά το φιλτράρισμα
     */
    static HashSet<String> filterIntRange(HashSet<String> ids, String type)
    {
        System.out.println("=======================================");
        System.out.println("Minimum:");
        int min = validityCheck(0);
        System.out.println("Maximum:");
        int max = validityCheck(min);
        System.out.println("=======================================");

        HashSet<String> toKeep = new HashSet<>();

        for (int i = min; i <= max; i++)
        {
            Integer value = i;
            HashSet<String> tIds = Rental.strongFilter(ids,type,value.toString());

            toKeep.addAll(tIds);
        }

        ids.retainAll(toKeep);
        return ids;
    }

    /**
     * Φιλτράρει τα αποτελέσματα της αναζήτησης με διάφορα κριτήρια
     * @param ids τα ids που είναι διαθέσιμα πριν το φιλτράρισμα
     * @return τα ids που είναι διαθέσιμα μετά το φιλτράρισμα
     */
    static HashSet<String> addFilters(HashSet<String> ids)
    {
        System.out.println("=======================================");
        System.out.println("Specify Rental Type? (Hotel,bnb,etc.):");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("=======================================");

        int entry = validityCheck(1,2);

        if (entry == 1)
        {
            System.out.println("What type of rental would you like to see?");
            Scanner lineScanner2 = new Scanner(System.in);
            String type = lineScanner2.nextLine();

            ids = Rental.filter(ids,"type",type);
        }

        System.out.println("=======================================");
        System.out.println("Specify Location?");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("=======================================");

        entry = validityCheck(1,2);

        if (entry == 1)
        {
            System.out.println("Where are you going?");
            Scanner lineScan = new Scanner(System.in);
            String location = lineScan.nextLine();

            ids = Rental.filter(ids,"location",location);
        }

        System.out.println("=======================================");
        System.out.println("Specify Square Meters ?:");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("=======================================");

        entry = validityCheck(1,2);

        if (entry == 1)
        {
            ids = filterIntRange(ids,"m2");
        }

        System.out.println("=======================================");
        System.out.println("Specify Price Range ?:");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("=======================================");

        entry = validityCheck(1,2);

        if (entry == 1)
        {
            ids = filterIntRange(ids,"price");
        }

        System.out.println("=======================================");
        System.out.println("WiFi ?:");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("3.Don't Care");
        System.out.println("=======================================");

        entry = validityCheck(1,3);

        if (entry == 1)
        {
            ids = Rental.strongFilter(ids,"wifi","yes");
        }
        else if (entry == 2)
        {
            ids = Rental.strongFilter(ids,"wifi","no");
        }

        System.out.println("=======================================");
        System.out.println("Parking ?:");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("3.Don't Care");
        System.out.println("=======================================");

        entry = validityCheck(1,3);

        if (entry == 1)
        {
            ids = Rental.strongFilter(ids,"parking","yes");
        }
        else if (entry == 2)
        {
            ids = Rental.strongFilter(ids,"parking","no");
        }

        System.out.println("=======================================");
        System.out.println("Pool ?:");
        System.out.println("1.Yes");
        System.out.println("2.No");
        System.out.println("3.Don't Care");
        System.out.println("=======================================");

        entry = validityCheck(1,3);

        if (entry == 1)
        {
            ids = Rental.strongFilter(ids,"pool","yes");
        }
        else if (entry == 2)
        {
            ids = Rental.strongFilter(ids,"pool","no");
        }
        return ids;

    }
}
