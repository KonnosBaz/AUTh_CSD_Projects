import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Αυτή η κλάση χειρίζεται τους χρήστες και τα δεδομένα τους, τόσο ατομικά, όσο και συνολικά
 */
public class User
{
    /**
     * Η βάση στην οποία αποθηκεύονται τα δεδομένα όλων των χρηστών
     */
    private static Database credDatabase;

    /**
     * Το ΜΟΝΑΔΙΚΟ αναγνωριστικό κάθε χρήστη
     */
    private String userName;
    /**
     * Ο πίνακας με τα στοιχεία κάθε χρήστη
     */
    private HashMap<String,String> credentials;

//Basic Methods (Constructor and getters setters)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Δημιουργεί ένα νέο χρήστη, και αν ο χρήστης υπάρχει ήδη στη βάση, φορτίζει τα δεδομένα του
     * @param userName το Αναγνωριστικό του χρήστη
     */
    public User(String userName)
    {
        this.userName=userName;
        this.credentials = new HashMap<>();

        if (!checkUsernameAvailability(userName))
        {
            this.loadCredentials();
        }
        else
        {
            this.create();
        }
    }

    /**
     * @return το αναγνωριστικό του χρήστη
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * Αλλάζει την τιμή ενός δεδομένου του χρήστη
     * @param type ο τύπος του δεδομένου που θα αλλάξει
     * @param value η νέα τιμή του δεδομένου που θα αλλάξει
     * @return <p>false αν δεν υπήρχε ο τύπος δεδομένου στα credentials</p>
     *         <p>true αν άλλαξε επιτυχώς</p>
     */
    public boolean setCredential(String type,String value)
    {
        if(!credentials.keySet().contains(type))
        {
            return false;
        }

        credentials.put(type,value);
        return true;
    }


    /**
     * Δημιουργεί ένα καινούργιο δεδομένο για το χρήστη
     * @param type ο τύπος του νέου δεδομένου
     * @param value η τιμή του νέου δεδομένου
     * @return <p>false αν υπήρχε ήδη ο τύπος δεδομένου στα credentials</p>
     *         <p>true αν προστέθηκε επιτυχώς</p>
     */
    public boolean addCredential(String type,String value)
    {
        if(credentials.keySet().contains(type))
        {
            return false;
        }

        credentials.put(type,value);
        return true;
    }

    /**
     * @param type ο τύπος του δεδομένου που θέλουμε
     * @return η τιμή του δεδομένου που θέλουμε
     */
    public String getCredential(String type)
    {
        return credentials.get(type);
    }

    /**
     * Διαγράφει ένα δεδομένο του χρήστη
     * @param type ο τύπος του δεδομένου που θα διαγραφεί
     * @return <p>false αν δεν υπήρχε ο τύπος δεδομένου στα credentials</p>
     *         <p>true αν διαγράφηκε επιτυχώς</p>
     */
    public boolean deleteCredential(String type)
    {
        if(!credentials.keySet().contains(type))
        {
            return false;
        }

        credentials.remove(type);
        return true;
    }


