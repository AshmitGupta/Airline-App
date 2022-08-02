package persistence;

import model.BookingList;
import model.Card;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BookingList wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testReaderEmptyBookingList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookingList.json");
        try {
            BookingList wr = reader.read();
            assertEquals(0, wr.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It was edited appropriately according to the needs of this project
    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookingList.json");
        try {
            BookingList wr = reader.read();
            assertEquals(2, wr.size());
            checkRoute("Vancouver", "Toronto", wr.getRoute(0));
            checkRoute("Toronto", "Waterloo", wr.getRoute(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    @Test
    void testReaderNonExistentCardFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Card wr = reader.readCard();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It was edited appropriately according to the needs of this project
    @Test
    void testReaderGeneralCard() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCard.json");
        try {
            Card wr = reader.readCard();
            checkCard(15000,8000, wr);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
