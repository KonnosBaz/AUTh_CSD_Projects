import java.io.IOException;
import java.util.*;


/**
 * Αυτή η κλάση χειρίζεται τα καταλύματα και τα χαρακτηριστικά τους μέσα στο πρόγραμμα, καθώς και την αποθήκευση τους
 * στην κύρια μνήμη του υπολογιστή.
 * Εμπεριέχει μεθόδους για την προβολή και επεξεργασία των δεδομένων κάθε καταλύματος ξεχωριστά, αλλά και την αναζήτηση
 * στο σύνολο όλων των καταλυμάτων
 */
public class Rental
{
    /**
     * Η βάση στην οποία αποθηκεύονται τα δεδομένα όλων των καταλυμάτων.
     */
    private static Database rentalsDatabase;

    /**
     * Το ΜΟΝΑΔΙΚΟ αναγνωριστικό κάθε καταλύματος
     */
    private String id;
    /**
     * Ο πίνακας με τα στοιχεία κάθε καταλύματος
     */
    private HashMap<String,String> characteristics;
    


//Basic Methods
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Δημιουργεί ένα νέο κατάλυμα, και αν το κατάλυμα υπάρχει ήδη στη βάση, φορτίζει τα χαρακτηριστικά του
     * @param id το Αναγνωριστικό του καταλύματος
     */
    public Rental(String id)
    {
        this.id=id;
        this.characteristics = new HashMap<>();

        if (rentalsDatabase.search(id) != -1)
        {
            loadCharacteristics();
        }
        else
        {
            this.create();
        }

    }

    /**
     * @return Το αναγνωριστικό ενός Καταλύματος
     */
    public String getId()
    {
        return this.id;
    }

//Characteristics Methods (work with the Characteristics HashMap)
//----------------------------------------------------------------------------------------------------------------------


    /**
     * Αλλάζει την τιμή ενός χαρακτηριστικού του καταλύματος
     * @param type ο τύπος του χαρακτηριστικού που θα αλλάξει
     * @param value η νέα τιμή του χαρακτηριστικού που θα αλλάξει
     * @return <p>false αν δεν υπήρχε ο τύπος χαρακτηριστικού στα characteristics</p>
     *         <p>true αν άλλαξε επιτυχώς</p>
     */
    public boolean setCharacteristic(String type,String value)
    {
        if(!characteristics.keySet().contains(type))
        {
            return false;
        }

        characteristics.put(type,value);
        return true;
    }

    /**
     * Δημιουργεί ένα καινούργιο χαρακτηριστικού για το καταλύματος
     * @param type ο τύπος του νέου χαρακτηριστικού
     * @param value η τιμή του νέου χαρακτηριστικού
     * @return <p>false αν υπήρχε ήδη ο τύπος χαρακτηριστικού στα characteristics</p>
     *         <p>true αν προστέθηκε επιτυχώς</p>
     */
    public boolean addCharacteristic(String type,String value)
    {
        if(characteristics.keySet().contains(type))
        {
            return false;
        }

        characteristics.put(type,value);
        return true;
    }

    /**
     * Διαγράφει ένα χαρακτηριστικό του καταλύματος
     * @param type ο τύπος του χαρακτηριστικού που θα διαγραφεί
     * @return <p>false αν δεν υπήρχε ο τύπος χαρακτηριστικού στα characteristics</p>
     *         <p>true αν διαγράφηκε επιτυχώς</p>
     */
    public boolean deleteCharacteristic(String type)
    {
        if(!characteristics.keySet().contains(type))
        {
            return false;
        }

        characteristics.remove(type);
        return true;
    }

    /**
     * @param type ο τύπος του χαρακτηριστικό που θέλουμε
     * @return η τιμή του χαρακτηριστικού που θέλουμε
     */
    public String getCharacteristic(String type)
    {
        return characteristics.get(type);
    }

