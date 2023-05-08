import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * <p>
 * Αυτή η κλάση λειτουργεί ως "γέφυρα" μεταξύ της μνήμης του υπολογιστή και της μνήμης του προγράμματος.
 * Σε αφαιρετικό επίπεδο είναι ένας πίνακας με τρεις στήλες και "άπειρες" γραμμές.
 * Η πρώτη στήλη είναι η "κατηγορία", η δεύτερη η "υποκατηγορία" και η τρίτη η "τιμή"
 * Ο πίνακας αυτός αποθηκεύεται μόνιμα (δηλαδή τα δεδομένα του παραμένουν ακόμα και όταν κλείσει το πρόγραμμα).
 * Τα δεδομένα είναι ταξινομημένα αλφαβητικά σε υποσύνολα ανάλογα με την κατηγορία και την υποκατηγορία στην οποία ανήκουν.
 * Ιδανικά θα θέλαμε έναν πιο εξελιγμένο τρόπο για την αποθήκευση αυτών των στοιχείων (πχ SQL) αλλά αυτή κλάση εξυπηρετεί τις ανάγκες μας
 * και είναι πιό απλή.
 * Η κλάση περιέχει τις απαραίτητες μεθόδους για επεξεργασία και προβολή των δεδομένων που αποθηκεύονται στη βάση μας.
 * </p>
 */

public class Database
{

    /**
     * Ένας φάκελος .txt που αποθηκεύεται εκτός της μνήμης του προγράμματος.
     */
    private File dataFile;
    /**
     * Μία ArrayList που μέσα της αποθηκεύει ArrayLists με τα δεδομένα μας (κάθε γραμμή της βάσης μας αποθηκεύεται ως ArrayLists τριών Strings).
     */
    private ArrayList<ArrayList<String>> database;

    /**
     * Ο κονστράκτορας που είτε δημιουργεί καινούργιο φάκελο με το όνομα που του δίνουμε
     * είτε ανοίγει ένα φάκελο που ηδη υπάρχει στο project directory και τον φορτίζει στη
     * μνήμη του προγράμματος.
     * @param filename το όνομα ενός φακέλου .txt (καινούργιου ή υπάρχοντος)
     */
    public Database(String filename) throws IOException
    {
        if(!filename.contains(".txt"))
        {
            System.out.println("TRIED TO CREATE FILE THAT ISN'T .txt");
            System.out.println("DATABASE INITIALIZATION FAILED");
            return;
        }

        this.database = new ArrayList<>();
        this.dataFile = new File(filename);

        if (!this.dataFile.exists()) //see if the file already exists
        {
            this.dataFile.createNewFile();//if it doesn't, make a new one
        }
        else
        {
            this.load(); //if it does, load its data
        }

    }


//Holistic methods (work with all the data in the database)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Αυτή η μέθοδος φορτίζει τα δεδομένα από το φάκελο στη μνήμη προγράμματος.
     * (dataFile -> database)
     */
    private void load() throws FileNotFoundException
    {
        //Declarations and busywork
        Scanner fileScanner = new Scanner(dataFile);
        int lineCount = this.getFileLines();


        for (int j = 0; j <lineCount ; j++)   //For every line
        {
            ArrayList<String> dataLine = new ArrayList<>(); //create a new ArrayList to add to the database
            String currentLine = fileScanner.nextLine(); //get the respective line from the dataFile
            Scanner lineScanner = new Scanner(currentLine); //create a Scanner for that line (that will "die" at the end of each loop)

            //get the first two words of the line
            String firstWord = lineScanner.next();
            String secondWord = lineScanner.next();
            //get the rest of the line (Last element of the list can be more than one word)
            String thirdWord = getRestOfLine(lineScanner);

            lineScanner.close();

            //add them to the dataLine
            dataLine.add(firstWord);
            dataLine.add(secondWord);
            dataLine.add(thirdWord);
            //add dataLine to database
            database.add(dataLine);
        }
        fileScanner.close();

    }



