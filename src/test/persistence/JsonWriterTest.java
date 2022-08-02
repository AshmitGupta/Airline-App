package persistence;

import model.BookingList;
import model.Card;
import org.junit.jupiter.api.Test;
import model.Route;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It was edited appropriately according to the needs of this project
    @Test
    void testWriterInvalidFile() {
        try {
            BookingList wr = new BookingList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It was edited appropriately according to the needs of this project
    @Test
    void testWriterEmptyBookingList() {
        try {
            BookingList wr = new BookingList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookingList.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookingList.json");
            wr = reader.read();
            assertEquals(0, wr.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It was edited appropriately according to the needs of this project
    @Test
    void testWriterGeneralBookingList() {
        try {
            BookingList wr = new BookingList();
            wr.addRoute(new Route("Vancouver", "Waterloo"));
            wr.addRoute(new Route("Toronto", "Vancouver"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookingList.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBookingList.json");
            wr = reader.read();
            assertEquals(2, wr.size());
            checkRoute("Vancouver", "Waterloo", wr.getRoute(0));
            checkRoute("Toronto", "Vancouver", wr.getRoute(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // This test class has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It was edited appropriately according to the needs of this project
    @Test
    void testWriterInvalidCardFile() {
        try {
            Card wr = new Card(1000,500);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterGeneralCard() {
        try {
            Card wr = new Card(1000,800);
            wr.updateBalance(5000);
            wr.updatePoints(1500);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookingList.json");
            writer.open();
            writer.writeCard(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBookingList.json");
            wr = reader.readCard();
            checkCard(5000,1500, wr);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
