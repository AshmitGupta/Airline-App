package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Card;
import model.Route;

public class JsonTest {

    protected void checkRoute(String from, String  to, Route route) {
        assertEquals(from, route.getFrom());
        assertEquals(to, route.getTo());
    }

    protected void checkCard(int balance, int  points, Card card) {
        assertEquals(balance, card.getBalance());
        assertEquals(points, card.getPoints());
    }
}
