package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookingListTest {

    private BookingList bookingList;

    @BeforeEach
    void setup() {
        bookingList = new BookingList();
    }

    @Test
    void TestAddRoute() {
        Route route1 = new Route("Vancouver", "Toronto");
        Route route2 = new Route("Vancouver", "Waterloo");
        Route route3 = new Route("Waterloo", "Vancouver");

        bookingList.addRoute(route1);
        assertEquals(route1, bookingList.getRoute(0));

        bookingList.addRoute(route2);
        bookingList.addRoute(route3);
        assertEquals(route2, bookingList.getRoute(1));
        assertEquals(route3, bookingList.getRoute(2));
    }

    @Test
    void TestSize() {
        Route route1 = new Route("Vancouver", "Toronto");
        Route route2 = new Route("Vancouver", "Waterloo");
        Route route3 = new Route("Waterloo", "Vancouver");

        bookingList.addRoute(route1);
        assertEquals(1,bookingList.size());

        bookingList.addRoute(route2);
        bookingList.addRoute(route3);
        assertEquals(3,bookingList.size());
    }

    @Test
    void TestRemove() {
        Route route1 = new Route("Vancouver", "Toronto");
        Route route2 = new Route("Vancouver", "Waterloo");
        Route route3 = new Route("Waterloo", "Vancouver");

        bookingList.addRoute(route1);
        assertEquals(1,bookingList.size());
        bookingList.remove(0);
        assertEquals(0,bookingList.size());

        bookingList.addRoute(route2);
        bookingList.addRoute(route3);
        bookingList.remove(1);
        bookingList.remove(0);
        assertEquals(0, bookingList.size());
    }

    @Test
    void TestShowRoutes() {
        JLabel label = bookingList.showRoutes();
        assertEquals(label.getText(), "Sorry, no available bookings found.");

        bookingList.addRoute(new Route("Vancouver", "Toronto"));
        bookingList.addRoute(new Route("Victoria", "Waterloo"));
        JLabel label2 = bookingList.showRoutes();
        assertEquals(label2.getText(),"<html>Booking 1: <br/> From: Vancouver<br/> To: Toronto<br/><br/>Booking 2: " +
                "<br/> From: Victoria<br/> To: Waterloo<br/><br/><html>");

        bookingList.remove(0);
        JLabel label3 = bookingList.showRoutes();
        assertEquals(label3.getText(),"<html>Booking 1: <br/> From: Victoria<br/> To: Waterloo<br/><br/><html>");
    }

}
