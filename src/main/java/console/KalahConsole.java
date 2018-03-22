package console;

import controller.RulesImpl;
import exception.KalahException;
import interfaces.Rules;
import model.Board;
import model.Game;

import java.util.Scanner;

/**
 * @author Alper Ulu
 *
 * Simple console application to play the Kalah game
 */
public class KalahConsole {

    public static void main(String[] args) {

        Game game = new Game(); //create a new game
        Rules controller = new RulesImpl(game); //give game controller to controller class

        Board.printBoard(); //prints board's current situation
        System.out.println();
        System.out.println("turn is ->" + game.getTurn() + ". player");
        System.out.println("pit->");

        Scanner in = new Scanner(System.in);
        int pit = in.nextInt(); //reads pit no from console

        while (pit != -1) {
            try {
                if (game.getTurn() == 1) {
                    if (controller.play(game.getPlayer1(), pit)) {
                        break;
                    }
                } else {
                    if (controller.play(game.getPlayer2(), pit)) {
                        break;
                    }
                }
                Board.printBoard();
                System.out.println();
                System.out.println("turn is ->" + game.getTurn() + ". player");
                System.out.println("pit->");
                pit = in.nextInt();
            } catch (KalahException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        System.out.println("finito");
        System.out.println("player 1 -> " + game.getPlayer1().getScore());
        System.out.println("player 2 -> " + game.getPlayer2().getScore());
        Board.printBoard();
    }
}
