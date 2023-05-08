import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
 * Where do I even begin to explain this...
 * This is the worst designed aspect of the whole project
 *
 * It implements a Frame with card layout, on which every "screen" is added as Panels, then interchanged based on actions (exclusively button clicks)
 * Every Screen is designed separately as its own subclass but some of them that were too similar share sub-classes and are differentiated by an integer in their constructors
 * All the actions are handled by two Action Listeners that handle EVERY interaction the user has with the program
 * The first action listener is navListener (or Navigation Listener) and it handles the simple exchange of panels in the frame (and for two buttons something more but I was too bored to migrate them to the other listener)
 * The second action listener is funListener (or Function Listener, but it also does the fun stuff), and it handles everything else
 */
public class MainWindow extends CoolComponents.CoolFrame
{
    /**
     * standard point to put labels (i had to manually change them after all)
     */
    final Point headerPoint = new Point(0, 7);
    /**
     * standard point to put the right button (almost always worked)
     */
    final Point rightButtonPoint = new Point(375, 395);
    /**
     * standard point to put the secondary buttons (almost never worked because the secondary buttons had bigger words so the needed to change size and consequently position,to fit appropriately)
     */
    final Point secondButtonPoint = new Point(265, 395);
    /**
     * the default user icon
     */
    final static String defUserPic = "./pictures/user.png";
    /**
     * the default rental icon
     */
    final static String defRentalPic = "./pictures/house.png";


    //All of these screens are better explained in their respective constructors (not well-explained, but better)
    private MainMenuScreen mainMenuScreen = new MainMenuScreen();
    private AccountScreen userScreen;
    private MessageMenuScreen messageMenuScreen = new MessageMenuScreen();
    private ReadMessageScreen readMessageScreen = new ReadMessageScreen();
    private SendMessageScreen sendMessageScreen = new SendMessageScreen();
    private RentalMenuScreen rentalMenuScreen = new RentalMenuScreen();
    private SearchScreen searchScreenBasic = new SearchScreen(1);
    private SearchScreen searchScreenAdvanced = new SearchScreen(2);
    private ProviderMenuScreen providerMenuScreen = new ProviderMenuScreen();
    private AdminMenuScreen adminMenuScreen = new AdminMenuScreen();
    private RentalListScreen rentalListScreen = new RentalListScreen();
    private RentalScreen rentalScreen = new RentalScreen(1);
    private UserListScreen userListScreen = new UserListScreen(1);
    private AccountScreen accountScreen = new AccountScreen(new User("User"));
    private ReservationsList reservationsList = new ReservationsList();
    private RentalScreen reserveScreen = new RentalScreen(2);
    private UserListScreen requestList = new UserListScreen(2);

    /**
     * used to differentiate between approval of users and approval of provider requests
     */
    private String verType;
    /**
     * Rental View Mode, used to differentiate between an owner accessing a rental's screen or someone else
     */
    private int rvm;
    /**
     * a list with all the messages sent to the user (initialized in the constructor)
     */
    private ArrayList<Messages.Message> messages;
    /**
     * a index tracker for the messages the user views (can't explain it any better)
     */
    private int messageCounter;
    /**
     * stores the first date picked in the filters to use later in the reservation screen
     */
    Date fromDate;
    /**
     * stores the last date picked in the filters to use later in the reservation screen
     */
    Date toDate;
    /**
     * when the window closes this turns to 1 to signify that the databases should be updated
     */
    int done = 0;

