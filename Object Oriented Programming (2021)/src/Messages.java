import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Αυτή η κλάση χειρίζεται τα μηνύματα των χρηστών
 * Υλοποιείται με ένα database το οποίο έχει στις στήλες του (με αυτή τη σειρά)
 * Τον παραλήπτη, τον αποστολέα και το ίδιο το μνήμα
 */
public class Messages
{
    /**
     * ΝΕΑ ΠΡΟΣΘΗΚΗ ΣΤΗΝ ΚΛΑΣΗ
     * Χρησιμοποιείται μόνο για την εξαγωγή των μηνυμάτων από τη βάση δεδομένων
     */
    public static class Message
    {
        private String sender;
        private String body;

        public Message(String sender,String body)
        {
            this.sender=sender;
            this.body=body;
        }

        public String getBody()
        {
            return body;
        }

        public String getSender()
        {
            return sender;
        }
    }





    /**
     * Η βάση δεδομένων που αποθηκεύει τα μηνύματα
     */
    public static Database messDataBase;


    /**
     * Δημιουργεί καινούργιο database με το όνομα που του δίνεται για τα μηνύματα ΟΛΩΝ των χρηστών
     * (και αυτόματα φορτίζει και τα δεδομένα της)
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται ΠΑΝΤΑ στην αρχή του προγράμματος
     * @param filename Το όνομα του φακέλου που θα εμπεριέχει το messageDataBase
     */
    public static void initializeDatabase(String filename) throws IOException
    {
        messDataBase = new Database(filename);
    }

    /**
     * Φορτίζει τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος
     * ΠΡΟΣΟΧΗ: Πρέπει να καλείται ΠΑΝΤΑ στο τέλος του προγράμματος
     */
    public static void updateDatabase() throws IOException
    {
        messDataBase.update();
    }

    /**
     * Στέλνει (στην πραγματικότητα καταχωρεί) ένα μύνημα
     * @param from Αποστολέας
     * @param to Παραλήπτης
     * @param message Μήνυμα
     */
    public static int send(String from,String to,String message)
    {
        return messDataBase.add(to,from,message);
    }

    /**
     * Διαγράφει ένα μήνυμα (από τη βάση δεδομένων)
     * @param to Παραλήπτης
     * @param from Αποστολέας
     * @param message Μήνυμα
     * @return <p>-1  Αν το μήνυμα δεν βρέθηκε</p>
     *         <p>Διαφορετικά τη θέση της βάσης δεδομένων από την οποία διαγράφτηκε το μήνυμα</p>
     */
    public static int delete(String to,String from ,String message)
    {
        int index = messDataBase.remove(to,from,message);
        if(index == -1)
        {
            System.out.println("Message couldn't be deleted because it wasn't found");
        }
        return index;
    }

    /**
     * Εμφανίζει όλα τα μηνύματα ενός χρήστη και του επιτρέπει να τα διαγράψει
     * @param username ο χρήστης
     */
    public static void showMessages(String username)
    {
        Scanner input = new Scanner(System.in);
        String entry;

        int from = messDataBase.search(username,SearchType.first);
        int to = messDataBase.search(username,SearchType.last);

        if (from == -1)
        {
            System.out.println("No Messages");
            return;
        }

        for (int i = from; i <= to; i++)
        {
            String sender = messDataBase.get(1,i);
            String message = messDataBase.get(2,i);

            System.out.println("=======================================");
            System.out.println("From :" + sender);
            System.out.println("=======================================");
            System.out.println("Body :");
            System.out.println(message);
            System.out.println("=======================================");

            boolean flag = true;
            while(flag)
            {
                System.out.println("Would you like to delete this message?  (y/n)");
                System.out.println("=======================================");
                entry = input.next();

                if (entry.equals("y"))
                {
                    delete(username,sender,message);
                    flag = false;
                    i--;
                    to--;
                }
                else if (entry.equals("n"))
                {
                    flag = false;
                }
                else
                {
                    System.out.println("Invalid Input");
                    System.out.println("Please type 'y' or 'n'");
                    System.out.println("Would you like to delete this message?  (y/n)");
                    System.out.println("=======================================");
                }
            }
        }
    }

    public static ArrayList<Message> getMessages(String username)
    {
        ArrayList<Message> messages = new ArrayList<>();

        int from = messDataBase.search(username,SearchType.first);
        int to = messDataBase.search(username,SearchType.last);

        if (from == -1)
        {
            return null;
        }

        for (int i = from; i <= to; i++)
        {
            String sender = messDataBase.get(1,i);
            String body = messDataBase.get(2,i);

            Message message = new Message(sender,body);

            messages.add(message);
        }
        return messages;

    }

}
