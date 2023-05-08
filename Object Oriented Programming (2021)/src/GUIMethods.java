import java.io.IOException;
import java.util.HashSet;


public interface GUIMethods
{
    /**
     * tries to log into the system
     * @param username
     * @param password
     * @return true if login was successful, false otherwise
     */
    static boolean loginAttempt(String username, String password)
    {
        if (User.checkUsernameAvailability(username))
        {
            return false;
        }

        User user = new User(username);

        if(user.getCredential("password").equals(password))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Takes in the basic credentials of an account and puts them in the database
     * @param username
     * @param password
     * @param email
     * @return the new User
     */
    static User createAccount(String username,String password,String email)
    {
        User user = new User(username);

        user.addCredential("password", password);
        user.addCredential("email", email);
        user.addCredential("activated", "no");

        return user;

    }

    /**
     * self-explanatory, Extremely important
     * @throws IOException
     */
    static void updateDatabases() throws IOException
    {
        User.updateDatabase(GUI.user);
        Messages.updateDatabase();
        Rental.updateDatabase();
        Date.updateDatabase();
    }

    /**
     * self-explanatory
     * @return String Array with all the ids of the rentals
     */
    static String[] getAllRentals()
    {
        HashSet<String> rentals = Rental.getAll();
        return rentals.toArray(new String[0]);
    }

    /**
     * an attempt at streamlining the process of filtering Rentals, not very useful
     * Only has fields, each of which represents the characteristics of Rentals
     */
    public class Filters
    {
        private Date fromDate;
        private Date toDate;
        private String type;
        private String location;
        private int minPrice;
        private int maxPrice;
        private int minSize;
        private int maxSize;
        private boolean wifi;
        private boolean pool;
        private boolean parking;

        public Date getFromDate()
        {
            return fromDate;
        }

        public Date getToDate()
        {
            return toDate;
        }

        public String getType()
        {
            return type;
        }

        public String getLocation()
        {
            return location;
        }

        public int getMinPrice()
        {
            return minPrice;
        }

        public int getMaxPrice()
        {
            return maxPrice;
        }

        public int getMinSize()
        {
            return minSize;
        }

        public int getMaxSize()
        {
            return maxSize;
        }

        public boolean isWifi()
        {
            return wifi;
        }

        public boolean isPool()
        {
            return pool;
        }

        public boolean isParking()
        {
            return parking;
        }

        public void setFromDate(Date fromDate)
        {
            this.fromDate = fromDate;
        }

        public void setToDate(Date toDate)
        {
            this.toDate = toDate;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public void setLocation(String location)
        {
            this.location = location;
        }

        public void setMinPrice(int minPrice)
        {
            this.minPrice = minPrice;
        }

        public void setMaxPrice(int maxPrice)
        {
            this.maxPrice = maxPrice;
        }

        public void setMinSize(int minSize)
        {
            this.minSize = minSize;
        }

        public void setMaxSize(int maxSize)
        {
            this.maxSize = maxSize;
        }

        public void setWifi(boolean wifi)
        {
            this.wifi = wifi;
        }

        public void setPool(boolean pool)
        {
            this.pool = pool;
        }

        public void setParking(boolean parking)
        {
            this.parking = parking;
        }

        public void print()
        {
            this.getFromDate().print();
            this.getToDate().print();
            System.out.println(this.getType());
            System.out.println(this.getLocation());
            System.out.println(this.minPrice);
            System.out.println(this.maxPrice);
            System.out.println(this.minSize);
            System.out.println(this.maxSize);
            System.out.println(this.wifi);
            System.out.println(this.parking);
            System.out.println(this.pool);
        }
    }

    /**
     * Special method for filtering rentals based on numbers (needed because all data is stored as strings, also not very efficient...)
     * @param ids a pre-existing HashSet of ids to be filtered
     * @param type the type of characteristic we're filtering for
     * @param min the min value of that characteristic to pass the filter
     * @param max the max value of that characteristic to pass the filter
     * @return ids, but everything that didn't pass the filter got removed
     */
    static HashSet<String> filterIntRange(HashSet<String> ids, String type,int min,int max)
    {
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
     * Finds all the rentals that meet the criteria of a set of filters
     * @param filters
     * @return Array of Strings with the id's of all the rentals that passed "through" the filters
      */
    static String[] addFilters(Filters filters)
    {

        Date startDate = filters.getFromDate();
        Date endDate = filters.getToDate();


        HashSet<String> availableIds = Date.getAvailableBetween(startDate, endDate);


        if (filters.getType() != null)
        {
            availableIds = Rental.filter(availableIds,"type",filters.getType());
        }


        if (filters.getLocation() != null)
        {
            availableIds = Rental.filter(availableIds,"location",filters.getLocation());
        }


        availableIds = filterIntRange(availableIds,"m2",filters.getMinSize(), filters.getMaxSize());

        availableIds = filterIntRange(availableIds,"price", filters.getMinPrice(), filters.getMaxSize());

        if (filters.isWifi())
        {
            availableIds = Rental.strongFilter(availableIds,"wifi","yes");
        }

        if (filters.isParking())
        {
            availableIds = Rental.strongFilter(availableIds,"parking","yes");
        }


        if (filters.isPool())
        {
            availableIds = Rental.strongFilter(availableIds,"pool","yes");
        }


        return availableIds.toArray(new String[0]);
    }

    /**
     * Finds all the rentals owned by a specific user
     * @param user
     * @return String Array with the ids of said rentals
     */
    public static String[] filterByOwner(User user)
    {
        HashSet<String> myIds = Rental.strongFilter("owner", user.getUserName());
        return myIds.toArray(new String[0]);
    }

    /**
     * Finds all the reservations a specific user has made
     * @param user
     * @return String Array where every string contains the name and id of a rental and the first and last date of the user's reservation
     */
    public static String[] getReservationsFor(User user)
    {
        HashSet<String> allRentals = Rental.getAll();
        String username = user.getUserName();

        HashSet<String> reservations = new HashSet<>();


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

                String current = (rental.getCharacteristic("name") + " (" + id + ")  from: " + first.toHumanString() + " until: "+ last.toHumanString());
                reservations.add(current);
            }

        }

        return reservations.toArray(new String[0]);
    }

    /**
     * converts false to "no" and true to "yes"
     */
    public static String toYesNo(boolean value)
    {
        if (value)
        {
            return "yes";
        }
        else
        {
            return "no";
        }
    }

    /**
     * Adds reservation dates for a user in a specific rental
     * @param startDate the beginning of the reservation period
     * @param endDate the end of the reservation period
     * @param username the user's username
     * @param id the rental's id
     */
    public static void reserve(Date startDate, Date endDate,String username, String id)
    {
        Date.addDate(id,username,startDate);
        Date.addDate(id,username,endDate);
        for (Date date : Date.getBetween(startDate, endDate))
        {

            Date.addDate(id, username, date);
        }
    }


}