    /**
     * the listener that handles simple navigation (and occasionally some fun stuff)
     */
    private NavigationListener navListener = new NavigationListener();
    /**
     * the listener that handles complex interactions
     */
    private FunctionalListener funListener = new FunctionalListener();
    /**
     * the listener that does nothing other than update the done field
     */
    private WindowListener myWindowListener = new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            super.windowClosing(e);
            done = 1;
        }
    };

    /**
     * the user that is logged into the program
     */
    private User user;

    /**
     * constructor for the main window
     * @param cUser the user tha is logged in
     */
    public MainWindow(User cUser)
    {
        super();
        this.addWindowListener(myWindowListener);

        this.setIconImage(new ImageIcon("./pictures/fire.png").getImage());
        this.setTitle("FireBNB" + '\u2122');

        this.user = cUser;
        userScreen = new AccountScreen(user);

        messages = Messages.getMessages(user.getUserName());
        if (messages == null)
        {
            messages = new ArrayList<>(0);
        }

        addScreens();

        addActionListeners();


        this.setVisible(true);
    }

    /**
     * adds all the panels that the user can navigate through and interact with (and names them...)
     */
    private void addScreens()
    {
        cPane.add(mainMenuScreen, "main menu");
        cPane.add(userScreen, "user");
        cPane.add(messageMenuScreen, "message menu");
        cPane.add(sendMessageScreen, "send message");
        cPane.add(readMessageScreen, "read message");
        cPane.add(rentalMenuScreen, "rental menu");
        cPane.add(searchScreenBasic, "search basic");
        cPane.add(searchScreenAdvanced, "search advanced");
        cPane.add(providerMenuScreen, "provider menu");
        cPane.add(adminMenuScreen, "admin menu");
        cPane.add(rentalListScreen,"rental list");
        cPane.add(rentalScreen,"rental screen");
        cPane.add(userListScreen,"user list");
        cPane.add(accountScreen,"account screen");
        cPane.add(reservationsList,"reservations list");
        cPane.add(reserveScreen,"reserve screen");
        cPane.add(requestList,"request list");

    }

    /**
     * adds action listeners to all the buttons
     */
    private void addActionListeners()
    {
        this.mainMenuScreen.account.addActionListener(navListener);
        this.mainMenuScreen.messages.addActionListener(navListener);
        this.mainMenuScreen.rentals.addActionListener(navListener);
        this.mainMenuScreen.provider.addActionListener(navListener);
        this.mainMenuScreen.admin.addActionListener(navListener);

        this.userScreen.back.addActionListener(navListener);
        this.userScreen.edit.addActionListener(funListener);
        this.userScreen.commit.addActionListener(funListener);
        this.userScreen.accountView.idRow.change.addActionListener(funListener);

        this.messageMenuScreen.back.addActionListener(navListener);
        this.messageMenuScreen.send.addActionListener(navListener);
        this.messageMenuScreen.read.addActionListener(navListener);

        this.sendMessageScreen.back.addActionListener(navListener);
        this.sendMessageScreen.send.addActionListener(funListener);

        this.readMessageScreen.back.addActionListener(navListener);
        this.readMessageScreen.delete.addActionListener(funListener);
        this.readMessageScreen.next.addActionListener(funListener);

        this.rentalMenuScreen.back.addActionListener(navListener);
        this.rentalMenuScreen.find.addActionListener(navListener);
        this.rentalMenuScreen.reservations.addActionListener(funListener);

        this.searchScreenBasic.back.addActionListener(navListener);
        this.searchScreenBasic.more.addActionListener(navListener);
        this.searchScreenBasic.next.addActionListener(funListener);

        this.searchScreenAdvanced.back.addActionListener(navListener);
        this.searchScreenAdvanced.next.addActionListener(funListener);

        this.providerMenuScreen.back.addActionListener(navListener);
        this.providerMenuScreen.add.addActionListener(funListener);
        this.providerMenuScreen.view.addActionListener(funListener);

        this.adminMenuScreen.users.addActionListener(funListener);
        this.adminMenuScreen.listings.addActionListener(funListener);
        this.adminMenuScreen.uRequests.addActionListener(funListener);
        this.adminMenuScreen.pRequests.addActionListener(funListener);
        this.adminMenuScreen.back.addActionListener(navListener);

        this.rentalListScreen.back.addActionListener(navListener);
        this.rentalListScreen.view.addActionListener(funListener);

        this.rentalScreen.back.addActionListener(navListener);
        this.reserveScreen.back.addActionListener(navListener);

        this.rentalScreen.edit.addActionListener(funListener);
        this.rentalScreen.commit.addActionListener(funListener);
        this.rentalScreen.rentalView.idRow.change.addActionListener(funListener);

        this.reserveScreen.reserve.addActionListener(funListener);

        this.userListScreen.back.addActionListener(navListener);
        this.userListScreen.view.addActionListener(funListener);

        this.accountScreen.back.addActionListener(navListener);

        this.reservationsList.back.addActionListener(navListener);

        this.requestList.back.addActionListener(navListener);
        this.requestList.verify.addActionListener(funListener);

    }


    /**
     * mostly handles the "back" buttons and everything that doesn't require more code than two lines of code
     */
    private class NavigationListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //main menu
            if (e.getSource().equals(mainMenuScreen.account))
            {
                card.show(cPane, "user");
                return;
            }
            if (e.getSource().equals(mainMenuScreen.messages))
            {
                card.show(cPane, "message menu");
                return;
            }
            if (e.getSource().equals(mainMenuScreen.rentals))
            {
                card.show(cPane, "rental menu");
                return;
            }
            if (e.getSource().equals(mainMenuScreen.provider))
            {
                if (!user.is("provider"))
                {

                    if (user.has("provider"))
                    {
                        JOptionPane.showMessageDialog(cPane,"You have requested Provide Privileges \nPlease wait until an Admin confirms your request");
                        return;
                    }
                    int ans = JOptionPane.showConfirmDialog(cPane,"You do not have have access to this\nWould you like to request provider privileges?","Provider Request",JOptionPane.YES_NO_OPTION);

                    if (ans==0)
                    {
                        user.addCredential("provider","no");
                        user.updateCredentials();
                        JOptionPane.showMessageDialog(cPane,"You have requested Provide Privileges \nPlease wait until an Admin confirms your request");
                    }
                    return;
                }


                card.show(cPane, "provider menu");
                return;
            }
            if (e.getSource().equals(mainMenuScreen.admin))
            {
                if (!user.is("admin"))
                {
                    int ans = JOptionPane.showConfirmDialog(cPane,"You do not have Admin Privileges\nDo you want to become an Admin?","Admin Request",JOptionPane.YES_NO_OPTION);

                    if (ans == 0)
                    {
                        String pass = JOptionPane.showInputDialog(cPane,"Please enter the password only Admins know!!!\n(if you are employed by us you should know the password)");
                        if (pass.equals("admin"))
                        {
                            JOptionPane.showMessageDialog(cPane,"CONGRATULATIONS!!!\nYou are now an admin, go approve requests or you are fired");
                            user.addCredential("admin","yes");
                            user.updateCredentials();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(cPane,"YOU HAVE FAILED!!!\nYour account will be deleted shortly...");
                            JOptionPane.showMessageDialog(cPane,"That was a joke, your account will not be deleted");
                            return;
                        }
                    }
                    else
                    {
                        return;
                    }
                }


                card.show(cPane, "admin menu");
                return;
            }


            //user screen
            if (e.getSource().equals(userScreen.back))
            {
                card.show(cPane, "main menu");
                return;
            }


            //message menu
            if (e.getSource().equals(messageMenuScreen.back))
            {
                card.show(cPane, "main menu");
                return;
            }
            if (e.getSource().equals(messageMenuScreen.send))
            {
                card.show(cPane, "send message");
                return;
            }
            if (e.getSource().equals(messageMenuScreen.read))
            {
                if (messages.isEmpty())
                {
                    JOptionPane.showMessageDialog(cPane,"You don't have any messages");
                    return;
                }
                messageCounter = 0;
                readMessageScreen.showMessage(messages.get(messageCounter));
                card.show(cPane, "read message");
                return;
            }

            //send message
            if (e.getSource().equals(sendMessageScreen.back))
            {
                card.show(cPane, "message menu");
                return;
            }

            //read message
            if (e.getSource().equals(readMessageScreen.back))
            {
                card.show(cPane, "message menu");
                return;
            }


            //rental menu
            if (e.getSource().equals(rentalMenuScreen.back))
            {
                card.show(cPane, "main menu");
                return;
            }
            if (e.getSource().equals(rentalMenuScreen.find))
            {
                card.show(cPane, "search basic");
                return;
            }

            //basic search
            if (e.getSource().equals(searchScreenBasic.back))
            {
                card.show(cPane, "rental menu");
                return;
            }
            if (e.getSource().equals(searchScreenBasic.more))
            {
                card.show(cPane, "search advanced");
                return;
            }

            //search advanced
            if (e.getSource().equals(searchScreenAdvanced.back))
            {
                card.show(cPane, "search basic");
                return;
            }


            //provider menu
            if (e.getSource().equals(providerMenuScreen.back))
            {
                card.show(cPane, "main menu");
                return;
            }


            //admin menu
            if (e.getSource().equals(adminMenuScreen.back))
            {
                card.show(cPane, "main menu");
                return;
            }





            //rental list
            if (e.getSource().equals(rentalListScreen.back))
            {
                card.show(cPane,e.getActionCommand());
                return;
            }

            //rental screen
            if (e.getSource().equals(rentalScreen.back))
            {
                if (e.getActionCommand().equals("take me to provider"))
                {
                    rentalScreen.back.setActionCommand("");
                    card.show(cPane,"provider menu");
                    return;
                }

                card.show(cPane,"rental list");
                return;
            }

            //reserve screen
            if (e.getSource().equals(reserveScreen.back))
            {
                card.show(cPane,"rental list");
                return;
            }


            //user list
            if (e.getSource().equals(userListScreen.back))
            {
                card.show(cPane,"admin menu");
                return;
            }


            //account screen
            if (e.getSource().equals(accountScreen.back))
            {
                card.show(cPane,"user list");
                return;
            }


            //reservations list
            if (e.getSource().equals(reservationsList.back))
            {
                card.show(cPane,"rental menu");
                return;
            }


            //request list
            if (e.getSource().equals(requestList.back))
            {
                card.show(cPane,"admin menu");
                return;
            }
        }
    }

    /**
     * does EVERYTHING, very mysterious and indecipherable by other developers
     * actually looked worse during development
     */
    private class FunctionalListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            //user screen
            if (e.getSource().equals(userScreen.edit))
            {
                userScreen.changeMode(1);
                return;
            }
            if (e.getSource().equals(userScreen.commit))
            {
                if(!accountChanges())
                {
                    return;
                }
                JOptionPane.showMessageDialog(cPane,"Your credentials have been changed successfully");
                userScreen.changeMode(0);
                return;
            }
            if (e.getSource().equals(userScreen.accountView.idRow.change))
            {
                changeUserPic();
                return;
            }


            //messages
            if (e.getSource().equals(sendMessageScreen.send))
            {
                if (User.checkUsernameAvailability(sendMessageScreen.recipient.textfield.getText()))
                {
                    JOptionPane.showMessageDialog(cPane,"The user you're trying to message does not exist");
                    return;
                }
                if (sendMessageScreen.textArea.getLineCount() > 1)
                {
                    JOptionPane.showMessageDialog(cPane,"Unfortunately our infrastructure would collapse if you sent a message with more than one line\nI would make the text area smaller,but i like the way it looks\nSorry for the inconvenience");
                    return;
                }
                if (sendMessageScreen.textArea.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(cPane,"You cannot send an empty message");
                    return;
                }

                Messages.send(user.getUserName(),sendMessageScreen.recipient.textfield.getText(),sendMessageScreen.textArea.getText());
                JOptionPane.showMessageDialog(cPane,"Message sent successfully");
                card.show(cPane,"message menu");
                return;
            }
            if (e.getSource().equals(readMessageScreen.delete))
            {
                Messages.Message current = messages.get(messageCounter);
                Messages.delete(user.getUserName(), current.getSender(), current.getBody());
                messages.remove(messageCounter);

                if (messages.size()-1 < messageCounter)
                {
                    JOptionPane.showMessageDialog(cPane,"No more messages");
                    card.show(cPane,"message menu");
                    return;
                }

                readMessageScreen.showMessage(messages.get(messageCounter));
            }
            if (e.getSource().equals(readMessageScreen.next))
            {
                if (messages.size()-1 < messageCounter+1)
                {
                    JOptionPane.showMessageDialog(cPane,"No more messages");
                    card.show(cPane,"message menu");
                    return;
                }
                messageCounter++;
                readMessageScreen.showMessage(messages.get(messageCounter));
                return;
            }

            //rentals
            if (e.getSource().equals(rentalScreen.edit))
            {
                rentalScreen.changeMode(1);
                return;
            }
            if (e.getSource().equals(rentalScreen.commit))
            {
                if (!editRental())
                {
                    return;
                }
                rentalScreen.changeMode(0);
                return;
            }
            if (e.getSource().equals(rentalScreen.rentalView.idRow.change))
            {
                changeRentalPic(rentalScreen.rental);
                return;
            }
            if (e.getSource().equals(reserveScreen.reserve))
            {
                int ans = JOptionPane.showConfirmDialog(cPane,"Do you want to reserve this Rental for the dates\n" + fromDate.toHumanString() +" - " + toDate.toHumanString(),"Confirm Reservation",JOptionPane.YES_NO_OPTION);

                if (ans==0)
                {
                    GUIMethods.reserve(fromDate,toDate,user.getUserName(),reserveScreen.rental.getId());

                }

                return;
            }

            //lists
            if (e.getSource().equals(adminMenuScreen.listings))
            {
                rentalListScreen.showAll();
                rentalListScreen.back.setActionCommand("admin menu");
                rvm = 0;
                card.show(cPane,"rental list");
                return;
            }
            if (e.getSource().equals(rentalListScreen.view))
            {
                if (rentalListScreen.rentals.list.isSelectionEmpty())
                {
                    JOptionPane.showMessageDialog(cPane,"Please select a rental to view");
                    return;
                }


                if (rvm == 0)
                {
                    rentalScreen.changeRental(new Rental((String) rentalListScreen.rentals.list.getSelectedValue()));
                    rentalScreen.edit.setEnabled(false);
                    card.show(cPane, "rental screen");
                    return;
                }
                if (rvm == 1)
                {
                    rentalScreen.changeRental(new Rental((String) rentalListScreen.rentals.list.getSelectedValue()));
                    rentalScreen.edit.setEnabled(true);
                    card.show(cPane,"rental screen");
                    return;
                }
                if (rvm == 2)
                {
                    reserveScreen.changeRental(new Rental((String) rentalListScreen.rentals.list.getSelectedValue()));
                    card.show(cPane,"reserve screen");
                    return;
                }

            }
            if (e.getSource().equals(requestList.verify))
            {
                if (requestList.users.list.isSelectionEmpty())
                {
                    JOptionPane.showMessageDialog(cPane,"Choose a user to verify");
                    return;
                }

                User cUser = new User((String) requestList.users.list.getSelectedValue());
                int ans = JOptionPane.showConfirmDialog(cPane,"Approve request?","Confirm",JOptionPane.YES_NO_OPTION);

                if (ans == 1)
                {
                    return;
                }

                cUser.setCredential(verType,"yes");
                cUser.updateCredentials();


                return;
            }
            if (e.getSource().equals(userListScreen.view))
            {
                if (userListScreen.users.list.isSelectionEmpty())
                {
                    JOptionPane.showMessageDialog(cPane,"Please select a user to view");
                    return;
                }

                accountScreen.changeUser(new User((String) userListScreen.users.list.getSelectedValue()));
                card.show(cPane,"account screen");
                return;
            }
            if (e.getSource().equals(providerMenuScreen.view))
            {
                rentalListScreen.showOwned();
                rentalListScreen.back.setActionCommand("provider menu");
                if (GUIMethods.filterByOwner(user).length==0)
                {
                    JOptionPane.showMessageDialog(cPane,"You don't have any listings in our service");
                    return;
                }
                rvm = 1;
                card.show(cPane,"rental list");
                return;
            }

            //admin
            if (e.getSource().equals(adminMenuScreen.users))
            {
                userListScreen.showAll();

                card.show(cPane,"user list");
                return;
            }
            if (e.getSource().equals(adminMenuScreen.uRequests))
            {
                requestList.showUnverified();
                verType = "activated";

                card.show(cPane,"request list");
                return;
            }
            if (e.getSource().equals(adminMenuScreen.pRequests))
            {
                requestList.showProviders();
                verType = "provider";

                card.show(cPane,"request list");
                return;
            }
            if (e.getSource().equals(rentalMenuScreen.reservations))
            {
                reservationsList.showOwned();
                card.show(cPane,"reservations list");
                return;
            }

            //search screen
            if (e.getSource().equals(searchScreenBasic.next))
            {
                if (!checkFilters())
                {
                    return;
                }
                GUIMethods.Filters filters = getFilters();
                fromDate = filters.getFromDate();
                toDate = filters.getToDate();

                if (GUIMethods.addFilters(filters).length==0)
                {
                    JOptionPane.showMessageDialog(cPane,"No Rentals match your Criteria,\n feel free to try again with different filters");
                    return;
                }

                rentalListScreen.showFiltered(filters);
                rentalListScreen.back.setActionCommand("search basic");
                rvm = 2;
                card.show(cPane,"rental list");

                return;
            }
            if (e.getSource().equals(searchScreenAdvanced.next))
            {
                if (!checkFilters())
                {
                    return;
                }
                GUIMethods.Filters filters = getFilters();
                fromDate = filters.getFromDate();
                toDate = filters.getToDate();

                rentalListScreen.showFiltered(filters);


                if (GUIMethods.addFilters(filters).length==0)
                {
                    JOptionPane.showMessageDialog(cPane,"No Rentals match your Criteria,\n feel free to try again with different filters");
                    return;
                }

                rentalListScreen.back.setActionCommand("search basic");
                rvm = 2;
                card.show(cPane,"rental list");
                return;
            }

            //provider
            if (e.getSource().equals(providerMenuScreen.add))
            {
                if (createRental())
                {
                    rentalScreen.back.setActionCommand("take me to provider");
                }
                return;
            }
        }
    }


    /**
     * The screen to view and edit account information
     */
    private class AccountScreen extends CoolComponents.CoolPanelFull
    {
        private AccountView accountView;
        private User user;
        private CoolComponents.CoolButton edit = new CoolComponents.CoolButton("Edit");
        private CoolComponents.CoolButton commit = new CoolComponents.CoolButton("Commit");

        /**
         * the default user to be displayed on the screen
         * @param cUser
         */
        private AccountScreen(User cUser)
        {
            super();
            this.user = cUser;
            accountView = new AccountView(user);
            this.setInnerPanel(accountView);


            edit.setLocation(rightButtonPoint);
            commit.setLocation(secondButtonPoint);

            commit.setFont(fontSize(17));

            this.add(edit);
            this.add(commit);

            changeMode(0);
        }

        /**
         * The inner panel of AccountScreen
         */
        private class AccountView extends JPanel
        {
            private User user;
            private CoolComponents.IDRow idRow;
            private CoolComponents.CoolRow email;
            private CoolComponents.CoolRow password;
            private CoolComponents.CheckRow provider;
            private CoolComponents.CheckRow admin;

            private AccountView(User cUser)
            {
                this.user = cUser;
                String iconName = defUserPic;

                if (user.has("icon"))
                {
                    iconName = user.getCredential("icon");
                }

                idRow = new CoolComponents.IDRow(iconName,user.getUserName());

                email = new CoolComponents.CoolRow("Email", user.getCredential("email"));
                password = new CoolComponents.CoolRow("Password", user.getCredential("password"));
                provider = new CoolComponents.CheckRow("Provider",user.is("provider"));
                admin = new CoolComponents.CheckRow("Admin",user.is("admin"));


                this.setLayout(null);
                idRow.setLocation(rowAt(5));
                email.setLocation(rowAt(160));
                password.setLocation(rowAt(190));
                provider.setLocation(rowAt(220));
                admin.setLocation(rowAt(250));

                provider.label.setSize(provider.label.getWidth() + 30,provider.label.getHeight());
                provider.checkBox.setLocation(provider.checkBox.getX()+30,provider.checkBox.getY());

                admin.label.setSize(admin.label.getWidth() + 30,admin.label.getHeight());
                admin.checkBox.setLocation(admin.checkBox.getX()+30,admin.checkBox.getY());

                this.add(idRow);
                this.add(email);
                this.add(password);
                this.add(admin);
                this.add(provider);
            }

            /**
             * enables editing in the inner panel
             * @param enable
             */
            private void makeEditable(boolean enable)
            {
                this.email.enableEditing(enable);
                this.password.enableEditing(enable);
                this.idRow.change.setEnabled(enable);
            }
        }

        /**
         * Enables or disables editing info
         *
         * @param mode if 0 the fields won't be editable,else they will
         */
        private void changeMode(int mode)
        {
            if (mode == 0)
            {
                edit.setEnabled(true);
                commit.setEnabled(false);
                accountView.makeEditable(false);
                back.setEnabled(true);
            }
            else
            {
                edit.setEnabled(false);
                commit.setEnabled(true);
                accountView.makeEditable(true);
                back.setEnabled(false);
            }

        }

        /**
         * changes the info shown on screen
         * @param newUser
         */
        private void changeUser(User newUser)
        {
            if (newUser.has("icon"))
            {
                accountView.idRow.icon.changeIcon(newUser.getCredential("icon"));
            }
            else
            {
                accountView.idRow.icon.changeIcon(defUserPic);
            }
            accountView.idRow.name.setText(newUser.getUserName());
            accountView.password.textfield.setText(newUser.getCredential("password"));
            accountView.email.textfield.setText(newUser.getCredential("email"));
            accountView.admin.checkBox.setState(newUser.is("admin"));
            accountView.provider.checkBox.setState(newUser.is("provider"));
            this.edit.setEnabled(false);
            this.commit.setEnabled(false);
        }
    }



    /**
     * The screen to view messages sent to the user
     */
    private class ReadMessageScreen extends CoolComponents.CoolPanelHalf
    {
        private CoolComponents.CoolTextArea textArea;
        private CoolComponents.LargeRow sender;
        CoolComponents.CoolButton next = new CoolComponents.CoolButton("NEXT");
        CoolComponents.CoolButton delete = new CoolComponents.CoolButton("DELETE");

        private ReadMessageScreen()
        {
            JPanel inner = new JPanel();
            inner.setLayout(null);

            textArea = new CoolComponents.CoolTextArea();
            textArea.setEditable(false);
            textArea.setText("");


            next.setLocation(rightButtonPoint);
            delete.setLocation(secondButtonPoint);
            delete.setFont(fontSize(17));

            sender = new CoolComponents.LargeRow("FROM", "");
            sender.label.setSize(sender.label.getWidth() - 110, sender.label.getHeight());
            sender.setLocation(headerPoint);

            inner.add(textArea);
            this.add(sender);
            this.setInnerPanel(inner);
            this.add(delete);
            this.add(next);
        }

        /**
         * loads the contents of a message on the screen
         * @param message
         */
        private void showMessage(Messages.Message message)
        {
            this.sender.textfield.setText(message.getSender());
            this.textArea.setText(message.getBody());
        }
    }

    /**
     * The screen to compile and send messages to other users
     */
    private class SendMessageScreen extends CoolComponents.CoolPanelHalf
    {
        private CoolComponents.CoolTextArea textArea;
        private CoolComponents.LargeRow recipient;
        CoolComponents.CoolButton send = new CoolComponents.CoolButton("SEND");

        private SendMessageScreen()
        {
            JPanel inner = new JPanel();
            inner.setLayout(null);

            textArea = new CoolComponents.CoolTextArea();
            inner.add(textArea);

            recipient = new CoolComponents.LargeRow("To", "");
            recipient.textfield.setLocation(60, 0);
            recipient.enableEditing(true);

            recipient.setLocation(headerPoint);
            recipient.label.setSize(recipient.label.getWidth() - 160, recipient.label.getHeight());


            send.setLocation(rightButtonPoint);

            this.setInnerPanel(inner);
            this.add(send);
            this.add(recipient);

        }
    }



    /**
     * The screen to view,edit and create rentals
     * The first screen to have two forms differentiated by an integer in the constructor
     */
    private class RentalScreen extends CoolComponents.CoolPanelFull
    {
        private RentalView rentalView;
        private Rental rental;
        CoolComponents.CoolButton edit = new CoolComponents.CoolButton("Edit");
        CoolComponents.CoolButton reserve = new CoolComponents.CoolButton("Reserve");
        private CoolComponents.CoolButton commit = new CoolComponents.CoolButton("Commit");

        /**
         * @param i = 1 : the screen is made for the owner of the rental,else it is created for users to view and reserve
         */
        private RentalScreen(int i)
        {
            super();

            rentalView = new RentalView();

            this.setInnerPanel(rentalView);


            edit.setLocation(rightButtonPoint);
            reserve.setLocation(rightButtonPoint.x-30,rightButtonPoint.y);
            reserve.setSize(reserve.getWidth()+30,reserve.getHeight());
            commit.setLocation(secondButtonPoint);


            if (i==1)
            {
                this.add(edit);
                this.add(commit);

                this.changeMode(0);
            }
            else
            {
                this.add(reserve);
                this.rentalView.idRow.change.setEnabled(false);
            }

        }

        /**
         * The inner panel of Rental Screen
         */
        private class RentalView extends JPanel
        {
            private CoolComponents.IDRow idRow;
            private CoolComponents.CoolRow location;
            private CoolComponents.CoolRow type;
            private CoolComponents.NarrowRow size;
            private CoolComponents.NarrowRow price;
            private CoolComponents.CheckRow wifi;
            private CoolComponents.CheckRow parking;
            private CoolComponents.CheckRow pool;
            private CoolComponents.NarrowRow id;

            private RentalView()
            {
                this.setLayout(null);

                idRow = new CoolComponents.IDRow(defRentalPic, "");
                location = new CoolComponents.CoolRow("Location","");
                type = new CoolComponents.CoolRow("Type","");
                size = new CoolComponents.NarrowRow("Size" + "(m\u00B2)","" );
                price = new CoolComponents.NarrowRow("Price" + "(\u20ac)", "");
                wifi = new CoolComponents.CheckRow("Wifi", true);
                parking = new CoolComponents.CheckRow("Parking", true);
                pool = new CoolComponents.CheckRow("Pool", false);
                id = new CoolComponents.NarrowRow("ID", "");


                idRow.setLocation(rowAt(5));
                location.setLocation(rowAt(160));
                type.setLocation(rowAt(190));
                size.setLocation(rowAt(220));
                price.setLocation(rowAt(250));
                wifi.setLocation(rowAt(280));
                parking.setLocation(rowAt(310));
                pool.setLocation(rowAt(340));

                size.label.setSize(size.label.getWidth()-120,size.label.getHeight());
                price.label.setSize(price.label.getWidth()-120,price.label.getHeight());

                id.setLocation(170, 50);
                id.label.setLocation(id.label.getX() + 40, id.label.getY());
                id.label.setSize(30,id.label.getHeight());
                idRow.add(id);

                this.add(idRow);
                this.add(location);
                this.add(type);
                this.add(size);
                this.add(price);
                this.add(wifi);
                this.add(parking);
                this.add(pool);

            }

            private void changeRental(Rental rental)
            {
                if (rental.contains("icon"))
                {
                    idRow.icon.changeIcon(rental.getCharacteristic("icon"));
                }
                else
                {
                    idRow.icon.changeIcon(defRentalPic);
                }
                idRow.name.setText(rental.getCharacteristic("name"));
                location.textfield.setText( rental.getCharacteristic("location"));
                type.textfield.setText( rental.getCharacteristic("type"));
                size.textfield.setText(rental.getCharacteristic("m2"));
                price.textfield.setText(rental.getCharacteristic("price"));
                wifi.checkBox.setState(rental.has("wifi"));
                parking.checkBox.setState(rental.has("parking"));
                pool.checkBox.setState(rental.has("pool"));
                id.textfield.setText(rental.getId());

            }
            private void makeEditable(boolean enable)
            {
                this.type.enableEditing(enable);
                this.location.enableEditing(enable);
                this.size.enableEditing(enable);
                this.price.enableEditing(enable);
                this.wifi.checkBox.setClickable(enable);
                this.parking.checkBox.setClickable(enable);
                this.pool.checkBox.setClickable(enable);
                this.idRow.change.setEnabled(enable);

            }

        }

        /**
         * changes the contents of the screen to match a new rental
         * @param rental the rental
         */
        private void changeRental(Rental rental)
        {
            this.rental=rental;
            rentalView.changeRental(rental);
        }

        /**
         * Enables or disables editing info
         *
         * @param mode if 0 the fields won't be editable,else they will
         */
        private void changeMode(int mode)
        {
            if (mode == 0)
            {
                edit.setEnabled(true);
                commit.setEnabled(false);
                rentalView.makeEditable(false);
                back.setEnabled(true);
            }
            else
            {
                edit.setEnabled(false);
                commit.setEnabled(true);
                rentalView.makeEditable(true);
                back.setEnabled(false);
            }

        }
    }

    /**
     * The screen to search for rentals
     * also has two forms
     */
    private class SearchScreen extends CoolComponents.CoolPanelHalf
    {
        private FilterViewBasic basic = new FilterViewBasic();
        private FilterViewMore advanced = new FilterViewMore();

        private CoolComponents.CoolButton next = new CoolComponents.CoolButton("NEXT");
        private CoolComponents.CoolButton more = new CoolComponents.CoolButton("MORE FILTERS");

        /**
         * @param i = 1 : basic/first batch of filters, else advanced/second batch of filters (they are actually not advanced,they are more minor and didn't all fit in one screen)
         */
        private SearchScreen(int i)
        {
            CoolComponents.CoolLabel header = new CoolComponents.CoolLabel("Filters");
            header.setSize(180, 40);
            header.setFont(fontSize(30));
            header.setLocation(headerPoint.x + 10, headerPoint.y);
            this.add(header);

            next.setLocation(rightButtonPoint);
            more.setLocation(secondButtonPoint.x - 50, secondButtonPoint.y);
            more.setSize(more.getWidth() + 50, more.getHeight());
            more.setFont(fontSize(15));


            this.add(next);

            if (i == 1)
            {
                this.setInnerPanel(basic);
                this.add(more);
            }
            else
            {
                this.setInnerPanel(advanced);
            }


        }

        /**
         * the inner panel of SearchScreen (first batch of filters)
         */
        private class FilterViewBasic extends JPanel
        {
            private CoolComponents.CoolDatePicker date1 = new CoolComponents.CoolDatePicker();
            private CoolComponents.CoolDatePicker date2 = new CoolComponents.CoolDatePicker();
            private CoolComponents.CoolRow type = new CoolComponents.CoolRow("Type (Hotel,Camp,etc.)", "Don't Care");
            private CoolComponents.CoolRow location = new CoolComponents.CoolRow("Location", "Don't Care");


            private FilterViewBasic()
            {
                this.setLayout(null);

                CoolComponents.CoolLabel from = new CoolComponents.CoolLabel("From");
                CoolComponents.CoolLabel until = new CoolComponents.CoolLabel("Until");
                CoolComponents.CoolLabel dates = new CoolComponents.CoolLabel("Dates");


                dates.setSize(100, 50);

                from.setSize(60, 40);
                until.setSize(60, 40);

                dates.setFont(fontSize(30));

                from.setFont(fontSize(20));
                until.setFont(fontSize(20));

                dates.setLocation(10, 2);

                from.setLocation(60, 60);
                until.setLocation(60, 120);
                date1.setLocation(180, 50);
                date2.setLocation(180, 110);

                this.add(dates);
                this.add(from);
                this.add(until);
                this.add(date1);
                this.add(date2);

                type.setLocation(rowAt(200));
                location.setLocation(rowAt(250));

                type.enableEditing(true);
                location.enableEditing(true);

                this.add(type);
                this.add(location);

            }
        }

        /**
         * the inner panel of SearchScreen (second batch of filters)
         */
        private class FilterViewMore extends JPanel
        {
            private CoolComponents.MinMaxRow price = new CoolComponents.MinMaxRow("Price");
            private CoolComponents.MinMaxRow size = new CoolComponents.MinMaxRow("Size");
            private CoolComponents.CheckRow wifi = new CoolComponents.CheckRow("Wifi", false);
            private CoolComponents.CheckRow pool = new CoolComponents.CheckRow("Pool", false);
            private CoolComponents.CheckRow parking = new CoolComponents.CheckRow("Parking", false);

            private FilterViewMore()
            {
                this.setLayout(null);

                price.setLocation(rowAt(20));
                size.setLocation(rowAt(70));
                wifi.setLocation(rowAt(120));
                pool.setLocation(rowAt(170));
                parking.setLocation(rowAt(220));

                wifi.checkBox.setClickable(true);
                pool.checkBox.setClickable(true);
                parking.checkBox.setClickable(true);

                this.add(price);
                this.add(size);
                this.add(wifi);
                this.add(pool);
                this.add(parking);
            }
        }


    }

    /**
     * Screen that shows a list of rentals
     * (I wanted to have their names and ids in the list but I accidentally made the contents of the list extremely vital and could not change them afterwards)
     */
    private class RentalListScreen extends CoolComponents.CoolPanelHalf
    {
        private CoolComponents.CoolList rentals = new CoolComponents.CoolList();
        private CoolComponents.CoolButton view = new CoolComponents.CoolButton("View");
        RentalListScreen()
        {
            CoolComponents.CoolLabel header = new CoolComponents.CoolLabel("Choose a Rental");
            header.setSize(300, 40);
            header.setFont(fontSize(30));
            header.setLocation(headerPoint.x + 10, headerPoint.y);
            this.add(header);

            view.setLocation(rightButtonPoint);

            String[] empty = {""};
            rentals.addList(empty);

            this.setInnerPanel(rentals);
            this.add(view);
        }

        /**
         * loads a list with all the rentals
         */
        public void showAll()
        {
            rentals.updateList(GUIMethods.getAllRentals());
        }

        /**
         * loads a list with the rentals after applying the search filters on them
         * @param filters
         */
        public void showFiltered(GUIMethods.Filters filters)
        {
            rentals.updateList(GUIMethods.addFilters(filters));
        }

        /**
         * loads a list with the rentals owned by the user
         */
        public void showOwned()
        {
            rentals.updateList(GUIMethods.filterByOwner(user));
        }

    }

    /**
     * Screen that shows a list of users
     */
    private class UserListScreen extends CoolComponents.CoolPanelHalf
    {
        private CoolComponents.CoolList users = new CoolComponents.CoolList();
        private CoolComponents.CoolButton view = new CoolComponents.CoolButton("View");
        private CoolComponents.CoolButton verify = new CoolComponents.CoolButton("Verify");

        /**
         * @param i = 1 : the chosen user is displayed in separate screen, otherwise the screen has a verify button on it to approve requests
         */
        UserListScreen(int i)
        {
            CoolComponents.CoolLabel header = new CoolComponents.CoolLabel("Choose a User");
            header.setSize(300, 40);
            header.setFont(fontSize(30));
            header.setLocation(headerPoint.x + 10, headerPoint.y);
            this.add(header);

            view.setLocation(rightButtonPoint);
            verify.setLocation(rightButtonPoint.x-50, rightButtonPoint.y);
            verify.setSize(verify.getWidth()+50,verify.getHeight());

            String[] empty = {""};
            users.addList(empty);

            this.setInnerPanel(users);
            if(i==1)
            {
                this.add(view);
            }
            else
            {
                this.add(verify);
            }
        }

        /**
         * loads all the users in the list
         */
        public void showAll()
        {
            users.updateList(User.getAll().toArray(new String[0]));
        }

        /**
         * loads only unverified users in the list
         */
        public void showUnverified()
        {
            users.updateList(User.findAllWith("activated", "no").toArray(new String[0]));
        }

        /**
         * loads only users who requested Provider status in the list
         */
        public void showProviders()
        {
            users.updateList(User.findAllWith("provider", "no").toArray(new String[0]));
        }
    }

    /**
     * Screen that shows all of a user's reservations
     */
    private class ReservationsList extends CoolComponents.CoolPanelHalf
    {
        private CoolComponents.CoolList rentals = new CoolComponents.CoolList();

        ReservationsList()
        {
            CoolComponents.CoolLabel header = new CoolComponents.CoolLabel("Your Reservations");
            header.setSize(300, 40);
            header.setFont(fontSize(30));
            header.setLocation(headerPoint.x + 10, headerPoint.y);
            this.add(header);


            String[] empty = {""};
            rentals.addList(empty);

            this.setInnerPanel(rentals);
        }

        /**
         * the name is bad, i just forgot to change it, it loads all the reservations for a specific user
         */
        public void showOwned()
        {
            rentals.updateList(GUIMethods.getReservationsFor(user));
        }
    }



    //the only easy thing in this entire file

    /**
     * Custom Panel with methods to add buttons and a title in a formatted way
     */
    private class EasyMenu extends CoolComponents.CoolPanelHalf
    {
        private JPanel inner = new JPanel();

        private EasyMenu()
        {
            BoxLayout box = new BoxLayout(inner, BoxLayout.Y_AXIS);
            inner.setLayout(box);


            this.setInnerPanel(inner);
        }

        protected void addTitle(String title)
        {
            CoolComponents.CoolLabel header = new CoolComponents.CoolLabel(title);
            header.setSize(300, 40);
            header.setFont(fontSize(30));
            header.setLocation(headerPoint.x + 10, headerPoint.y);
            this.add(header);
        }

        protected void addButton(CoolComponents.CoolButton button)
        {
            button.setAlignmentX(0.5F);
            inner.add((Box.createRigidArea(new Dimension(0, 20))));
            inner.add(button);
        }
    }

    /**
     * The main menu screen
     */
    private class MainMenuScreen extends EasyMenu
    {
        private CoolComponents.CoolButton account = new CoolComponents.CoolButton("Account Settings");
        private CoolComponents.CoolButton messages = new CoolComponents.CoolButton("Messages");
        private CoolComponents.CoolButton rentals = new CoolComponents.CoolButton("Rentals");
        private CoolComponents.CoolButton provider = new CoolComponents.CoolButton("Rental Provider Mode");
        private CoolComponents.CoolButton admin = new CoolComponents.CoolButton("Admin Mode");

        private MainMenuScreen()
        {
            addTitle("Main Menu");

            addButton(account);
            addButton(messages);
            addButton(rentals);
            addButton(provider);
            addButton(admin);

            this.back.setEnabled(false);

        }
    }

    /**
     * menu with message options
     */
    private class MessageMenuScreen extends EasyMenu
    {
        private CoolComponents.CoolButton send = new CoolComponents.CoolButton("Send Message");
        private CoolComponents.CoolButton read = new CoolComponents.CoolButton("Read Messages");

        private MessageMenuScreen()
        {
            addTitle("Messages");

            addButton(send);
            addButton(read);
        }
    }

    /**
     * menu with rental options
     */
    private class RentalMenuScreen extends EasyMenu
    {
        CoolComponents.CoolButton find = new CoolComponents.CoolButton("Find Rentals");
        CoolComponents.CoolButton reservations = new CoolComponents.CoolButton("Your Reservations");

        RentalMenuScreen()
        {
            addTitle("Rentals");

            addButton(find);
            addButton(reservations);
        }
    }

    /**
     * menu with provider options
     */
    private class ProviderMenuScreen extends EasyMenu
    {
        private CoolComponents.CoolButton view = new CoolComponents.CoolButton("Your Listings");
        private CoolComponents.CoolButton add = new CoolComponents.CoolButton("Add Listing");

        ProviderMenuScreen()
        {
            addTitle("Provider Mode");

            addButton(view);
            addButton(add);
        }
    }

    /**
     * the menu for admin options
     */
    private class AdminMenuScreen extends EasyMenu
    {
        private CoolComponents.CoolButton users = new CoolComponents.CoolButton("View Users");
        private CoolComponents.CoolButton listings = new CoolComponents.CoolButton("View Listings");
        private CoolComponents.CoolButton uRequests = new CoolComponents.CoolButton("Verify Users");
        private CoolComponents.CoolButton pRequests = new CoolComponents.CoolButton("Verify Providers");

        AdminMenuScreen()
        {
            addTitle("Admin Mode");

            addButton(users);
            addButton(listings);
            addButton(uRequests);
            addButton(pRequests);
        }
    }


    /**
     * allows the user to change the picture of one of their rentals
     * @param rental
     */
    private void changeRentalPic(Rental rental)
    {
        CoolComponents.CoolIconChooser chooser = new CoolComponents.CoolIconChooser();

        int ans = chooser.showOpenDialog(cPane);

        if (ans == JFileChooser.APPROVE_OPTION)
        {
            String newFilename = chooser.getSelectedFile().getAbsolutePath();
            if (rental.contains("icon"))
            {
                rental.setCharacteristic("icon", newFilename);
            }
            else
            {
                rental.addCharacteristic("icon", newFilename);
            }
            JOptionPane.showMessageDialog(cPane,"Picture Changed");

        }
        rentalScreen.changeRental(rental);
        rentalScreen.changeMode(1);

    }

    /**
     * allows the user to change their profile picture
     */
    private void changeUserPic()
    {
        CoolComponents.CoolIconChooser chooser = new CoolComponents.CoolIconChooser();

        int ans = chooser.showOpenDialog(cPane);

        if (ans == JFileChooser.APPROVE_OPTION)
        {
            String newFilename = chooser.getSelectedFile().getAbsolutePath();
            if (user.has("icon"))
            {
                user.setCredential("icon", newFilename);
            }
            else
            {
                user.addCredential("icon", newFilename);
            }
            JOptionPane.showMessageDialog(cPane,"Profile Picture Changed");
            userScreen.changeUser(user);
            userScreen.changeMode(1);

        }

    }

    /**
     * Handles the creation of new rentals, if the user enters a name, an empty rental is created and the user is forced into it's edit screen
     * @return false if the user cancels out of naming the rental,else true if the rental is created
     */
    private boolean createRental()
    {

        Rental rental = new Rental(Rental.getAvailableID());

        boolean go = false;
        String name = "";

        while (!go)
        {
            name = JOptionPane.showInputDialog(cPane,"Enter A Name for your Listing\n(This can't be changed later)");

            if (name == null)
            {
                return false;
            }

            if (!name.isEmpty())
            {
                go = true;
            }
            else
            {
                JOptionPane.showMessageDialog(cPane,"Your Listing needs a name...");
            }
        }

        rental.addCharacteristic("name",name);
        rental.addCharacteristic("location","");
        rental.addCharacteristic("type","");
        rental.addCharacteristic("price","0");
        rental.addCharacteristic("m2","0");
        rental.addCharacteristic("wifi","no");
        rental.addCharacteristic("pool","no");
        rental.addCharacteristic("parking","no");
        rental.addCharacteristic("owner",user.getUserName());

        rental.updateCharacteristics();

        this.rentalScreen.changeRental(rental);
        this.rentalScreen.changeMode(1);
        card.show(cPane,"rental screen");

        Date.addDate(rental.getId(),user.getUserName(),new Date(1,1,2021));

        return true;
    }

    /**
     * Checks the info given on a rental for validity and makes the changes if the info is ok
     * @return true if the rental was edited successfully, false otherwise
     */
    private boolean editRental()
    {
        String location = rentalScreen.rentalView.location.textfield.getText();
        String type = rentalScreen.rentalView.type.textfield.getText();
        String sizeString = rentalScreen.rentalView.size.textfield.getText();
        int size;
        String priceString = rentalScreen.rentalView.price.textfield.getText();
        int price;
        boolean wifi = rentalScreen.rentalView.wifi.checkBox.getState();
        boolean pool = rentalScreen.rentalView.pool.checkBox.getState();
        boolean parking = rentalScreen.rentalView.parking.checkBox.getState();


        if (location.isEmpty())
        {
            JOptionPane.showMessageDialog(cPane,"Location can't be left empty");
            return false;
        }

        if (type.isEmpty())
        {
            JOptionPane.showMessageDialog(cPane,"Type can't be left empty");
        }

        if(!isInt(sizeString))
        {
            JOptionPane.showMessageDialog(cPane,"Size should be an integer");
            return false;
        }
        else
        {
            size = Integer.parseInt(sizeString);
        }

        if (!isInt(priceString))
        {
            JOptionPane.showMessageDialog(cPane,"Price should be an integer");
            return false;
        }
        else
        {
            price = Integer.parseInt(priceString);
        }

        if (price <= 0)
        {
            JOptionPane.showMessageDialog(cPane,"Your rental price can't be that low...");
            return false;
        }
        if (price >= 10000)
        {
            JOptionPane.showMessageDialog(cPane,"Your rental is too expensive for our system...\n(Price has to be less than 10000)");
            return false;
        }

        if (size <= 0)
        {
            JOptionPane.showMessageDialog(cPane,"Your rental can't be that small...");
            return false;
        }

        if (size >= 10000)
        {
            JOptionPane.showMessageDialog(cPane,"Your rental is too big for our system...\n(Size has to be less than 10000)");
            return false;
        }

        Rental rental = rentalScreen.rental;

        rental.setCharacteristic("type",type);
        rental.setCharacteristic("location",location);
        rental.setCharacteristic("price",priceString);
        rental.setCharacteristic("m2",sizeString);
        rental.setCharacteristic("wifi",GUIMethods.toYesNo(wifi));
        rental.setCharacteristic("parking",GUIMethods.toYesNo(parking));
        rental.setCharacteristic("pool",GUIMethods.toYesNo(pool));

        rental.updateCharacteristics();


        JOptionPane.showMessageDialog(cPane,"Rental Successfully Edited");
        return true;
    }

    /**
     * Checks the new account info the user is trying to set for invalid inputs
     * @return true if the new info is fine and has been successfully changed,false otherwise
     */
    private boolean accountChanges()
    {
        String newEmail = userScreen.accountView.email.textfield.getText();
        String newPassword = userScreen.accountView.password.textfield.getText();

        if (!User.checkCredentialAvailability("email",newEmail) && !user.getCredential("email").equals(newEmail))
        {
            JOptionPane.showMessageDialog(cPane,"The email you are trying to enter is already in use by another user");
            return false;
        }
        if (newEmail.contains(" ") || !newEmail.contains("@") || !newEmail.contains(".") || newEmail.contains("\n"))
        {
            JOptionPane.showMessageDialog(cPane,"Invalid email address");
            return false;
        }

        if (newPassword.equals("password") || newPassword.equals("Password"))
        {
            JOptionPane.showMessageDialog(cPane,"Your password can't be '" + newPassword + "'");
            return false;
        }
        if (newPassword.contains(" ") || newPassword.contains("\n"))
        {
            JOptionPane.showMessageDialog(cPane,"Your password has to be ONE WORD");
            return false;
        }

        user.setCredential("email",newEmail);
        user.setCredential("password",newPassword);
        user.updateCredentials();

        return true;
    }

    /**
     * Makes sure all the filters are set properly
     * @return true if they are set properly, false otherwise
     */
    private boolean checkFilters()
    {
        Date date1 = this.searchScreenBasic.basic.date1.getDate();
        Date date2 = this.searchScreenBasic.basic.date2.getDate();

        if (Date.compare(date2,date1) != 1)
        {
            JOptionPane.showMessageDialog(cPane,"Dates are incorrect");
            return false;
        }

        String priceMin = this.searchScreenAdvanced.advanced.price.min.getText();
        String priceMax = this.searchScreenAdvanced.advanced.price.max.getText();
        String sizeMin = this.searchScreenAdvanced.advanced.size.min.getText();
        String sizeMax = this.searchScreenAdvanced.advanced.size.max.getText();

        if (!isInt(priceMin) && !priceMin.equals("min"))
        {
            JOptionPane.showMessageDialog(cPane,"Min Price should be an integer or 'min' if you don't want to set a minimum price");
            return false;
        }

        if (!isInt(priceMax) && !priceMax.equals("max"))
        {
            JOptionPane.showMessageDialog(cPane,"Max Price should be an integer or 'max' if you don't want to set a maximum price");
            return false;
        }

        if (!isInt(sizeMin) && !sizeMin.equals("min"))
        {
            JOptionPane.showMessageDialog(cPane,"Min Size should be an integer or 'min' if you don't want to set a minimum size");
            return false;
        }

        if (!isInt(sizeMax) && !sizeMax.equals("max"))
        {
            JOptionPane.showMessageDialog(cPane,"Max Size should be an integer or 'max' if you don't want to set a maximum size");
            return false;
        }

        return true;
    }

    /**
     * Gets the filters from the filter screen and packs them into one object
     * @return the filters object
     */
    private GUIMethods.Filters getFilters()
    {
        GUIMethods.Filters filters = new GUIMethods.Filters();
        filters.setFromDate(this.searchScreenBasic.basic.date1.getDate());
        filters.setToDate(this.searchScreenBasic.basic.date2.getDate());

        String type = this.searchScreenBasic.basic.type.textfield.getText();

        if (type.equals("Don't Care"))
        {
            filters.setType(null);
        }
        else
        {
            filters.setType(type);
        }

        String location = this.searchScreenBasic.basic.location.textfield.getText();

        if (location.equals("Don't Care"))
        {
            filters.setLocation(null);
        }
        else
        {
            filters.setLocation(location);
        }

        String priceMin = this.searchScreenAdvanced.advanced.price.min.getText();

        if (priceMin.equals("min"))
        {
            filters.setMinPrice(0);
        }
        else
        {
            filters.setMinPrice(Integer.parseInt(priceMin));
        }

        String priceMax = this.searchScreenAdvanced.advanced.price.max.getText();

        if (priceMax.equals("max"))
        {
            filters.setMaxPrice(10000);
        }
        else
        {
            filters.setMaxPrice(Integer.parseInt(priceMax));
        }

        String sizeMin = this.searchScreenAdvanced.advanced.size.min.getText();

        if (sizeMin.equals("min"))
        {
            filters.setMinSize(0);
        }
        else
        {
            filters.setMinSize(Integer.parseInt(sizeMin));
        }

        String sizeMax = this.searchScreenAdvanced.advanced.size.max.getText();

        if (sizeMax.equals("max"))
        {
            filters.setMaxSize(10000);
        }
        else
        {
            filters.setMaxSize(Integer.parseInt(sizeMax));
        }

        filters.setWifi(searchScreenAdvanced.advanced.wifi.checkBox.getState());
        filters.setPool(searchScreenAdvanced.advanced.pool.checkBox.getState());
        filters.setParking(searchScreenAdvanced.advanced.parking.checkBox.getState());


        return filters;
    }

    /**
     * Checks if a string contains an Integer value
     * @param string the string to be checked
     * @return true if it is an Integer, false otherwise
     */
    private static boolean isInt(String string)
    {
        int tester;
        try
        {
            tester = Integer.parseInt(string);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    /**
     * Helps quickly change the size of the font
     *
     * @param size the size of letters
     * @return the default font but with the specified size
     */
    private static Font fontSize(int size)
    {
        return new Font("", Font.BOLD, size);
    }

    /**
     * Helps put rows in the right place
     *
     * @param y y-axis position of the row
     * @return Point with coordinates (5,y)
     */
    private static Point rowAt(int y)
    {
        return new Point(5, y);
    }

    /**
     * returns the completeness status of this window
     * @return
     */
    public int getDone()
    {
        return done;
    }

}
