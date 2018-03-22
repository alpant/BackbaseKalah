import com.google.gson.Gson;
import controller.RulesImpl;
import exception.KalahException;
import interfaces.Rules;
import model.Board;
import model.Game;
import model.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import rest.KalahWebResource;
import rest.PlayRequest;

import javax.ws.rs.core.Response;

/**
 * Test class for Kalah game
 */
public class KalahTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private Game game;
    private Rules controller;

    @Before
    public void setupKalahGame() {
        game = new Game();
        controller = new RulesImpl(game);
    }

    @Test
    public void testPlay() {
        int[] board = {0, 0, 0, 0, 0, 1, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        Player player = new Player(1);
        try {
            Assert.assertTrue(controller.play(player, 5));
        } catch (KalahException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSowStones_forPlayer1() {
        int[] board = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        int[] expected = {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;

        Player player = new Player(1);
        controller.sowStones(player, 0);

        Assert.assertEquals(1, board[6]);
        Assert.assertArrayEquals(expected, board);
    }

    @Test
    public void testSowStones_forPlayer1withCapture() {
        int[] board = {2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 0};
        int[] expected = {0, 3, 0, 2, 2, 2, 3, 2, 2, 2, 0, 2, 2, 0};
        Board.board = board;

        Player player = new Player(1);
        controller.sowStones(player, 0);

        Assert.assertEquals(3, board[6]);
        Assert.assertArrayEquals(expected, board);
    }

    @Test
    public void testSowStones_forPlayer2() {
        int[] board = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        int[] expected = {7, 6, 6, 6, 6, 6, 0, 6, 0, 7, 7, 7, 7, 1};
        Board.board = board;

        Player player = new Player(2);
        controller.sowStones(player, 8);

        Assert.assertEquals(1, board[13]);
        Assert.assertArrayEquals(expected, board);
    }

    @Test
    public void testSowStones_forPlayer2withStore() {
        int[] board = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 10, 0};
        int[] expected = {7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 6, 6, 0, 1};
        Board.board = board;

        Player player = new Player(2);
        controller.sowStones(player, 12);

        Assert.assertEquals(0, board[6]);
        Assert.assertArrayEquals(expected, board);
    }

    @Test
    public void testSowStones_forPlayer2withCapture() {
        int[] board = {2, 2, 2, 2, 2, 2, 0, 2, 2, 0, 2, 2, 2, 0};
        int[] expected = {2, 2, 2, 0, 2, 2, 0, 0, 3, 0, 2, 2, 2, 3};
        Board.board = board;

        Player player = new Player(2);
        controller.sowStones(player, 7);

        Assert.assertEquals(3, board[13]);
        Assert.assertArrayEquals(expected, board);
    }

    @Test
    public void testCheckIfGameFinished_forPlayer1() {
        int[] board = {0, 0, 0, 0, 0, 0, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        Assert.assertTrue(controller.checkIfGameFinished(game.getPlayer1()));
    }

    @Test
    public void testCheckAllowedPitsForWrongPit() throws KalahException {
        exception.expect(KalahException.class);
        exception.expectMessage("choose");
        controller.checkAllowedPits(game.getPlayer1(), 7);
    }

    @Test
    public void testCheckAllowedPitsForEmptyPit() throws KalahException {
        int[] board = {0, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        exception.expect(KalahException.class);
        exception.expectMessage("no stone");
        controller.checkAllowedPits(game.getPlayer1(), 0);
    }

    @Test
    public void testCheckTurn() throws KalahException {
        exception.expect(KalahException.class);
        exception.expectMessage("wrong");
        game.setTurn(2);
        controller.checkTurn(game.getPlayer1());
    }

    @Test
    public void testCheckIfFreeTurn_false() {
        int[] board = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        Assert.assertFalse(controller.checkIfFreeTurn(game.getPlayer1(), 0));
    }

    @Test
    public void testCheckIfFreeTurn_true() {
        int[] board = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        Assert.assertTrue(controller.checkIfFreeTurn(game.getPlayer1(), 6));
    }

    @Test
    public void testCheckIfCaptured_true() {
        int[] board = {1, 0, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        Assert.assertTrue(controller.checkIfCapture(game.getPlayer1(), 0));
    }

    @Test
    public void testCheckIfCaptured_false() {
        int[] board = {1, 0, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        Board.board = board;
        Assert.assertFalse(controller.checkIfCapture(game.getPlayer1(), 7));
    }

    @Test
    public void testPrintBoard() {
        Board.printBoard();
    }

    @Test
    public void testStartGameService_200() {
        KalahWebResource resource = new KalahWebResource();
        String request = "request";
        Response response = resource.startGame(request);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testPlayService_200() {
        KalahWebResource resource = new KalahWebResource();
        PlayRequest playRequest = new PlayRequest();
        Gson gson = new Gson();
        String request = "request";
        Response response;
        resource.startGame(request);
        response = resource.play(gson.toJson(playRequest));
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testPlayService_500() {
        KalahWebResource resource = new KalahWebResource();
        PlayRequest playRequest = new PlayRequest();
        Gson gson = new Gson();
        Response response;
        response = resource.play(gson.toJson(playRequest));
        Assert.assertEquals(500, response.getStatus());
    }
}
