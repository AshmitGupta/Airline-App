package ui;

import model.BookingList;
import model.Event;
import model.EventLog;
import model.Route;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the main application window
public class AirlineGUI extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final String VIEW_BOOKING_STRING = "View Bookings";
    private static final String VIEW_FARES_STRING = "View Fares";
    private static final String ADD_BOOKING_STRING = "Add Bookings";
    private static final String REMOVE_BOOKING_STRING = "Remove Bookings";
    private static final String SAVE_BOOKING_STRING = "Save Bookings";
    private static final String LOAD_BOOKING_STRING = "Load Bookings";

    private BookingList bookingList;

    protected JFrame desktop;
    private JPanel controlPanel;
    private JPanel imagePanel;
    private JPanel innerContainer1;
    private JPanel innerContainer2;
    private JPanel container;

    private JComboBox comboBox;
    private JTextField textField;
    private JInternalFrame removeFrame;

    private JButton viewBookingsButton;
    private JButton viewFaresButton;
    private JButton addBookingsButton;
    private JButton removeBookingsButton;
    private JButton saveButton;
    private JButton loadButton;
    private Integer integer;

    private JLabel imageLabel;
    private JsonWriter jsonWriterBookings;
    private JsonReader jsonReaderBookings;
    private String[] bookingsString = { "1) Vancouver to Toronto", "2) Vancouver to Waterloo",
            "3) Vancouver to Victoria", "4) Toronto to Waterloo", "5) Toronto to Victoria " };

    // sets up the main application Jframe and its panels (controlPanel and imagePanel)
    public AirlineGUI() {
        desktop = new JFrame();
        desktop.setBackground(Color.BLUE);
        bookingList = new BookingList();
        jsonWriterBookings = new JsonWriter("./data/bookinglist.json");
        jsonReaderBookings = new JsonReader("./data/bookinglist.json");

        innerContainer1 = new JPanel();
        //https://stackoverflow.com/questions/6325384/adding-multiple-jpanels-to-jframe
        innerContainer1.setLayout(new BoxLayout(innerContainer1, BoxLayout.X_AXIS));

        innerContainer2 = new JPanel();
        innerContainer2.setLayout(new BoxLayout(innerContainer2, BoxLayout.X_AXIS));

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(innerContainer1);
        container.add(innerContainer2);

        controlPanel = new JPanel(new GridLayout(3,3));
        imagePanel = new JPanel();
        addImage();
        setup();
        desktop.add(container);
        desktop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        desktop.setVisible(true);
        printLog(EventLog.getInstance());
        integer = 0;

    }

    // https://stackoverflow.com/questions/43547337/java-how-do-i-check-whether-a-jframe-is-closed
    public void printLog(EventLog el) {
        desktop.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                //https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
                for (Event next: el) {
                    System.out.println(next.toString() + "\n");
                }
            }

        });
    }

    // MODIFIES: controlPanel
    // EFFECTS: creates and adds all the required buttons
    public void createAndAddButtons() {
        viewBookingsButton = new JButton(VIEW_BOOKING_STRING);
        viewFaresButton = new JButton(VIEW_FARES_STRING);
        addBookingsButton = new JButton(ADD_BOOKING_STRING);
        removeBookingsButton = new JButton(REMOVE_BOOKING_STRING);
        saveButton = new JButton(SAVE_BOOKING_STRING);
        loadButton = new JButton(LOAD_BOOKING_STRING);

        controlPanel.add(viewBookingsButton);
        controlPanel.add(viewFaresButton);
        controlPanel.add(addBookingsButton);
        controlPanel.add(removeBookingsButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);

        createAndAddListeners();

        viewBookingsButton.setActionCommand(VIEW_BOOKING_STRING);
        viewFaresButton.setActionCommand(VIEW_FARES_STRING);
        addBookingsButton.setActionCommand(ADD_BOOKING_STRING);
        removeBookingsButton.setActionCommand(REMOVE_BOOKING_STRING);
        saveButton.setActionCommand(SAVE_BOOKING_STRING);
        loadButton.setActionCommand(LOAD_BOOKING_STRING);
    }

    // Displays the list of bookings in a JInternal Frame
    class DisplayListener implements ActionListener {

        private JButton button;
        private JInternalFrame frame;
        private InternalFrameListener internalFrameListener;

        // EFFECTS: constructor creates new JInternal frame
        public DisplayListener(JButton button) {
            this.button = button;
            frame = new JInternalFrame("Bookings", false, true, false, false);
        }

        // EFFECTS: deactivates viewBookings button when JInternal frame is opened and activate it when frame is closed
        public void internalDisplayListener() {
            internalFrameListener = new InternalFrameListener() {

                public void internalFrameOpened(InternalFrameEvent e) {
                    viewBookingsButton.setEnabled(false);
                }

                public void internalFrameClosing(InternalFrameEvent e) {
                }

                public void internalFrameClosed(InternalFrameEvent e) {
                    viewBookingsButton.setEnabled(true);
                }

                public void internalFrameIconified(InternalFrameEvent e) {
                }

                public void internalFrameDeiconified(InternalFrameEvent e) {
                }

                public void internalFrameActivated(InternalFrameEvent e) {
                    viewBookingsButton.setEnabled(false);
                }

                public void internalFrameDeactivated(InternalFrameEvent e) {
                }
            };
        }

        // EFFECTS: Displays the list of bookings in an internal JFrame
        @Override
        public void actionPerformed(ActionEvent e) {

            internalDisplayListener();
            frame.addInternalFrameListener(internalFrameListener);
            revertButtons();

            // Used scroll panes from https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
            JScrollPane scrollBar = new JScrollPane(bookingList.showRoutes());

            frame.add(scrollBar);
            innerContainer2.add(frame);
            frame.setVisible(true);
        }

    }

    // Displays the list of fares in a JInternal Frame
    class ViewFaresListener implements ActionListener {

        private JButton button;
        private JInternalFrame frame;
        private InternalFrameListener internalFrameListener;

        // EFFECTS: constructor creates new JInternal frame
        public ViewFaresListener(JButton button) {
            this.button = button;
            frame = new JInternalFrame("Flight Fares", false, true, false, false);
        }

        // EFFECTS: deactivates viewFares button when JInternal frame is opened and activate it when frame is closed
        public void internalFaresListener() {
            internalFrameListener = new InternalFrameListener() {

                public void internalFrameOpened(InternalFrameEvent e) {
                    viewFaresButton.setEnabled(false);
                }

                public void internalFrameClosing(InternalFrameEvent e) {
                }

                public void internalFrameClosed(InternalFrameEvent e) {
                    viewFaresButton.setEnabled(true);
                }

                public void internalFrameIconified(InternalFrameEvent e) {
                }

                public void internalFrameDeiconified(InternalFrameEvent e) {
                }

                public void internalFrameActivated(InternalFrameEvent e) {
                    viewFaresButton.setEnabled(false);
                }

                public void internalFrameDeactivated(InternalFrameEvent e) {
                }
            };
        }


        // EFFECTS: Displays the list of fares in an internal JFrame
        @Override
        public void actionPerformed(ActionEvent e) {
            internalFaresListener();
            revertButtons();

            // Using html tags instead of \n character inside JLabel has been inspired from:
            // https://stackoverflow.com/questions/1090098/newline-in-jlabel
            // Used scroll panes from https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
            JScrollPane scrollBar = new JScrollPane(new JLabel("<html>" + "Flight Fares are: <br />"
                    + "\t 1) Vancouver to Toronto   : $5000 <br />"
                    + "\t 2) Vancouver to Waterloo  : $7000  <br /> \t 3) Vancouver to Victoria  : $2000 <br />"
                    + "\t 4) Toronto to Waterloo    : $4500 <br /> \t 5) Toronto to Victoria    : $9000"
                    + "<html>"));

            frame.add(scrollBar);
            frame.addInternalFrameListener(internalFrameListener);
            innerContainer2.add(frame);
            frame.setVisible(true);
        }
    }

    // Saves the list of bookings
    class SavesListener implements ActionListener {

        private JButton button;

        public SavesListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: Saves the list of bookings
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriterBookings.open();
                jsonWriterBookings.write(bookingList);
                jsonWriterBookings.close();
                revertButtons();
                saveButton.setText("Bookings Saved");
                saveButton.setForeground(Color.green);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            }
        }
    }

    // Loads the list of bookings
    class LoadListener implements ActionListener {

        private JButton button;

        public LoadListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: loads the list of bookings
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                bookingList = jsonReaderBookings.read();
                revertButtons();
                loadButton.setText("Bookings Loaded");
                loadButton.setForeground(Color.green);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
            } catch (IOException ioException) {
                System.out.println("Unable to read from file");
            }
        }
    }

    // Displays the list of new routes and innerAdd button in a JInternal Frame
    class AddListener implements ActionListener {

        private JButton button;
        private JInternalFrame frame;
        private InternalFrameListener internalFrameListener;

        // EFFECTS: constructor creates new JInternal frame
        public AddListener(JButton button) {
            this.button = button;
            frame = new JInternalFrame("Add Bookings", false, true, false, false);
        }

        // EFFECTS: deactivates addBookings button when JInternal frame is opened and activates it when frame is closed
        public void internalAddListener() {
            internalFrameListener = new InternalFrameListener() {

                public void internalFrameOpened(InternalFrameEvent e) {
                    addBookingsButton.setEnabled(false);
                }

                public void internalFrameClosing(InternalFrameEvent e) {
                }

                public void internalFrameClosed(InternalFrameEvent e) {
                    addBookingsButton.setEnabled(true);
                }

                public void internalFrameIconified(InternalFrameEvent e) {
                }

                public void internalFrameDeiconified(InternalFrameEvent e) {
                }

                public void internalFrameActivated(InternalFrameEvent e) {
                    addBookingsButton.setEnabled(false);
                }

                public void internalFrameDeactivated(InternalFrameEvent e) {
                }
            };
        }


        // EFFECTS: Displays the available flight paths and the innerAdd button in an internal JFrame
        @Override
        public void actionPerformed(ActionEvent e) {
            internalAddListener();
            revertButtons();
            comboBox = new JComboBox(bookingsString);

            JButton innerAddButton = new JButton("Add");
            InnerAddListener innerAddListener = new InnerAddListener(innerAddButton);
            innerAddButton.addActionListener(innerAddListener);

            // Combo box from https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html#uneditable
            frame.add(comboBox);
            frame.add(innerAddButton, BorderLayout.SOUTH);
            frame.addInternalFrameListener(internalFrameListener);
            innerContainer2.add(frame);
            frame.setVisible(true);
        }
    }

    // Adds the chosen bookings to the list of bookings
    class InnerAddListener implements ActionListener {

        private JButton button;

        public InnerAddListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: adds the chosen booking to the list of bookings
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = comboBox.getSelectedIndex();
            revertButtons();
            if (index == 0) {
                bookingList.addRoute(new Route("Vancouver", "Toronto"));
            } else if (index == 1) {
                bookingList.addRoute(new Route("Vancouver", "Waterloo"));
            } else if (index == 2) {
                bookingList.addRoute(new Route("Vancouver", "Victoria"));
            } else if (index == 3) {
                bookingList.addRoute(new Route("Toronto", "Waterloo"));
            } else {
                bookingList.addRoute(new Route("Toronto", "Victoria"));
            }
        }
    }

    // Displays the text field to enter booking number and innerRemove button in a JInternal Frame
    class RemoveListener implements ActionListener {

        private JButton button;
        private Container removeContainer;
        private InternalFrameListener internalFrameListener;

        // EFFECTS: constructor creates new JInternal frame
        public RemoveListener(JButton button) {
            this.button = button;
            removeFrame = new JInternalFrame("Remove Bookings", false, true, false, false);
        }

        // EFFECTS: deactivates removeBookings button when JInternal frame is opened
        // and activates it when frame is closed
        public void internalRemoveListener() {
            internalFrameListener = new InternalFrameListener() {

                public void internalFrameOpened(InternalFrameEvent e) {
                    removeBookingsButton.setEnabled(false);
                }

                public void internalFrameClosing(InternalFrameEvent e) {
                }

                public void internalFrameClosed(InternalFrameEvent e) {
                    removeBookingsButton.setEnabled(true);
                }

                public void internalFrameIconified(InternalFrameEvent e) {
                }

                public void internalFrameDeiconified(InternalFrameEvent e) {
                }

                public void internalFrameActivated(InternalFrameEvent e) {
                    removeBookingsButton.setEnabled(false);
                }

                public void internalFrameDeactivated(InternalFrameEvent e) {
                }
            };
        }


        // EFFECTS: Displays the text field to enter booking number and the innerRemove button in an internal JFrame
        @Override
        public void actionPerformed(ActionEvent e) {
            internalRemoveListener();
            textField = new JTextField(5);
            revertButtons();
            addKey();
            JButton innerRemoveButton = new JButton("Delete");
            InnerRemoveListener innerRemoveListener = new InnerRemoveListener(innerRemoveButton);
            innerRemoveButton.addActionListener(innerRemoveListener);

            removeContainer = new Container();
            removeContainer.setLayout(new BoxLayout(removeContainer, BoxLayout.X_AXIS));
            removeContainer.add(new JLabel("Enter Booking Number"));
            removeContainer.add(textField);

            // Combo box from https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html#uneditable
            removeFrame.add(removeContainer);
            removeFrame.add(innerRemoveButton, BorderLayout.SOUTH);
            removeFrame.addInternalFrameListener(internalFrameListener);
            innerContainer2.add(removeFrame);
            removeFrame.setVisible(true);
        }

        // EFFECTS:
        // The following method has been taken from:
        // https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java
        // The method has been edited accordingly to the needs of this project
        public void addKey() {
            textField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent ke) {
                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                        textField.setForeground(Color.BLACK);
                        textField.setEditable(true);
                    } else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        // The above backspace key event has been taken from
                        // https://stackoverflow.com/questions/30943107/detecting-backspace-ke
                        textField.setEditable(true);
                    } else {
                        textField.setEditable(false);
                    }
                }
            });
        }
    }

    // Removes the chosen booking from the list of bookings
    class InnerRemoveListener implements ActionListener {

        private JButton button;

        public InnerRemoveListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            revertButtons();
            removeBooking();
        }

        // EFFECTS: removes the chosen booking from the list of bookings
        public void removeBooking() {
            String text = textField.getText();
            if (text.equals("")) {
                textField.setForeground(Color.RED);
                textField.setText("Enter Valid Booking Number");
            } else if (text.equals("Enter Valid Booking Number")) {
                textField.setText("Enter Valid Booking Number");
            } else if (text.equals("Booking Removed!")) {
                textField.setForeground(Color.RED);
                textField.setText("Enter Valid Booking Number");
            } else {
                int index = Integer.parseInt(text);
                if (index == 0 || index > bookingList.size()) {
                    textField.setForeground(Color.RED);
                    textField.setText("Enter Valid Booking Number");
                } else {
                    bookingList.remove(index - 1);
                    textField.setForeground(Color.GREEN);
                    textField.setText("Booking Removed!");
                }
            }
        }
    }

    // EFFECTS: creates and adds button listeners
    public void createAndAddListeners() {
        DisplayListener displayListener = new DisplayListener(viewBookingsButton);
        viewBookingsButton.addActionListener(displayListener);

        ViewFaresListener viewFaresListener = new ViewFaresListener(viewFaresButton);
        viewFaresButton.addActionListener(viewFaresListener);

        SavesListener savesListener = new SavesListener(saveButton);
        saveButton.addActionListener(savesListener);

        LoadListener loadListener = new LoadListener(loadButton);
        loadButton.addActionListener(loadListener);

        AddListener addListener = new AddListener(addBookingsButton);
        addBookingsButton.addActionListener(addListener);

        RemoveListener removeListener = new RemoveListener(removeBookingsButton);
        removeBookingsButton.addActionListener(removeListener);
    }

    // MODIFIES: viewBookingsButton, viewFaresButton, addBookingsButton,  removeBookingsButton, saveButton, loadButton
    // EFFECTS: reverts the buttons to heir original text and turns their text colour to black
    public void revertButtons() {
        viewBookingsButton.setText("View Bookings");
        viewBookingsButton.setForeground(Color.black);

        viewFaresButton.setText("View Fares");
        viewFaresButton.setForeground(Color.black);

        addBookingsButton.setText("Add Bookings");
        addBookingsButton.setForeground(Color.black);

        removeBookingsButton.setText("Remove Bookings");
        removeBookingsButton.setForeground(Color.black);

        saveButton.setText("Save Bookings");
        saveButton.setForeground(Color.black);

        loadButton.setText("Load Bookings");
        loadButton.setForeground(Color.black);
    }

    // MODIFIES: desktop
    // EFFECTS: sets the size of desktop and adds the controlPanel and imagePane to the innerContainer
    public void setup() {
        desktop.setSize(new Dimension(WIDTH,HEIGHT));
        desktop.setTitle("Airline App");
        createAndAddButtons();
        innerContainer1.add(controlPanel);
        innerContainer1.add(imagePanel);
    }

    // EFFECTS: adds an image to imagePanel
    public void addImage() {
        imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("data/Airplane-image.jpeg"));
        imageLabel.setPreferredSize(new Dimension(400,500));
        imagePanel.add(imageLabel);
    }
}