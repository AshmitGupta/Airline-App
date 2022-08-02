package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

public class Card implements Writable {

    static final int REWARDS_PER_DOLLAR = 1;

    private int balance;
    private int points;

    // Represents a credit/debit card with initial balance and reward points
    public Card(int balance, int points) {
        this.balance = balance;
        this.points = points;
    }

    public int getBalance() {
        return balance;
    }

    public int getPoints() {
        return points;
    }

    // MODIFIES: balance
    // EFFECTS: updates balance to newBalance
    public void updateBalance(int newBalance) {
        this.balance = newBalance;
    }

    // MODIFIES: points
    // EFFECTS: updates points to newPoints
    public void updatePoints(int newPoints) {
        this.points = newPoints;
    }

    // MODIFIES: this
    // EFFECTS: decreases the balance on the card by amount dollars
    public boolean isTransaction(int amount) {
        if (balance >= amount) {
            balance -= amount;
            points += amount;
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: decreases the reward points on the card by amount dollars
    public boolean isRewardsTransaction(int amount) {
        if (points >= amount) {
            points -= REWARDS_PER_DOLLAR * amount;
            return true;
        }
        return false;
    }

    // This method has been taken from the JsonSerializationDemo project
    // The project can be cloned from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // It has been appropriately edited according to the needs of this project
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", this.balance);
        json.put("points", this.points);
        return json;
    }
}

