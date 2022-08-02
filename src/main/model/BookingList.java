package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;

public class BookingList implements Writable {

    private ArrayList<Route> bookingList;

    public BookingList() {
        bookingList = new ArrayList<Route>();
    }

    // EFFECTS: returns the size of the bookingList
    public int size() {
        return bookingList.size();
    }

    // MODIFIES: this, EventLog.getInstance()
    // REQUIRES: bookingList.size() != 0
    // EFFECTS: removes the element of the given index from the bookingList
    //          and logs the removing of a booking
    public void remove(int index) {
        bookingList.remove(index);
        EventLog.getInstance().logEvent(new Event("Removed booking number " + (index + 1)));
    }

    // EFFECTS: returns the given index elements from the bookingList
    public Route getRoute(int index) {
        return (bookingList.get(index));
    }

    // MODIFIES: this, EventLog.getInstance()
    // EFFECTS: add the route to bookingList
    //          and logs the addition of a booking
    public void addRoute(Route route) {
        bookingList.add(route);
        EventLog.getInstance().logEvent(new Event("Added new booking: "
                + "From: " + route.getFrom()
                + " To: " + route.getTo()));
    }

    // EFFECTS: returns the list of all bookings as a JLabel
    public JLabel showRoutes() {
        if (bookingList.size() == 0) {
            return (new JLabel("Sorry, no available bookings found."));
        }
        // The string concatenation used in the following lines of code has been inspired from:
        // https://stackoverflow.com/questions/7817951/string-concatenation-in-java-when-to-use-stringbuilder-and-concat
        // Using html tags instead of \n character inside JLabel has been inspired from:
        // https://stackoverflow.com/questions/1090098/newline-in-jlabel
        String combined = "";
        for (int i = 0; i < bookingList.size(); i++) {
            Route str = getRoute(i);
            combined += "Booking " + (i + 1) + ": <br/> From: " + str.getFrom() + "<br/> To: "
                    + str.getTo() + "<br/>" + "<br/>";
        }
        return (new JLabel("<html>" + combined + "<html>"));
    }

    // This method has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It has been appropriately edited according to the needs of this project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Booking", routesToJson());
        return json;
    }

    // EFFECTS: returns route in this booking as a JSON array
    // This method has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It has been appropriately edited according to the needs of this project
    private JSONArray routesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Route route : bookingList) {
            jsonArray.put(route.toJson());
        }
        return jsonArray;
    }
}
