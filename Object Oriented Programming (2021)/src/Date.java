import java.io.IOException;
import java.util.*;

/**
 * Βοηθητική κλάση που χειρίζεται τις ημερομηνίες κράτησης στο πρόγραμμα.
 * Αποτελείται από μία βάση δεδομένων για την αποθήκευση των κρατήσεων, αλλά και απο αντικείμενα Date
 * που δημιουργήθηκαν για πιο εύκολη σύγκριση χρονικών περιόδων
 */
public class Date
{
    /**
     * Η βάση δεδομένων που αποθηκεύει τις ημερομηνίες κρατήσεων
     */
    public static Database dateabase;



    public int year;
    public int month;
    public int day;  //Θεωρούμε πως κάθε μήνας έχει 30 ημέρες

//Basic Methods
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Αυτός ο constructor δημιουργεί ημερομηνίες με συγκεκριμένες τιμές για το έτος το μήνα και τη μέρα
      * @param day
     * @param month
     * @param year
     */
    public Date(int day, int month, int year)
    {
        if (day < 0 || day > 30)
        {
            System.out.println("Day must be between 1 and 30");
            return;
        }
        if (month < 0 || month > 12)
        {
            System.out.println("Month must be between 1 and 12");
            return;
        }
        if (year < 2021)
        {
            System.out.println("Year must be after 2021");
            return;
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     *Αυτός ο constructor δημιουργεί ημερομηνίες απο String το οποίο διαβάζει και μεταφράζει
     *(Αρκεί το String αυτό να δημιουργήθηκε από την Date.toString())
     */
    public Date(String dateString)
    {
        Scanner parser = new Scanner(dateString);

        int day = parser.nextInt();
        int month = parser.nextInt();
        int year = parser.nextInt();

        parser.close();

        if (day < 0 || day > 30)
        {
            System.out.println("Day must be between 1 and 30");
            return;
        }
        if (month < 0 || month > 12)
        {
            System.out.println("Month must be between 1 and 12");
            return;
        }
        if (year < 2021)
        {
            System.out.println("Year must be after 2021");
            return;
        }

        this.day = day;
        this.month = month;
        this.year = year;


    }

    /**
     * Προσθέτει μία κράτηση στο database
     * @param id το id του καταλύματος που θα κρατηθεί
     * @param user το username του χρήστη που κάνει την κράτηση
     * @param date η ημερομηνία της κράτησης
     * @return τη θέση του database που μπήκε η ημερομηνία
     */
    public static int addDate(String id, String user, Date date)
    {
        return dateabase.add(id, user, date.toString());
    }

    /**
     * Διαγράφει μία κράτηση από το database
     * @param id το id του καταλύματος
     * @param user το username του χρήστη που είχε κάνει την κράτηση
     * @param date η ημερομηνία της κράτησης
     * @return τη θέση του database από όπου διαγράφτηκε η ημερομηνία
     */
    public static int removeDate(String id, String user, Date date)
    {
        return dateabase.remove(id, user, date.toString());
    }

//Comparative Methods (Methods that handle two dates and the ones in between)
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Συγκρίνει δύο Date και μας λέει αν είναι ίδια ή όχι
     */
    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Date))
        {
            return false;
        }

        if (other == this)
        {
            return true;
        }

        if (this.day == ((Date) other).day && this.month == ((Date) other).month && this.year == ((Date) other).year)
        {
            return true;
        } else
            return false;
    }

    /**
     * Συγκρίνει δύο ημερομηνίες
     *
     * @param date1
     * @param date2
     * @return <p>1 αν η πρώτη είναι μετά από τη δεύτερη</p>
     * <p>-1 αν η δεύτερη είναι μετά από την πρώτη</p>
     * <p>0 αν είναι η ίδια ημερομηνία</p>
     */
    public static int compare(Date date1, Date date2)
    {

        if (date1.equals(date2))
        {
            return 0;
        }

        if (date1.year > date2.year)
        {
            return 1;
        } else if (date1.year < date2.year)
        {
            return -1;
        } else
        {
            if (date1.month > date2.month)
            {
                return 1;
            } else if (date1.month < date2.month)
            {
                return -1;
            } else
            {
                if (date1.day > date2.day)
                {
                    return 1;
                } else
                {
                    return -1;
                }
            }
        }
    }

    /**
     * @return Ένα ArrayList με όλες τις ημερομηνίες ανάμεσα στην πρώτη και τη δεύτερη
     */
    public static ArrayList<Date> getBetween(Date date1, Date date2)
    {
        ArrayList<Date> betweens = new ArrayList<>();

        if (compare(date1, date2) == 0)
            return null;
        if (compare(date1, date2) == 1)
        {
            Date temp = new Date(date1.day, date1.month, date1.year);
            date1 = date2;
            date2 = temp;
        }

        int y, m, d;


        for (y = date1.year; y <= date2.year; y++) //start at the first year and finish in the last
        {
            int startMonth = 1;
            int endMonth = 12;

            if (y == date1.year) //if this is the first loop we should only add the months after the first date
            {
                startMonth = date1.month;
            }

            if (y == date2.year)//if this is the last loop we should only add the months before the last date
            {
                endMonth = date2.month;
            }
            for (m = startMonth; m <= endMonth; m++)
            {
                int startDay = 1;
                int endDay = 30;

                if (y == date1.year && m == date1.month) //if this is the first loop we should only add the dates after the first date
                {
                    startDay = date1.day + 1;
                }

                if (y == date2.year && m == date2.month) //if this is the last loop we should only add the dates before the last date
                {
                    endDay = date2.day - 1;
                }

                for (d = startDay; d <= endDay; d++)
                {
                    betweens.add(new Date(d, m, y));
                }
            }
        }

        return betweens;
    }

    /**
     * @return Ένα HashSet με τα ids όλων των καταλυμάτων που δεν είναι κρατημένα σε αυτό το χρονικό διάστημα
     */
    public static HashSet<String> getAvailableBetween(Date date1,Date date2)
    {
        HashSet<String> ids = new HashSet<>();
        ArrayList<Date> inbetweens = getBetween(date1,date2);


        for (String tId: dateabase.getCategorySet())
        {

            int from = dateabase.search(tId,SearchType.first);
            int to = dateabase.search(tId,SearchType.last);

            boolean available = true;

            for (int i = from ; i <= to ; i++)
            {
                Date tDate = new Date(dateabase.get(2,i));
                for (Date date:inbetweens)
                {
                    if (date.equals(tDate))
                    {
                        available = false;
                        break;
                    }
                }
            }

            if (available)
            {
                ids.add(tId);
            }

        }

        return ids;
    }

