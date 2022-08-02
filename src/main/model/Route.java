package model;

import org.json.JSONObject;
import persistence.Writable;

public class Route implements Writable {

    private String fromCity;
    private String toCity;

    // Represents a flight route from one city to another in Canada
    public Route(String from, String to) {
        this.fromCity = from;
        this.toCity = to;
    }

    // MODIFIES: this
    // EFFECTS: changes the 'from' city to the desired 'changedfrom' city
    public void changeFrom(String changedfrom) {
        this.fromCity = changedfrom;
    }

    // MODIFIES: this
    // EFFECTS: changes the 'to' city to the desired 'changedto' city
    public void changeTo(String changedto) {
        this.toCity = changedto;
    }

    public String getFrom() {
        return fromCity;
    }

    public String getTo() {
        return toCity;
    }

    // This method has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It has been appropriately edited according to the needs of this project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("from", fromCity);
        json.put("to", toCity);
        return json;
    }
}