    /**
     * Αυτή η μέθοδος ενημερώνει το dataFile με τις αλλαγές που έγιναν κατά την εκτέλεση του προγράμματος.
     * (database -> dataFile)
     * ΠΡΕΠΕΙ ΝΑ ΚΛΑΛΕΙΤΑΙ ΣΤΟ ΤΕΛΟΣ ΚΑΘΕ ΠΡΟΓΡΑΜΜΑΤΟΣ ΓΙΑ ΟΛΑ ΤΑ ΑΝΤΙΚΕΙΜΕΝΑ ΤΗΣ ΚΛΑΣΗΣ!!!
     */
    public void update() throws IOException
    {
        //Busywork
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile));

        for (int i = 0; i < database.size(); i++) // for every entry in the database
        {
            if (i != 0) //make sure the dataFile doesn't start with an empty line
                writer.newLine();

            for (int j = 0; j < database.get(i).size(); j++)//for every entry in each line
            {
                writer.write(database.get(i).get(j)); //write the contents to the file

                if(j!=database.get(i).size()) //make sure that the line doesn't end with an empty space (IT DOES BUT IT DOESN'T MATTER)
                    writer.write(" ");
            }
        }
        writer.close();
    }



    /**
     * Εκτυπώνει τα δεδομένα της βάσης στη γραμμή εντολών.
     * (για χρήση από διαχειριστές)
     */
    public void show()
    {
        for(int i=0;i<database.size();i++)
        {
            StringBuilder sline = new StringBuilder();
            sline.append("|");
            for (int j = 0; j < 3; j++)
            {
                if(j!=0)
                    sline.append("|");
                sline.append(database.get(i).get(j));
            }
            sline.append("|");
            System.out.println(sline);
        }
    }

