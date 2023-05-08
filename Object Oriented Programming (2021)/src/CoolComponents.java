import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is an attempt at simplifying swing and creating standard components for use in the main GUI
 * I would have spent more time here if I had the time (and knew how annoying swing could be)
 * (almost) all the components in here are Very Cool
 */
public class CoolComponents
{
    /**
     * standard size for components containing text (made thing worse)
     */
    static Dimension txtFieldSize = new Dimension(200, 20);
    /**
     * standard font or at least font size (i needed to change it for basically every component)
     */
    static Font txtFieldFont = new Font("", Font.BOLD, 15);
    /**
     * a cool color I like for backgrounds
     */
    static Color preferredBGColor = new Color(0xf7f7f7);
    /**
     * a cool border that is used by basically every component
     */
    static Border myBorder = BorderFactory.createLineBorder(Color.BLACK, 2, true);



    /**
     * Custom JFrame with cardLayout
     */
    public static class CoolFrame extends JFrame
    {
        public Container cPane = this.getContentPane();
        public CardLayout card = new CardLayout();

        /**
         * Constructor for JFrame
         */
        public CoolFrame()
        {
            this.setSize(500, 500);
            this.setResizable(false);
            this.setLocationRelativeTo(null);

            cPane.setLayout(card);
        }

        /**
         * Adds a panel to the card layout
         * (I FORGOT TO USE THIS)
         * @param panel
         */
        public void addPanel(JPanel panel)
        {
            cPane.add(panel);
        }
    }



    /**
     * Custom JPanel
     */
    public static class CoolPanel extends JPanel
    {
        public JPanel innerPanel;
        protected CoolButton back = new CoolButton("BACK");
        ;

        /**
         * Constructor for custom JPanel
         */
        public CoolPanel()
        {
            this.setBackground(new Color(0xF6E8CB));
            this.setLayout(null);
            back.setLocation(10, 395);
            this.add(back);
        }

        /**
         * Sets the inner panel displayed (This version doesn't really do anything, it's overridden further below)
         *
         * @param innerPanel the new innerPanel
         */
        public void setInnerPanel(JPanel innerPanel)
        {
            this.innerPanel = innerPanel;
        }

    }

    /**
     * Like cool panel but has a big inner panel
     */
    public static class CoolPanelFull extends CoolPanel
    {
        /**
         * Constructor
         */
        public CoolPanelFull()
        {
            super();
        }

        /**
         * Sets an inner panel and adds it on the panel (This version makes the inner panel bigger)
         *
         * @param innerPanel the new innerPanel
         */
        @Override
        public void setInnerPanel(JPanel innerPanel)
        {
            this.innerPanel = innerPanel;
            this.innerPanel.setBounds(10, 10, 465, 380);
            this.innerPanel.setBorder(myBorder);
            this.innerPanel.setBackground(preferredBGColor);


            this.add(innerPanel);
        }
    }

    /**
     * Like CoolPanel but with smaller inner panel
     */
    public static class CoolPanelHalf extends CoolPanel
    {
        /**
         * Constructor
         */
        public CoolPanelHalf()
        {
            super();
        }

        /**
         * Sets an inner panel and adds it on the panel (This version makes the inner panel smaller)
         *
         * @param innerPanel the new innerPanel
         */
        @Override
        public void setInnerPanel(JPanel innerPanel)
        {
            this.innerPanel = innerPanel;
            this.innerPanel.setBounds(10, 50, 465, 340);
            this.innerPanel.setBorder(myBorder);
            this.innerPanel.setBackground(preferredBGColor);
            this.add(innerPanel);
        }

    }





    /**
     * Custom JPanel with an image and a large "Title" (either a username or the name of a rental)
     */
    public static class IDRow extends JPanel
    {
        protected CoolIcon icon;
        protected CoolLabel name;
        protected CoolButton change = new CoolButton("");

        /**
         * Constructor for custom JPanel
         *
         * @param defIcon the filename of the image that shows (user should be able to change this)
         * @param defText the text that shows on the "Title" (user shouldn't be able to change this)
         */
        IDRow(String defIcon, String defText)
        {
            this.setBackground(null);
            this.setOpaque(true);
            this.setSize(458, 155);
            this.setLayout(null);

            icon = new CoolIcon(defIcon);
            name = new CoolLabel(defText);

            name.setSize(450-160, 30);
            name.setFont(new Font("", Font.BOLD, 30));

            icon.setLocation(5, 5);
            name.setLocation(160, 5);

            this.add(name);
            this.add(icon);

            change.setIcon(new ImageIcon("./pictures/change.png"));
            change.setBounds(160,155-32,32,32);
            change.setBackground(null);


            this.add(change);
        }
    }


    /**
     * Custom JPanel for the purposes of showing a textfield with al label explaining its contents
     */
    public static class CoolRow extends JPanel
    {
        protected CoolLabel label;
        protected CoolTextField textfield;

