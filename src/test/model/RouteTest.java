package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {

    private Route route;

    @BeforeEach
    void setup() {
        route = new Route("Vancouver", "Toronto");
    }

    @Test
    void changeFromTest() {
        route.changeFrom("Montreal");
        assertEquals("Montreal", route.getFrom());

        route.changeFrom("Toronto");
        route.changeFrom("Waterloo");
        assertEquals("Waterloo", route.getFrom());
    }

    @Test
    void changeToTest(){
        route.changeTo("Victoria");
        assertEquals("Victoria", route.getTo());

        route.changeTo("Toronto");
        route.changeTo("Waterloo");
        assertEquals("Waterloo", route.getTo());
    }

}