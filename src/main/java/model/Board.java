package model;


/**
 * @author Alper Ulu
 *
 * Game board
 * 0,1,2,3,4,5 -> holes for player1 6->player1 kalah
 * 7,8,9,10,11,12 -> holes for player2 13->player2 kalah
 */
public class Board {

    //static game board is for easy update operation
    public static int[] board = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};

    public static void printBoard() {
        System.out.println();
        System.out.print(board[13] + "- ");
        for (int i = 12; i > 6; i--) {
            System.out.print(board[i] + " ");
        }
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < 6; i++) {
            System.out.print(board[i] + " ");
        }
        System.out.print("-" + board[6]);
        System.out.println();
    }
}
