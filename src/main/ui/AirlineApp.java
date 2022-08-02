package ui;

import model.BookingList;
import model.Card;
import model.Route;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Flight Booking App
public class AirlineApp extends JFrame {

    private static final String JSON_STORE_BOOKINGS = "./data/bookinglist.json";
    private static final String JSON_STORE_CREDIT = "./data/creditcard.json";
    private static final String JSON_STORE_DEBIT = "./data/debitcard.json";
    private Card creditCard;
    private Card debitCard;
    private Scanner input;
    private BookingList allBookings;
    private JsonWriter jsonWriterBookings;
    private JsonReader jsonReaderBookings;
    private JsonReader jsonReaderCredit;
    private JsonWriter jsonWriterCredit;
    private JsonReader jsonReaderDebit;
    private JsonWriter jsonWriterDebit;


    // EFFECTS: runs the airline flight booking application
    public AirlineApp() {
        jsonWriterBookings = new JsonWriter(JSON_STORE_BOOKINGS); //taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        jsonReaderBookings = new JsonReader(JSON_STORE_BOOKINGS); //taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

        jsonWriterCredit = new JsonWriter(JSON_STORE_CREDIT); //taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        jsonReaderCredit = new JsonReader(JSON_STORE_CREDIT); //taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

        jsonWriterDebit = new JsonWriter(JSON_STORE_DEBIT); //taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        jsonReaderDebit = new JsonReader(JSON_STORE_DEBIT); //taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
        init();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // This method has been taken from the AccountNotRobust - TellerApp.java project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    public void runAirline() {
        boolean keepGoing = true;
        String command = null;



        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you!");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    // This method has been taken from the AccountNotRobust - TellerApp.java project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // The method has been appropriately edited according to the need of this project
    private void init() {
        creditCard = new Card(20000, 7000);
        debitCard = new Card(5000, 10);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        allBookings = new BookingList();
    }

    // EFFECTS: displays menu of options to user
    // This method has been taken from the AccountNotRobust - TellerApp.java project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // The method has been appropriately edited according to the need of this project
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view all flight routes");
        System.out.println("\tf -> view all fares");
        System.out.println("\tb -> book a flight");
        System.out.println("\te -> change booking");
        System.out.println("\tt -> view all bookings");
        System.out.println("\tc -> Cancel a booking");
        System.out.println("\ts -> save all bookings");
        System.out.println("\tl -> load a booking");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: processes user command
    // This method has been taken from the AccountNotRobust - TellerApp.java project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
    // The method has been appropriately edited according to the need of this project
    private void processCommand(String command) {
        if (command.equals("v")) {
            showRoutes();
        } else if (command.equals("f")) {
            showFares();
        } else if (command.equals("b")) {
            bookFlight();
        } else if (command.equals("t")) {
            viewBookings();
        } else if (command.equals("e")) {
            changeBooking();
        } else if (command.equals("c")) {
            cancelBooking();
        } else if (command.equals("s")) {
            saveBookings();
        } else if (command.equals("l")) {
            loadBookings();
        } else {
            System.out.println("Please select a valid choice");
        }
    }

    // REQUIRES: allBookings.size() >= 1
    // MODIFIES: allBookings
    // EFFECTS: removes the existing booking from allBookings and adds the new booking
    private void changeBooking() {
        int changed = allBookings.size();
        int which = 0;
        System.out.println("Which booking do you want to change");
        changed = input.nextInt();
        while ((changed > allBookings.size()) | (changed < 1))  {
            System.out.println("Please enter a valid booking number");
            changed = input.nextInt();
        }
        allBookings.remove((changed - 1));
        System.out.println("Choose a new flight path");
        showRoutes();
        bookFlight();
    }


    // EFFECTS: prints the list of available routes
    private void showRoutes() {
        System.out.println("\nAll the available flights are:");
        System.out.println("\tVancouver to Toronto");
        System.out.println("\tVancouver to Waterloo");
        System.out.println("\tVancouver to Victoria");
        System.out.println("\tToronto to Vancouver");
        System.out.println("\tToronto to Waterloo");
        System.out.println("\tToronto to Victoria");
    }

    // EFFECTS: prints the list of available routes along with their fares
    private void showFares() {
        System.out.println("\nAll the flight fares are:");
        System.out.println("\tVancouver to Toronto   : $5000");
        System.out.println("\tVancouver to Waterloo  : $7000");
        System.out.println("\tVancouver to Victoria  : $2000");
        System.out.println("\tToronto to Waterloo    : $4500");
        System.out.println("\tToronto to Victoria    : $9000");
    }


    // EFFECTS: books a flight from and to the required destination
    private void bookFlight() {
        Route route1 = new Route("null", "null");
        int from = 0;
        printRoutes();
        from = input.nextInt();
        while (!((from == 1) | (from == 2) | (from == 3) | (from == 4) | (from == 5))) {
            System.out.println("No flight routes found, please enter another number");
            from = input.nextInt();
        }
        if (from == 1) {
            bookVToT(5000, route1);
        } else if (from == 2) {
            bookVtoW(7000, route1);
        } else if (from == 3) {
            bookVToV(2000, route1);
        } else if (from == 4) {
            bookTtoW(4500, route1);
        } else {
            bookTtoV(9000, route1);
        }
    }

    // EFFECTS: prints the list of available flight routes along with their fares
    public void printRoutes() {
        System.out.println("Enter 1/2/3/4/5 ->");
        System.out.println("\tVancouver to Toronto   : $5000 -> 1");
        System.out.println("\tVancouver to Waterloo  : $7000 -> 2");
        System.out.println("\tVancouver to Victoria  : $2000 -> 3");
        System.out.println("\tToronto to Waterloo    : $4500 -> 4");
        System.out.println("\tToronto to Victoria    : $9000 -> 5");
    }

    // EFFECTS: prints all the bookings (along with booking number made) by the user
    public void viewBookings() {
        if (allBookings.size() == 0) {
            System.out.println("Sorry, no available bookings found.");
            this.add(new JLabel("Sorry, no available bookings found."));
            this.pack();
            this.setVisible(true);
        }
        for (int i = 0; i < allBookings.size(); i++) {
            Route str = allBookings.getRoute(i);
            System.out.println("\n Booking " + (i + 1) + ":");

            System.out.println("From: " + str.getFrom());
            System.out.println("To: " + str.getTo());
        }
    }

    // MODIFIES: allBookings
    // EFFECTS: adds the route to allBookings
    public void booked(Route route) {
        allBookings.addRoute(route);
    }

    // MODIFIES: route
    // EFFECT: books a flight from Vancouver to Toronto and processes payment,
    //         if insufficient balance, prints "Sorry" message
    public void bookVToT(int amount, Route route) {
        route.changeFrom("Vancouver");
        route.changeTo("Toronto");
        if (payment(amount)) {
            System.out.println("Congratulations! Your ticket has been booked");
            booked(route);
        } else {
            System.out.println("Sorry, please try again!");
        }
    }

    // MODIFIES: route
    // EFFECT: books a flight from Vancouver to Waterloo and processes payment,
    //         if insufficient balance, prints "Sorry" message
    public void bookVtoW(int amount, Route route) {
        route.changeFrom("Vancouver");
        route.changeTo("Waterloo");
        if (payment(amount)) {
            System.out.println("Congratulations! Your ticket has been booked");
            booked(route);
        } else {
            System.out.println("Sorry, please try again!");
        }
    }

    // MODIFIES: route
    // EFFECT: books a flight from Vancouver to Victoria and processes payment,
    //         if insufficient balance, prints "Sorry" message
    public void bookVToV(int amount, Route route) {
        route.changeFrom("Toronto");
        route.changeTo("Victoria");
        if (payment(amount)) {
            System.out.println("Congratulations! Your ticket has been booked");
            booked(route);
        } else {
            System.out.println("Sorry, please try again!");
        }
    }

    // MODIFIES: route
    // EFFECT: books a flight from Toronto to Waterloo and processes payment,
    //         if insufficient balance, prints "Sorry" message
    public void bookTtoW(int amount, Route route) {
        route.changeFrom("Toronto");
        route.changeTo("Waterloo");
        if (payment(amount)) {
            System.out.println("Congratulations! Your ticket has been booked");
            booked(route);
        } else {
            System.out.println("Sorry, please try again!");
        }
    }

    // MODIFIES: route
    // EFFECT: books a flight from Toronto to Victoria and processes payment,
    //         if insufficient balance, prints "Sorry" message
    public void bookTtoV(int amount, Route route) {
        route.changeFrom("Toronto");
        route.changeTo("Victoria");
        if (payment(amount)) {
            System.out.println("Congratulations! Your ticket has been booked");
            booked(route);
        } else {
            System.out.println("Sorry, please try again!");
        }
    }

    // MODIFIES: credit card and/or debit card balance
    // EFFECTS: deducts the required amount from credit/debit card (from balance or from reward points),
    //          in case of insufficient balance, return false
    public boolean payment(int amount) {
        int card = 0;
        System.out.println("Enter \n 1 for credit card \n 2 for debit card\n 3 for using reward points (credit card) "
                + "\n 4 for using reward points (debit card)");
        card = input.nextInt();
        while (!((card == 1) | (card == 2) | (card == 3) | (card == 4))) {
            System.out.println("Please enter the correct number");
            card = input.nextInt();
        }
        if (card == 1) {
            return (creditCard.isTransaction(amount));
        } else if (card == 2) {
            return (debitCard.isTransaction(amount));
        } else if (card == 3) {
            return (creditCard.isRewardsTransaction(amount));
        } else {
            return (debitCard.isRewardsTransaction(amount));
        }
    }

    // REQUIRES: allBookings.size() >= 1
    // MODIFIES: allBookings
    // EFFECTS: removes the existing booking from allBookings
    private void cancelBooking() {
        int changed = allBookings.size();
        int which = 0;
        System.out.println("Which booking do you want to cancel");
        changed = input.nextInt();
        while ((changed > allBookings.size()) | (changed < 1))  {
            System.out.println("Please enter a valid booking number");
            changed = input.nextInt();
        }
        allBookings.remove((changed - 1));
        System.out.println("Congrats! Your booking has been cancelled!");
    }

    // EFFECTS: saves allBookings, credit card and debit card information to file
    // This method has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It has been appropriately edited according to the needs of this project
    private void saveBookings() {
        try {
            jsonWriterBookings.open();
            jsonWriterBookings.write(allBookings);
            jsonWriterBookings.close();

            jsonWriterCredit.open();
            jsonWriterCredit.writeCard(creditCard);
            jsonWriterCredit.close();

            jsonWriterDebit.open();
            jsonWriterDebit.writeCard(debitCard);
            jsonWriterDebit.close();

            System.out.println("Saved your bookings to " + JSON_STORE_BOOKINGS);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_BOOKINGS);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads bookingList, credit card and debit card information from file
    // This method has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It has been appropriately edited according to the needs of this project
    private void loadBookings() {
        try {
            allBookings = jsonReaderBookings.read();
            creditCard = jsonReaderCredit.readCard();
            debitCard = jsonReaderDebit.readCard();
            System.out.println("Loaded all your bookings from " + JSON_STORE_BOOKINGS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_BOOKINGS);
        }
    }
}