//Holistic Methods (work with all the data in the database)
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος στο εξωτερικό μέρος του dateabase (το αρχείο .txt)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται πάντα στο τέλος του προγράμματος
     */
    public static void updateDatabase() throws IOException
    {
        dateabase.update();
    }

    /**
     * Δημιουργεί καινούργιο database με το όνομα που του δίνεται για τις ημερομηνίες κράτησης ΟΛΩΝ των καταλυμάτων
     * (και αυτόματα φορτίζει και τα δεδομένα της)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται στην αρχή κάθε προγράμματος
     *
     * @param filename Το όνομα του φακέλου που θα εμπεριέχει το dateabase
     */
    public static void initializeDatabase(String filename) throws IOException
    {
        dateabase = new Database(filename);
    }

//Helper methods (Help with other methods and smaller stuff)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Μετατρέπει μία ημερομηνία σε (δυσανάγνωστο) String για την μετάφραση απο και προς το database
     */
    @Override
    public String toString()
    {
        StringBuilder dateString = new StringBuilder();
        dateString.append(day);
        dateString.append(" ");
        dateString.append(month);
        dateString.append(" ");
        dateString.append(year);

        return dateString.toString();
    }

    /**
     * Όπως το toString() αλλά πιο ευανάγνωστο
     */
    public String toHumanString()
    {
        StringBuilder dateString = new StringBuilder();
        dateString.append(day);
        dateString.append("/");
        dateString.append(month);
        dateString.append("/");
        dateString.append(year);

        return dateString.toString();
    }

    /**
     * Γράφει την ημερομηνία σε ευανάγνωστη μορφή στο terminal
     */
    public void print()
    {
        System.out.println(this.day + "/" + this.month + "/" + this.year);
    }

    /**
     * @param dates ένα σύνολο από ημερομηνίες
     * @return την πρώτη (χρονολογικά) από τις ημερομηνίες που δόθηκαν
     */
    public static Date getFirst(HashSet<Date> dates)
    {
        Date min = new Date(12,12,3000);

        for (Date date:dates)
        {
            if(Date.compare(date,min) == -1)
            {
                min = date;
            }
        }
        return min;
    }

    /**
     * @param dates ένα σύνολο από ημερομηνίες
     * @return την τελευτάια (χρονολογικά) από τις ημερομηνίες που δόθηκαν
     */
    public static Date getLast(HashSet<Date> dates)
    {
        Date max = new Date(1,1,2021);

        for (Date date:dates)
        {
            if(Date.compare(date,max) == 1)
            {
                max = date;
            }
        }
        return max;
    }

    /**
     * @param id ενός καταλύματος
     * @param username ενός χρήστη
     * @return Όλες τις ημερομηνίες για τις οποίες ο χρήστης αυτός έχει κάνει κράτηση σε ένα κατάλυμα
     */
    public static HashSet<Date> getDatesFor(String id,String username)
    {
        HashSet<Date> dates = new HashSet<>();

        Date nullDate = new Date(1,1,2021);

        int from = dateabase.search(id,username,SearchType.first);
        int to = dateabase.search(id,username,SearchType.last);

        if (from == -1)
            return dates;

        for (int i = from;i<=to;i++)
        {
            Date tDate = new Date(dateabase.get(2,i));

            if(!tDate.equals(nullDate))
            {
                dates.add(tDate);
            }
        }

        return dates;
    }
}