//Single Data Methods (work for single entries in the database)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Επιστρέφει ενα συγκεκριμένο δεδομένο
     * @param targetColumn η στήλη στην οποία κοιτάμε
     * @param index η γραμμή στην οποία κοιτάμε
     * @return Το δεδομένο που ψάχναμε
     */
    public String get(int targetColumn,int index)
    {
        return database.get(index).get(targetColumn);
    }


    /**
     * Εισάγει καινούργια δεδομένα στη βάση και κανονίζει να εισάγονται στη σωστή αλφαβητική τους σειρά.
     * Αν τα δεδομένα της εισόδου υπάρχουν ήδη στη βάση τότε τα ξαναβάζει και δεν τα αντικαθιστά.
     * @param s1 String με ΜΙΑ ΛΕΞΗ
     * @param s2 String με ΜΙΑ ΛΕΞΗ
     * @param s3 String με ΜΙα Ή ΠΟΛΛΕΣ ΛΈΞΕΙΣ
     * @return <p>-1 Αν δεν είναι έγκυρες οι παράμετροι</p>
     *         <p>Διαφορετικά τη θέση που μπήκε η είσοδος</p>
     */
    public int add(String s1,String s2,String s3)
    {
        //Validity checks
        if(s1.contains(" "))
        {
            System.out.println("ADDING " + s1 + " FAILED BECAUSE s1 MUST BE A SINGLE WORD");
            return -1;
        }
        if(s2.contains(" "))
        {
            System.out.println("ADDING " + s2 + " FAILED BECAUSE s2 MUST BE A SINGLE WORD");
            return -2;
        }

        //convert the string to ArrayList, so you can add them to the database
        ArrayList<String> dataLine = new ArrayList<>();
        dataLine.add(s1);
        dataLine.add(s2);
        dataLine.add(s3);


        //find the appropriate position
        int search1 = search(s1);

        if(database.isEmpty())
        {
            database.add(dataLine);
            return 0;
        }
        else
        {
            if (search1 != -1) //the category already exists
            {
                int search2 = search(s1, s2);

                if (search2 != -1) //the subcategory already exists
                {
                    int search3 = search(s1, s2, s3, SearchType.last);

                    if (search3 != -1) //this entire entry already exists
                    {
                        database.add(search3, dataLine); //simply duplicate it
                        return search3;
                    } else //there are other entries in this subcategory already
                    {
                        int from = search(s1, s2, SearchType.first);
                        int to = search(s1, s2, SearchType.last);

                        int place = findPlace(s3, from, to, 2); //find where to put it alphabetically

                        database.add(place, dataLine);
                        return place;
                    }
                } else // this subcategory doesn't exist
                {
                    int from = search(s1, SearchType.first);
                    int to = search(s1, SearchType.last);

                    int place = findPlace(s2, from, to, 1);//find where to put it alphabetically
                    database.add(place, dataLine);
                    return place;
                }
            } else //this category doesn't exist
            {
                int from = 0;
                int to = database.size()-1;

                int place = findPlace(s1, from, to, 0);//find where to put it alphabetically
                database.add(place, dataLine);
                return place;
            }
        }
    }

    /**
     * Αντικαθιστά μία είσοδο με μία άλλη.
     * Διαγράφει την παλιά είσοδο και προσθέτει την καινούργια.
     * ΠΡΟΣΟΧΗ : αν υπάρχουν πολλές είσοδοι με τη δοθείσα τιμή, μόνο μία θα αντικατασταθεί.
     * @param old1 η κατηγορία της εισόδου που θα αλλάξει
     * @param old2 η υποκατηγορία της εισόδου που θα αλλάξει
     * @param old3 η τιμή της εισόδου που θα αλλάξει
     * @param new1 η νέα κατηγορία της εισόδου
     * @param new2 η νέα υποκατηγορία της εισόδου
     * @param new3 η νέα τιμή της εισόδου
     * @return <p>-1 Αν δεν υπάρχει η δοθείσα είσοδος</p>
     *         <p>Διαφορετικά τη θέση της βάσης που εισάχθηκε η νέα είσοδος</p>
     */
    public int replace(String old1,String old2,String old3, String new1,String new2, String new3)
    {
        //busywork
        int search = search(old1,old2,old3);

        if(search == -1)
        {
            return -1;//not found
        }
        else
        {
            database.remove(search);

            return this.add(new1,new2,new3);//return the index
        }
    }


    /**
     * Διαγράφει μία είσοδο απο τη βάση.
     * Αν υπάρχουν πολλές είσοδοι με αυτά τα δεδομένα, διαγράφει μόνο μία
     * @param s1 η κατηγορία του δεδομένου
     * @param s2 η υποκατηγορία του δεδομένου
     * @param s3 η τιμή του δεδομένου
     * @return <p>-1 αν το δεδομένο δεν υπάρχει στη βάση</p>
     *         <p>Διαφορετικά τη θέση του δεδομένο</p>
     */
    public int remove(String s1,String s2,String s3)
    {
        //search for the entry
        int search = this.search(s1,s2,s3);
        if (search == -1)
        {
            return -1;//not found
        }
        else
        {
            database.remove(search);
            return search;//return index
        }
    }

    /**
     * Διαγράφει μία είσοδο με βάση τη θέση της
     * @param index η θέση της εισόδου
     */
    public void remove(int index)
    {
        database.remove(index);
    }

