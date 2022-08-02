package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CardTest {

    private Card card;

    @BeforeEach
    void setup(){
        card = new Card(30000, 2000);
    }

    @Test
    void transactionTest() {
        assertTrue(card.isTransaction(5000));
        assertFalse(card.isTransaction(27000));
        assertEquals(25000, card.getBalance());

        assertTrue(card.isTransaction(1000));
        assertTrue(card.isTransaction(2000));
        assertEquals(22000, card.getBalance());
    }

    @Test
    void rewardsTransactionTest() {
        assertTrue(card.isRewardsTransaction(100));
        assertFalse(card.isRewardsTransaction(2100));
        assertEquals(1900, card.getPoints());

        assertTrue(card.isRewardsTransaction(200));
        assertTrue(card.isRewardsTransaction(300));
        assertEquals(1400, card.getPoints());
    }

    @Test
    void updateBalanceTest() {
        card.updateBalance(1000);
        assertEquals(1000, card.getBalance());

        card.updateBalance(500);
        card.updateBalance(1500);
        assertEquals(1500, card.getBalance());
    }

    @Test
    void updatePointsTest() {
        card.updatePoints(1000);
        assertEquals(1000, card.getPoints());

        card.updatePoints(500);
        card.updatePoints(1500);
        assertEquals(1500, card.getPoints());
    }
}