        /**
         * constructor for custom JPanel
         *
         * @param labelText     the text on the label
         * @param textfieldText the default text on the textfield
         */
        public CoolRow(String labelText, String textfieldText)
        {
            this.setBackground(null);
            this.setLayout(null);
            this.setSize(458, 30);

            label = new CoolLabel(labelText);
            textfield = new CoolTextField(textfieldText);

            label.setLocation(10, 10);
            textfield.setLocation(210, 10);

            textfield.setEditable(false);

            this.add(label);
            this.add(textfield);
        }

        /**
         * This allows the user to change the content of the textfield
         */
        public void enableEditing(boolean enable)
        {
            textfield.setEditable(enable);
        }
    }

    /**
     * Like CoolRow But Larger
     */
    public static class LargeRow extends CoolRow
    {
        /**
         * constructor for custom JPanel
         *
         * @param labelText     the text on the label
         * @param textfieldText the default text on the textfield
         */
        public LargeRow(String labelText, String textfieldText)
        {
            super(labelText, textfieldText);
            this.setSize(450, 35);
            this.label.setFont(new Font("", Font.BOLD, 20));
            this.label.setLocation(10, 7);

            this.textfield.setFont(new Font("", Font.BOLD, 20));
            this.textfield.setSize(200, 35);
            this.textfield.setLocation(100, 0);
        }
    }

    /**
     * Like CoolRow but narrower
     */
    public static class NarrowRow extends CoolRow
    {

        /**
         * constructor for custom JPanel
         *
         * @param labelText     the text on the label
         * @param textfieldText the default text on the textfield
         */
        public NarrowRow(String labelText, String textfieldText)
        {
            super(labelText, textfieldText);

            this.setSize(200, 30);
            this.textfield.setLocation(100, 10);
            this.textfield.setSize(100, textfield.getHeight());
        }
    }

    /**
     * Custom JPanel with a label and a checkbox
     */
    public static class CheckRow extends JPanel
    {
        protected CoolLabel label;
        protected CoolCheckBox checkBox;

        public CheckRow(String labelText, Boolean check)
        {
            this.setBackground(null);
            this.setLayout(null);
            this.setSize(200, 30);

            label = new CoolLabel(labelText);
            label.setSize(60, label.getHeight());
            checkBox = new CoolCheckBox(check);

            label.setLocation(10, 10);
            checkBox.setLocation(101, 10);

            checkBox.setClickable(false);

            this.add(label);
            this.add(checkBox);
        }

        /**
         * This allows the user to click on the button
         */
        public void enableEditing()
        {
            checkBox.setClickable(true);
        }

    }

    /**
     * Custom JPanel for setting a range of integers
     */
    public static class MinMaxRow extends JPanel
    {
        protected CoolLabel label;
        protected CoolTextField min;
        protected CoolTextField max;

        public MinMaxRow(String text)
        {
            this.setBackground(null);
            this.setLayout(null);
            this.setSize(458, 30);

            label = new CoolLabel(text);
            min = new CoolTextField("min");
            max = new CoolTextField("max");

            label.setSize(80, 20);
            min.setSize(80, 20);
            max.setSize(80, 20);

            label.setLocation(10, 10);
            min.setLocation(100, 10);
            max.setLocation(200, 10);

            this.add(label);
            this.add(min);
            this.add(max);
        }
    }




    /**
     * custom JFileChooser
     */
    public static class CoolIconChooser extends JFileChooser
    {
        public CoolIconChooser()
        {
            super("./pictures");
            this.setFileFilter(new FileNameExtensionFilter("Image Files","png","jpg","jpeg"));
        }
    }

    /**
     * Custom JPanel with a list in it
     */
    public static class CoolList extends JPanel
    {
        JList list;
        public CoolList()
        {
            this.setBackground(null);

        }

        /**
         * it is better for the list to be added into the panel afterwards (i don't know why swing doesn't cooperate)
         * this method does exactly that
         * @param contents
         */
        public void addList(String[] contents)
        {
            list = new JList(contents);
            list.setFixedCellHeight(25);
            list.setFixedCellWidth(450);

            JScrollPane listScroller = new JScrollPane(list,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            listScroller.setPreferredSize(new Dimension(458, 330));

            this.add(listScroller);
        }

        public void updateList(String[] contents)
        {
            list.setListData(contents);
        }
    }


    /**
     * It is NOT cool because of box layout
     * this manager ruins everything it is involved in
     * It even ruined the border of its container
     * Look bellow to see how I fixed that
     */
    public static class NotCoolDatePicker extends JPanel
    {
        EasyComboBox day = new EasyComboBox("Day", 1, 30);
        EasyComboBox month = new EasyComboBox("Month", 1, 12);
        EasyComboBox year = new EasyComboBox("Year", 2022, 2100);

        public NotCoolDatePicker()
        {
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setSize(200, 60);
            this.setBackground(null);

            this.add(day);
            this.add(month);
            this.add(year);
        }

        private class EasyComboBox extends JPanel
        {
            JLabel title = new JLabel();
            JComboBox<Integer> box = new JComboBox<>();

            public EasyComboBox(String text, int from, int to)
            {
                BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

                this.setLayout(boxLayout);

                title.setText(text);
                title.setBackground(null);


                for (int i = from; i <= to; i++)
                {
                    box.addItem(i);
                }

                box.setFont(new Font("", Font.BOLD, 25));
                box.setBackground(null);

                this.add(title);
                this.add(box);
            }
        }

