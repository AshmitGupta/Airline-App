package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.BookingList;
import model.Card;
import model.Route;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// This class has been taken from the JsonSerializationDemo project
// The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// It has been appropriately edited according to the needs of this project
public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bookingList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BookingList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBookingList(jsonObject);
    }

    // EFFECTS: reads Card from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Card readCard() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses bookingList from JSON object and returns it
    private BookingList parseBookingList(JSONObject jsonObject) {
        BookingList bookingList = new BookingList();
        addBookings(bookingList, jsonObject);
        return bookingList;
    }

    // EFFECTS: parses Card from JSON object and returns it
    private Card parseCard(JSONObject jsonObject) {
        Card card = new Card(0,0);
        updateAmount(card, jsonObject);
        return card;
    }

    // MODIFIES: bookingList
    // EFFECTS: parses routes from JSON object and adds them to bookingList
    private void addBookings(BookingList bookingList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Booking");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addRoute(bookingList, nextThingy);
        }
    }

    // MODIFIES: bookingList
    // EFFECTS: parses routes from JSON object and adds it to bookingList
    private void addRoute(BookingList bookingList, JSONObject jsonObject) {
        String from = jsonObject.getString("from");
        String to = jsonObject.getString("to");
        Route route = new Route(from, to);
        bookingList.addRoute(route);
    }

    // MODIFIES: card
    // EFFECTS: parses Card from JSON object and updates the balance and points
    private void updateAmount(Card card, JSONObject jsonObject) {
        int balance = jsonObject.getInt("balance");
        int points = jsonObject.getInt("points");
        card.updateBalance(balance);
        card.updatePoints(points);
    }
}