//Search Methods (complex and simplified)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Χαμηλού επιπέδου μέθοδος αναζήτησης στην οποία στηρίζονται άλλες μέθοδοι.
     * Βρίσκει ένα String σε μία από τις στήλες με τα δεδομένα.
     * @param targetColumn Η στήλη στην οποία γίνεται η αναζήτηση (καταμέτρηση ξεκινά απο το μηδέν)
     * @param content Το κλειδί της αναζήτησης
     * @param searchType <p>SearchType.first για να επιστρέψει την πρώτη εμφάνιση του κλειδιού</p>
     *                   <p>SearchType.last για να επιστρέψει την πρώτη εμφάνιση του κλειδιού</p>
     *                   <p>defaults to : SearchType.any για να επιστρέψει οποιαδήποτε εμφάνιση του κλειδιού</p>
     * @param from αρχή της αναζήτησης (defaults to : 0)
     * @param to τέλος της αναζήτησης (defaults to : database.size()-1)
     * @return <p>-1 αν δε βρεθεί το κλειδί</p>
     *         <p>τη θέση που βρέθηκε το κλειδί</p>
     */
    public int searchInColumn(int targetColumn,String content,SearchType searchType,int from,int to)
    {
        String[] column = columnToArray(targetColumn);

        if (column.length == 0)
        {
            return -1;
        }

        if(from == to)
        {
            if (column[from].equals(content))
            {
                return from;
            }
            return -1;
        }

        int search = Arrays.binarySearch(column,from,to+1,content);

        if (search < 0)
        {

            return -1;
        }

        if(searchType==SearchType.first)
        {
            for (int i = from; i <=search ; i++)
            {
                if (column[i].equals(content))
                {
                    return i;
                }
            }

        }
        else if(searchType==SearchType.last)
        {
            for (int i = to; i >=search ; i--)
            {
                if (column[i].equals(content))
                {
                    return i;
                }
            }
        }

        return search;
    }
    /**
     * @see #searchInColumn(int, String, SearchType, int, int)
     */
    public int searchInColumn(int targetColumn,String content,int from,int to) //no specific search type
    {
        return searchInColumn(targetColumn,content,SearchType.any,from,to);
    }
    /**
     * @see #searchInColumn(int, String, SearchType, int, int)
     */
    public int searchInColumn(int targetColumn,String content,SearchType searchType)//no specific range
    {
        return searchInColumn(targetColumn,content,searchType,0, database.size()-1);
    }
    /**
     * @see #searchInColumn(int, String, SearchType, int, int)
     */
    public int searchInColumn(int targetColumn,String content) //no range or search type
    {
        return searchInColumn(targetColumn,content,SearchType.any,0, database.size()-1);
    }


    /**
     * Μέθοδος αναζήτησης υψηλού επιπέδου, η οποία αναζητά στην κατάλληλη στήλη ανάλογα με τον αριθμό των εισαχθέντων
     * στοιχείων (πχ. Alice, password ψάχνει στη στήλη 1)
     * @param s1 Η κατηγορία του κλειδιού αναζήτησης
     * @param s2 Η υποκατηγορία του κλειδιού αναζήτησης
     * @param s3 Η τιμή του κλειδιού αναζήτησης
     * @param searchType <p>SearchType.first για να επιστρέψει την πρώτη εμφάνιση του κλειδιού</p>
     *                   <p>SearchType.last για να επιστρέψει την πρώτη εμφάνιση του κλειδιού</p>
     *                   <p>defaults to : SearchType.any για να επιστρέψει οποιαδήποτε εμφάνιση του κλειδιού</p>
     * @return <p>-1 αν δεν βρεθεί το κλειδί</p>
     *         <p>τη θέση στην οποία βρέθηκε το κλειδί</p>
     */
    public int search(String s1,String s2,String s3,SearchType searchType)
    {
        int search1 = searchInColumn(0,s1);
        if(search1 != -1)
        {

            int from1 = searchInColumn(0,s1,SearchType.first);
            int to1 = searchInColumn(0,s1,SearchType.last);

            int search2 = searchInColumn(1,s2,from1,to1);

            if (search2 != -1)
            {

                int from2 = searchInColumn(1,s2,SearchType.first,from1,to1);
                int to2 = searchInColumn(1,s2,SearchType.last,from1,to1);

                int search3 = searchInColumn(2,s3,from2,to2);

                return search3;
            }
            else return -1;
        }
        else return -1;
    }
    /**
     * @see #search(String, String, String, SearchType)
     */
    public int search(String s1,String s2,String s3)
    {
        return search(s1, s2, s3, SearchType.any);
    }
    /**
     * @see #search(String, String, String, SearchType)
     */
    public int search(String s1,String s2,SearchType searchType)
    {
        int search1 = searchInColumn(0,s1);
        if(search1 != -1)
        {
            int from1 = searchInColumn(0,s1,SearchType.first);
            int to1 = searchInColumn(0,s1,SearchType.last);

            int search2 = searchInColumn(1,s2,searchType,from1,to1);

            return search2;
        }
        else return -1;
    }
    /**
     * @see #search(String, String, String, SearchType)
     */
    public int search(String s1,String s2)
    {
        return search(s1,s2,SearchType.any);
    }
    /**
     * @see #search(String, String, String, SearchType)
     */
    public int search(String s1,SearchType searchType)
    {
        int search1 = searchInColumn(0,s1,searchType);
        return search1;
    }
    /**
     * @see #search(String, String, String, SearchType)
     */
    public int search(String s1)
    {
        return search(s1,SearchType.any);
    }