    /**
     * Μέθοδος ελέγχου για χρήση με δεδομένα των οποίων οι τιμές λειτουργούν σαν boolean (παίρνουν τιμες "yes" ή "no")
     * @param type ο τύπος δεδομένου που θέλουμε
     * @return <p>true η τιμή του δεδομένου είναι "yes"</p>
     *         <p>false διαφορετικά</p>
     */
    public boolean is(String type)
    {
        if(!has(type))
        {
            return false;
        }

        if(getCredential(type).equals("yes"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Μας λέει αν ένας χρήστης έχει έναν τύπο δεδομένου
     * @param type ο τύπος του δεδομένου
     * @return <p>true αν το δεδομένο υπάρχει για τον χρήστη</p>
     *          <p>false αν δεν υπάρχει</p>
     */
    public boolean has(String type)
    {
        return credentials.containsKey(type);
    }


//Database Methods (communication between single object and Database)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Φορτίζει τα δεδομένα ενός χρήστη απο το credDataBase στο credentials
     */
    private void loadCredentials()
    {
        int from = credDatabase.search(userName,SearchType.first);
        int to = credDatabase.search(userName,SearchType.last);

        for (int i = from; i <= to; i++)
        {
            credentials.put(credDatabase.get(1,i), credDatabase.get(2,i));
        }
    }

    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος, στο εσωτερικό μέρος της credDatabase (το ArrayList)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται πάντα όταν κάνει Logout ένας χρήστης
     */
    public void updateCredentials()
    {
        int from = credDatabase.search(userName,SearchType.first);
        int to = credDatabase.search(userName,SearchType.last);

        for (int i = from; i <= to; i++)
        {
            credDatabase.remove(i);
            i--;
            to--;
        }

        for (String cType:credentials.keySet())
        {
            credDatabase.add(userName,cType,credentials.get(cType));
        }
    }

//Holistic Methods (static methods that work with all rentals in the database)
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος στο εξωτερικό μέρος του credDatabase (το αρχείο .txt)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται πάντα στο τέλος του προγράμματος
     */
    public static void updateDatabase(User user) throws IOException
    {
        user.updateCredentials();
        credDatabase.update();
    }

    /**
     * Δημιουργεί καινούργιο database με το όνομα που του δίνεται για τα credentials ΟΛΩΝ των χρηστών
     * (και αυτόματα φορτίζει και τα δεδομένα της)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται στην αρχή κάθε προγράμματος
     * @param filename Το όνομα του φακέλου που θα εμπεριέχει το credDataBase
     */
    public static void initializeDatabase(String filename) throws IOException
    {
        credDatabase = new Database(filename);
    }



    /**
     * Βλέπει αν υπάρχει χρήστης με αυτό το όνομα στη βάση δεδομένων
     * @param userName το όνομα προς έλεγχο
     * @return<p>true αν είναι διαθέσιμο το όνομα</p>
     *        <p>false αν δεν είναι διαθέσιμο το όνομα</p>
     */
    public static boolean checkUsernameAvailability(String userName)
    {
        if (credDatabase.search(userName) == -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Βλέπει αν υπάρχουν άλλοι χρήστες με το ίδιο credential
     * Για χρήση με δεδομένα που δεν μπορούν να είναι ίδια για διαφορετικούς χρήστες (πχ email)
     * @param type o τύπος του δεδομένου
     * @param value η τιμή του δεδομένου
     * @return <p>true αν είναι διαθέσιμο</p>
     *         <p>false αν δεν είναι διαθέσιμο</p>
     */
    public static boolean checkCredentialAvailability(String type, String value)
    {

        HashSet<String> userNames = credDatabase.getCategorySet();

        for (String name:userNames)
        {
            if(credDatabase.search(name,type,value) != -1)
            {
                return false;
            }
        }
        return true;

    }



    /**
     * Μέθοδος αναζήτησης με κριτήριο κάποιο δεδομένο
     * @param type ο τύπος του δεδομένου
     * @param value η τιμή του δεδομένου
     * @return HashSet με τα usernames όλων των χρηστών που έχουν αυτά τα δεδομένα
     */
    public static HashSet<String> findAllWith(String type,String value)
    {
        HashSet<String> usernames = credDatabase.getCategorySet();

        HashSet<String> filteredNames = new HashSet<>();

        for (String name:usernames)
        {

            User user = new User(name);

            if(user.has(type))
            {
               if(user.getCredential(type).equals(value))
               {
                   filteredNames.add(user.getUserName());
               }
            }

        }

        return filteredNames;
    }

    /**
     * Μέθοδος αναζήτησης που βρίσκει τον χρήστη που έχει ένα συγκεκριμένο δεδομένο
     * (Για χρήση ΜΟΝΟ με δεδομένα που είναι μοναδικά για κάθε χρήστη (πχ email))
     * @param type ο τύπος του δεδομένου
     * @param value η τιμή του δεδομένου
     * @return το username του χρήστη που έχει το συγκεκριμένο δεδομένο
     */
    public static String findWith(String type,String value)
    {
        HashSet<String> usernames = credDatabase.getCategorySet();

        for (String name:usernames)
        {
            int search = credDatabase.search(name,type,value);
            if (search != -1)
            {
                return name;
            }
        }
        return "-1";
    }

    /**
     * Εκτυπώνει όλη τη βάση δεδομένων στο terminal
     */
    public static void showAll()
    {
        credDatabase.show();
    }


//Helper methods (Help with other methods and smaller stuff)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Αρχικοποιεί έναν χρήστη μέσα στη βάση δεδομένων <p>
     * (Είναι απαραίτητη η είσοδος ενός δεδομένου στο credDatabase όταν ένας χρήστης δεν υπάρχει ήδη μέσα της,
     * έτσι ώστε να λειτουργούν ομαλά οι άλλοι μέθοδοι.
     * Το τι θα βάλουμε δε μετράει) <p>
     * ΠΡΟΣΟΧΗ: Αυτή η μέθοδος πρέπει να καλείται κάθε φορά που δημιουργούμε νέο χρήστη (όχι αντικείμενο, αλλά χρήστη μέσα στο database)
     */
    private void create()
    {
        credDatabase.add(userName,"initializer","nothing");
    }

    /**
     * @return τα usernames όλων των χρηστών
     */
    public static HashSet<String> getAll()
    {
        return credDatabase.getCategorySet();
    }
}


