import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WelcomeWindow extends JFrame
{
    private WelcomeListener welcomeListener = new WelcomeListener();

    private LoadingPanel loadingPanel = new LoadingPanel();
    private LoginPanel loginPanel = new LoginPanel();
    private SignupPanel signupPanel = new SignupPanel();

    private Container cPane = getContentPane();
    private CardLayout card = new CardLayout();

    private User user = null;

    private int done = 0;




    public WelcomeWindow()
    {
        cPane.setLayout(card);

        this.setTitle("FireBNB" + '\u2122');
        this.setIconImage(new ImageIcon("./pictures/fire.png").getImage());
        this.setSize(300,400);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        cPane.add(loadingPanel);
        cPane.add(loginPanel);
        cPane.add(signupPanel);


        this.setVisible(true);

    }





    private class LoadingPanel extends JPanel
    {
        private JLabel loading = new JLabel("Loading");

        private LoadingPanel()
        {
            this.setLayout(null);

            JLabel welcome = new JLabel("Welcome to FireBNB" + '\u2122');

            welcome.setBounds(75,150,150,20);
            loading.setBounds(75,190,150,20);

            this.add(welcome);
            this.add(loading);

        }

        private void addDot()
        {
            loading.setText(loading.getText() + ".");
        }
    }



    private class LoginPanel extends JPanel
    {
        private JTextField username = new JTextField();
        private JTextField password = new JPasswordField();
        private JButton loginButton = new JButton("Log In");
        private JButton signupButton = new JButton("Sign Up");

        private LoginPanel()
        {
            this.setLayout(null);

            username.setBounds(100,110,100,20);
            password.setBounds(100,150,100,20);
            loginButton.setBounds(170,190,100,40);
            signupButton.setBounds(10,200,100,20);

            loginButton.addActionListener(welcomeListener);
            signupButton.addActionListener(welcomeListener);


            this.add(username);
            this.add(addLabel(username,"Username"));
            this.add(password);
            this.add(addLabel(password,"Password"));
            this.add(loginButton);
            this.add(signupButton);
        }


    }

    private class SignupPanel extends JPanel
    {
        private JTextField username = new JTextField();
        private JTextField password = new JPasswordField();
        private JTextField email = new JTextField();
        private JButton complete = new JButton("Create Account");

        private SignupPanel()
        {
            this.setLayout(null);

            username.setBounds(100,80,100,20);
            password.setBounds(100,120,100,20);
            email.setBounds(100,160,100,20);
            complete.setBounds(140,210,130,40);

            complete.addActionListener(welcomeListener);

            this.add(username);
            this.add(addLabel(username,"Username"));
            this.add(password);
            this.add(addLabel(password,"Password"));
            this.add(email);
            this.add(addLabel(email,"e-mail"));
            this.add(complete);
        }
    }




    private class WelcomeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == loginPanel.loginButton)
            {
                String username = loginPanel.username.getText();
                String password = loginPanel.password.getText();

                boolean success = GUIMethods.loginAttempt(username,password);

                if(success)
                {
                    user = new User(username);

                    if (!user.is("activated"))
                    {
                        JOptionPane.showMessageDialog(cPane,"Your account has not been activated yet\nplease wait until an admin activates it,thank you");
                        close(-1);
                    }
                    close(1);

                }
                else
                {
                    JOptionPane.showMessageDialog(cPane,"Incorrect Username or Password");
                }
            }

            if (e.getSource() == loginPanel.signupButton)
            {
                card.next(cPane);
            }

            if (e.getSource() == signupPanel.complete)
            {
                String username = signupPanel.username.getText();

                if (!User.checkUsernameAvailability(username))
                {
                    JOptionPane.showMessageDialog(cPane,"Username not Available");
                    return;
                }
                if (username.isEmpty())
                {
                    JOptionPane.showMessageDialog(cPane,"Username cannot be Empty");
                    return;
                }
                if (username.contains(" "))
                {
                    JOptionPane.showMessageDialog(cPane,"Username has to be One Word");
                    return;
                }

                String password = signupPanel.password.getText();

                if (password.isEmpty())
                {
                    JOptionPane.showMessageDialog(cPane,"Password cannot be Empty");
                    return;
                }
                if (password.contains(" "))
                {
                    JOptionPane.showMessageDialog(cPane,"Password has to be One Word");
                    return;
                }

                String email = signupPanel.email.getText();

                if (!User.checkCredentialAvailability("email",email))
                {
                    JOptionPane.showMessageDialog(cPane,"Email Already in use by another account");
                    return;
                }
                if (email.isEmpty() || email.contains(" ") || !email.contains("@") || !email.contains("."))
                {
                    JOptionPane.showMessageDialog(cPane,"Invalid email address,(write an email that at least looks real)");
                    return;
                }

                user = GUIMethods.createAccount(username,password,email);

                JOptionPane.showMessageDialog(cPane,"Your account has been created\nplease wait until an Admin approves its activation");

                close(2);
            }

        }
    }

    private class MyWindowListener extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            done = -1;
        }

    }



    private static JLabel addLabel(Component component, String text)
    {
        JLabel label = new JLabel(text);
        int x = component.getX() - 80;
        int y = component.getY();
        int width = component.getWidth();
        int height = component.getHeight();

        label.setBounds(x,y,width,height);
        return label;
    }

    public void addDot()
    {
        loadingPanel.addDot();
    }

    public void changePanel()
    {
        card.next(cPane);
    }

    public User getUser()
    {
        return user;
    }

    public void close(int i)
    {
        done = i;
        this.dispose();
    }

    public int getDone()
    {
        return done;
    }


}

