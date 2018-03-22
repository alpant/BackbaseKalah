package controller;

import exception.KalahException;
import interfaces.Rules;
import model.Board;
import model.Game;
import model.Player;

import java.util.List;

/**
 * @author Alper Ulu
 *
 * Implements all the game rules
 */
public class RulesImpl implements Rules {

    private Game game;

    public RulesImpl(Game game) {
        this.game = game;
    }

    public boolean play(Player player, int pit) throws KalahException {

        //check whose turn
        checkTurn(player);

        //check if the given player whether can play given pit number
        checkAllowedPits(player, pit);

        //sow the stones one by one
        sowStones(player, pit);

        //check game finished or not
        return checkIfGameFinished(player);
    }

    public void checkTurn(Player player) throws KalahException {
        //if given player id is not equal to game turn throw exception
        if (player.getId() != game.getTurn()) {
            throw new KalahException("wrong turn");
        }
    }

    public void checkAllowedPits(Player player, int pit) throws KalahException {
        //if given pit number does not in player's side throw an exception
        if (!player.getAllowedPits().contains(pit))
            throw new KalahException("you choose the wrong pit");

        //given pit should not be empty
        if (Board.board[pit] == 0)
            throw new KalahException("there is no stone in this pit");
    }

    public void sowStones(Player player, int pit) {

        int i, lastMoveIndex, capturedStones;
        int pitCount = Board.board[pit];
        boolean isCaptured = false;
        boolean isFreeTurn = true;
        Board.board[pit] = 0;

        //sow stones according to player 1
        if (player.getId() == game.getPlayer1().getId()) {

            //sow stones one by one but skip 13th pit
            for (i = 0; i < pitCount; i++) {
                Board.board[(pit + 1 + i) % 13]++;
            }
            lastMoveIndex = (pit + i) % 13;

            //if last stone is put into the player 1 kalah, player 1 gets one free turn
            if (!checkIfFreeTurn(player, lastMoveIndex)) {
                game.setTurn(2);
                isFreeTurn = false;
            }

            /**
             * if last piece player 1 drop is in an empty hole on his side, player 1 capture that piece and
             //any pieces in the hole directly opposite
             */
            if (!isFreeTurn && checkIfCapture(player, lastMoveIndex)) {
                capturedStones = Board.board[game.getPlayer2().getKala() - (lastMoveIndex + 1)];
                Board.board[game.getPlayer1().getKala()] += capturedStones;
                Board.board[game.getPlayer2().getKala() - (lastMoveIndex + 1)] = 0;
                isCaptured = true;
            }

        } else { //sow stones according to player 2

            //sow stones one by one but skip 6th pit
            for (i = 0; i < pitCount; i++) {
                if ((pit + 1 + i) % 14 == 6) {
                    pitCount++;
                    continue;
                }
                Board.board[(pit + 1 + i) % 14]++;
            }

            lastMoveIndex = (pit + i) % 14;

            //check whether free turn on not
            if (!checkIfFreeTurn(player, lastMoveIndex)) {
                game.setTurn(1);
                isFreeTurn = false;
            }

            //check if there is capture
            if (!isFreeTurn && checkIfCapture(player, lastMoveIndex)) {
                capturedStones = Board.board[(game.getPlayer2().getKala() - lastMoveIndex) - 1];
                Board.board[game.getPlayer2().getKala()] += capturedStones;
                Board.board[(game.getPlayer2().getKala() - lastMoveIndex) - 1] = 0;
                isCaptured = true;
            }
        }

        if (isCaptured) {
            Board.board[lastMoveIndex] = 0;
            Board.board[player.getKala()]++;
        }
        //updates players scores
        player.setScore(Board.board[player.getKala()]);
    }

    public boolean checkIfFreeTurn(Player player, int lastMoveIndex) {
        //if the last drop is into given player's kala, it means free turn
        if (player.getKala() == lastMoveIndex) {
            game.setTurn(player.getId());
            return true;
        }
        return false;
    }

    public boolean checkIfCapture(Player player, int lastMoveIndex) {
        //if the last drop is into empty pit, given player capture the stones where is directly opposite
        if (player.getAllowedPits().contains(lastMoveIndex)) {
            if ((Board.board[lastMoveIndex] - 1) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfGameFinished(Player player) {
        List<Integer> list = player.getAllowedPits();
        int i = 0, emptyHole = 0;

        //check if all holes are empty on given player's side
        while (i < list.size() && Board.board[list.get(i)] == 0) {
            emptyHole++;
            i++;
        }
        if (emptyHole == list.size()) {
            decideWinner();
            return true;
        }
        return false;
    }

    public void decideWinner() {
        List<Integer> player1Holes = game.getPlayer1().getAllowedPits();
        List<Integer> player2Holes = game.getPlayer2().getAllowedPits();

        //if the game finished, collect all remaining stones to the player's kalah
        for (Integer player1Hole : player1Holes) {
            Board.board[game.getPlayer1().getKala()] += Board.board[player1Hole];
        }

        for (Integer player2Hole : player2Holes) {
            Board.board[game.getPlayer2().getKala()] += Board.board[player2Hole];
        }

        //final scores is determined by how many stones each player has on your his kalah
        game.getPlayer1().setScore(Board.board[game.getPlayer1().getKala()]);
        game.getPlayer2().setScore(Board.board[game.getPlayer2().getKala()]);
    }
}