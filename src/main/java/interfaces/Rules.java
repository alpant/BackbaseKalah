package interfaces;

import exception.KalahException;
import model.Player;

/**
 * @author Alper Ulu
 */
public interface Rules {

    /**
     * responsible for play action according to kalah game rules
     *
     * @param player
     * @param pit
     * @return
     * @throws KalahException
     */
    boolean play(Player player, int pit) throws KalahException;

    /**
     * checks whether the game finished or not
     *
     * @param player
     * @return
     */
    boolean checkIfGameFinished(Player player);

    /**
     * calculates the final scores of the players and decides the winner
     */
    void decideWinner();

    /**
     * sow stones one by one according to given pit number and player
     *
     * @param player
     * @param pit
     */
    void sowStones(Player player, int pit);

    /**
     * checks whether the given player has free turn or not
     *
     * @param player
     * @param index
     * @return
     */
    boolean checkIfFreeTurn(Player player, int index);

    /**
     * checks whether the given player can capture opponents stones or not
     *
     * @param player
     * @param index
     * @return
     */
    boolean checkIfCapture(Player player, int index);

    /**
     * checks the sow operation is acceptable for given player
     *
     * @param player
     * @param pit
     * @throws KalahException
     */
    void checkAllowedPits(Player player, int pit) throws KalahException;

    /**
     * checks the turn is right for given player
     *
     * @param player
     * @throws KalahException
     */
    void checkTurn(Player player) throws KalahException;
}