        /**
         * extracts the picked content and converts it to a date
         * @return
         */
        public Date getDate()
        {
            int d = (Integer) day.box.getSelectedItem();
            int m = (Integer) month.box.getSelectedItem();
            int y = (Integer) year.box.getSelectedItem();

            return new Date(d,m,y);
        }

    }

    /**
     * Custom JPanel that helps with picking dates
     */
    public static class CoolDatePicker extends JPanel
    {
        NotCoolDatePicker date = new NotCoolDatePicker();

        public CoolDatePicker()
        {
            this.setSize(204, 64);
            this.setLayout(null);
            this.setBorder(myBorder);
            date.setLocation(2, 2);
            this.add(date);
        }

        public Date getDate()
        {
            return date.getDate();
        }
    }




    /**
     * Custom JLabel to display images
     */
    public static class CoolIcon extends JLabel
    {
        private ImageIcon icon;

        /**
         * Constructor for the Custom JLabel
         *
         * @param defaultFilename the filename of the image that shows up by default
         */
        public CoolIcon(String defaultFilename)
        {
            this.setBorder(myBorder);
            this.icon = new ImageIcon(defaultFilename);
            this.icon.setImage(this.icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            this.setIcon(icon);
            this.setSize(150, 150);
        }

        /**
         * Changes the image displayed on the label
         *
         * @param newFilename filename of the new image
         */
        public void changeIcon(String newFilename)
        {
            this.icon = new ImageIcon(newFilename);
            this.icon.setImage(this.icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            this.setIcon(icon);

        }
    }

    /**
     * Custom JTextArea
     */
    public static class CoolTextArea extends JTextArea
    {
        /**
         * Constructor for custom JTextArea
         */
        public CoolTextArea()
        {
            this.setBounds(2, 2, 460, 334);
            this.setFont(txtFieldFont);
            this.setLineWrap(true);
        }
    }

    /**
     * Custom JTextField
     */
    public static class CoolTextField extends JTextField
    {
        /**
         * Constructor for custom JTextField
         * @param text the default text on the TextField
         */
        public CoolTextField(String text)
        {
            this.setBorder(myBorder);
            this.setText(text);
            this.setSize(txtFieldSize);
            this.setFont(txtFieldFont);

        }
    }

    /**
     * JButton implementation of a checkbox that isn't as terrible as JCheckBox
     */
    public static class CoolCheckBox extends JButton implements ActionListener
    {
        private boolean state;
        private boolean enabled;
        private static ImageIcon check = new ImageIcon("./pictures/check.png");
        private static ImageIcon cross = new ImageIcon("./pictures/cross.png");

        /**
         * @return true if checked
         * false if crossed
         */
        public boolean getState()
        {
            return state;
        }

        /**
         * @param state if true the button shows a check, if false the button shows a cross
         */
        public void setState(boolean state)
        {
            this.state = state;
            if (state)
            {
                this.setIcon(check);
            }
            else
            {
                this.setIcon(cross);
            }
        }

        /**
         * Disables the button from changing states
         *
         * @param enabled
         */
        public void setClickable(boolean enabled)
        {
            this.enabled = enabled;
            if (this.enabled)
            {
                this.setBackground(Color.WHITE);
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
            }
            else
            {
                this.setOpaque(true);
                this.setBackground(preferredBGColor);
                this.setBorder(null);
            }
        }

        /**
         * @param checked the default state of the checkbox
         */
        public CoolCheckBox(boolean checked)
        {
            this.state = checked;
            this.setClickable(true);

            this.setSize(16, 16);

            this.addActionListener(this);

            if (state)
            {
                this.setIcon(check);
            }
            else
            {
                this.setIcon(cross);
            }
        }

        /**
         * This action listener makes sure the button changes states when pressed
         * (Also it is the only action listener in this project that is independent, self-sustained and somewhat well-designed)
         */
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (enabled)
            {
                if (state)
                {
                    this.setIcon(cross);
                }
                else
                {
                    this.setIcon(check);
                }

                state = !state;
            }

        }
    }

    /**
     * Custom JLabel
     */
    public static class CoolLabel extends JLabel
    {
        /**
         * Constructor for custom JLabel
         *
         * @param text the text on the label
         */
        public CoolLabel(String text)
        {
            super(text);
            this.setSize(txtFieldSize);
            this.setOpaque(true);
            this.setFont(txtFieldFont);
            this.setForeground(new Color(0x272727));
            this.setBackground(null);
        }
    }

    /**
     * Custom JButton
     */
    public static class CoolButton extends JButton
    {
        /**
         * Constructor for custom JButton
         * @param text the text on the button
         */
        public CoolButton(String text)
        {
            super(text);
            this.setBackground(new Color(0xABD6CC));
            this.setFont(new Font("", Font.BOLD, 20));
            this.setSize(100, 60);
            this.setFocusPainted(false);
            this.setBorder(myBorder);
        }
    }


}
