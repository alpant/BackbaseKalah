package model;

/**
 * Game class to hold players and the current turn
 */
public class Game {

    private Player player1; //player1 object
    private Player player2; //player2 object
    private int turn; //turn object, 1=player 1's turn, 2=player'2 turn

    public Game() {

        //Initiate the players
        this.player1 = new Player(1);
        this.player2 = new Player(2);

        //Initiate the current turn
        this.turn = 1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