    /**
     * Μας λέει αν ένα κατάλυμα έχει έναν χαρακτηριστικό
     * @param type ο τύπος του χαρακτηριστικού
     * @return <p>true αν το έχει</p>
     *          <p>false αν δεν το έχει</p>
     */
    public boolean has(String type)
    {
        if (this.getCharacteristic(type).equals("yes"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Don't look at this one... it's a mistake
     * @param type
     * @return true if this rental has the type of Characteristic in the database
     */
    public boolean contains(String type)
    {
        if (this.getCharacteristic(type) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος, στο εσωτερικό μέρος της rentalsDatabase (το ArrayList)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται κάθε φορά που τελειώνουμε την επεξεργασία ή δημιουργία ενός καταλύματος
     */
    public void updateCharacteristics()
    {
        int from = rentalsDatabase.search(id,SearchType.first);
        int to = rentalsDatabase.search(id,SearchType.last);

        for (int i = from; i <= to; i++)
        {
            rentalsDatabase.remove(i);
            i--;
            to--;
        }

        for (String cType:characteristics.keySet())
        {
            rentalsDatabase.add(id,cType,characteristics.get(cType));
        }
    }

    /**
     * Φορτίζει τα χαρακτηριστικά ενός καταλύματος απο το rentalsDataBase στο characteristics
     */
    private void loadCharacteristics()
    {
        int from = rentalsDatabase.search(id,SearchType.first);
        int to = rentalsDatabase.search(id,SearchType.last);

        for (int i = from; i <= to; i++)
        {
            characteristics.put(rentalsDatabase.get(1,i), rentalsDatabase.get(2,i));
        }
    }


//Holistic Methods (static methods that work with all rentals in the database)
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος στο εξωτερικό μέρος του rentalsDatabase (το αρχείο .txt)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται πάντα στο τέλος του προγράμματος
     */
    public static void updateDatabase() throws IOException
    {
        rentalsDatabase.update();
    }

    /**
     * Δημιουργεί καινούργιο database με το όνομα που του δίνεται για τα characteristics ΟΛΩΝ των καταλυμάτων
     * (και αυτόματα φορτίζει και τα δεδομένα της)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται στην αρχή κάθε προγράμματος
     * (Δίνοντας διαφορετικά filenames μπορούμε να δημιουργήσουμε οποιονδήποτε αριθμό βάσεων δεδομένων θέλουμε)
     * @param filename Το όνομα του φακέλου που θα εμπεριέχει το rentalsDatabase
     */
    public static void initializeDatabase(String filename) throws IOException
    {
        rentalsDatabase = new Database(filename);
    }

    /**
     * Αυτή η μέθοδος φιλτράρει τα δοθέντα id καταλυμάτων με ένα συγκεκριμένο κριτήριο
     * (τιμή καταλύματος.contains(τιμή κριτηρίου) || τιμή κριτηρίου.contains(τιμή καταλύματος))
     * @param ids Αν δε δοθεί η μέθοδος φιλτράρει όλα τα id
     * @param type Ο τύπος του κριτηρίου
     * @param value Η τιμή του κριτηρίου
     * @return Το σύνολο των αναγνωριστικών των οποίων τα καταλύματα πληρούν το κριτήριο
     */
    public static HashSet<String> filter(HashSet<String> ids, String type, String value)
    {
        //In this version we have a HashSet of ids already so that we can apply more than one filter
        HashSet<String> filteredIds = new HashSet<>();

        for (String id:ids)
        {
            int search = rentalsDatabase.search(id,type);
            String curValue = rentalsDatabase.get(2,search);

            if (curValue.contains(value) || value.contains(curValue))
            {
                filteredIds.add(id);
            }
        }

        return filteredIds;
    }

    /**
     * @see #filter(HashSet, String, String)
     */
    public static HashSet<String> filter(String type, String value)
    {
        HashSet<String> ids = getAll();
        HashSet<String> filteredIds = new HashSet<>();

        for (String id:ids)
        {
            int search = rentalsDatabase.search(id,type);
            String curValue = rentalsDatabase.get(2,search);

            if (curValue.contains(value) || value.contains(curValue))
            {
                filteredIds.add(id);
            }
        }

        return filteredIds;
    }

    /**
     * Κάνει το ίδιο με τη filter αλλά βλέπει άν οι τιμές των καταλυμάτων είναι ακριβώς ίδιες
     * (Τιμή καταλύματος.equals(τιμή κριτηρίου))
     * @see #filter(HashSet, String, String)
     */
    public static HashSet<String> strongFilter(String type, String value)
    {
        HashSet<String> ids = getAll();
        HashSet<String> filteredIds = new HashSet<>();

        for (String id:ids)
        {
            int search = rentalsDatabase.search(id,type);
            String curValue = rentalsDatabase.get(2,search);

            if (curValue.equals(value))
            {
                filteredIds.add(id);
            }
        }

        return filteredIds;
    }

    /**
     * @see #strongFilter(HashSet, String, String)
     */
    public static HashSet<String> strongFilter(HashSet<String> ids, String type, String value)
    {
        //In this version we have a HashSet of ids already so that we can apply more than one filter
        HashSet<String> filteredIds = new HashSet<>();

        for (String id:ids)
        {
            int search = rentalsDatabase.search(id,type);
            String curValue = rentalsDatabase.get(2,search);

            if (curValue.equals(value))
            {
                filteredIds.add(id);
            }
        }

        return filteredIds;
    }

    /**
     * @return το σύνολο όλων των ids, όλων των καταλυμάτων
     */
    public static HashSet<String> getAll()
    {
        return rentalsDatabase.getCategorySet();
    }

    /**
     * Βλέπει αν ένα id υπάρχει ήδη στη βάση δεδομένων
     * @param id
     * @return true αν δεν υπάρχει,false αν υπάρχει
     */
    public static boolean checkIdAvailability(String id)
    {
        if(rentalsDatabase.search(id) == -1)
            return true;
        else
            return false;
    }

    /**
     * Εκτυπώνει όλη τη βάση δεδομένων στο terminal
     */
    public static void showAll()
    {
        rentalsDatabase.show();
    }

//Helper methods (Help with other methods and smaller stuff)
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return ένα αναγνωριστικό που δε βρίσκεται σε χρήση από άλλο κατάλυμα
     */
    public static String getAvailableID()
    {
        boolean flag = true;
        Random rand = new Random();
        String id = "";

        while (flag)
        {
            Integer idNum = rand.nextInt(100000,999999);
            id = idNum.toString();

            if(checkIdAvailability(id))
            {
                flag = false;
            }
        }

        return id;
    }

    /**
     * Αρχικοποιεί ένα rental στο database
     */
    private void create()
    {
        rentalsDatabase.add(id,"initializer","nothing");
    }

}
