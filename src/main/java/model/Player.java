package model;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alper Ulu
 * Player class
 */
public class Player {

    private int id; //each player has id
    private int score; //player's current score
    private List<Integer> allowedPits; //pits that the player can play
    private int kalaId; //player's kalah

    public Player(int id) {
        this.id = id;
        this.score = 0;

        if (id == 1) {
            this.allowedPits = Arrays.asList(0, 1, 2, 3, 4, 5);
            this.kalaId = 6;
        } else {
            this.allowedPits = Arrays.asList(7, 8, 9, 10, 11, 12);
            this.kalaId = 13;
        }
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Integer> getAllowedPits() {
        return allowedPits;
    }

    public int getKala() {
        return kalaId;
    }
}