//Helper methods (Help with other methods and smaller stuff)
//----------------------------------------------------------------------------------------------------------------------

    /**
     * @return τον αριθμό των σειρών στο φάκελο dataFile
     */
    private int getFileLines() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(dataFile);
        String tLine;

        int counter = 0;

        while (scanner.hasNextLine())
        {
            tLine=scanner.nextLine();
            counter++;
        }

        scanner.close();
        return counter;
    }

    /**
     * Βοηθά στο διάβασμα των γραμμών για την load()
     * @param lineScanner πρέπει να έχει αρχικοποιηθεί ήδη (στην load())
     * @return String με την υπόλοιπη γραμμή που διαβάζει ο lineScanner
     */
    private String getRestOfLine(Scanner lineScanner)
    {
        StringBuilder restOfLine = new StringBuilder();
        String word;

        while (lineScanner.hasNext())
        {
            word = lineScanner.next();
            restOfLine.append(word);
            restOfLine.append(" ");
        }
        restOfLine.deleteCharAt(restOfLine.length()-1);

        return restOfLine.toString();
    }

    /**
     * Μετατρέπει μία στήλη της βάσης σε Πίνακα για την αναζήτηση
     * @param targetColumn η στήλη που θέλουμε να μετατρέψουμε σε Πίνακα (καταμέτρηση ξεκινά απο το μηδέν)
     * @return String[] ο Πίνακας στον οποίο μετατράπηκε η στήλη
     */
    private String[] columnToArray(int targetColumn)
    {
        String[] column = new String[this.database.size()];

        for (int i = 0; i < column.length; i++)
        {
            column[i] = this.database.get(i).get(targetColumn);
        }

        return column;
    }

    /**
     * @return τον αριθμό των εισόδων στη βάση
     */
    public int getSize()
    {
        return database.size();
    }

    /**
     * Βρίσκει τη σωστή θέση για να εισαχθεί μία γραμμή σε αλφαβητική σειρά
     * @param content η λέξη που θέλουμε να εισάγουμε
     * @param from η αρχή της αναζήτησης
     * @param to το τέλος της αναζήτησης
     * @param in η στήλη την οποία ελέγχουμε (καταμέτρηση ξεκινά απο το μηδέν)
     * @return τη θέση που πρέπει να εισαχθεί η λέξη
     */
    private int findPlace(String content,int from,int to,int in)
    {
        String[] column = columnToArray(in);
        int i;

        for (i = from; i <= to; i++)
        {

            if(column[i].compareTo(content) > 0)
            {
                return i ;
            }
        }
        return i;
    }

    /**
     * @return την πρώτη στήλη (0) σε μορφή Set για χρήση με for each εκτός της κλάσης
     */
    public HashSet<String> getCategorySet()
    {
        HashSet<String> categories = new HashSet<>();
        for (int i = 0; i < database.size(); i++)
        {
            String name = this.get(0,i);
            categories.add(name);
        }
        return categories;
    }
//----------------------------------------------------------------------------------------------------------------------

}