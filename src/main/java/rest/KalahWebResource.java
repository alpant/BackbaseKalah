package rest;

import com.google.gson.Gson;
import controller.RulesImpl;
import exception.KalahException;
import interfaces.Rules;
import model.Game;
import org.slf4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Alper Ulu
 *
 * Rest services for kalah game
 */

@Path("kalahGame")
public class KalahWebResource {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "json/application; charset=utf-8";
    private final Logger log = getLogger(getClass());

    private Game game;
    private Rules controller;

    /**
     * takes new game request from client and initialize the game
     *
     * @param requestJson
     * @return
     */
    @POST
    @Path("start")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(String requestJson) {

        Response restResponse;
        try {

            game = new Game();
            controller = new RulesImpl(game);

            Response.ResponseBuilder rb = Response.status(Response.Status.OK);
            restResponse = rb.header(CONTENT_TYPE, CONTENT_TYPE_VALUE).build();
        } catch (Exception e) {
            Response.ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            restResponse = rb.header(CONTENT_TYPE,
                    CONTENT_TYPE_VALUE).build();
            log.error("error occurred while new game creating", e);
        }
        return restResponse;
    }

    /**
     * takes play request and transfer it to the controller
     *
     * @param requestJson
     * @return
     */
    @POST
    @Path("play")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response play(String requestJson) {

        PlayRequest request;
        Response restResponse;

        try {
            Gson gson = new Gson();
            request = gson.fromJson(requestJson, PlayRequest.class);

            if (request == null)
                throw new KalahException("request is null");

            if (game == null)
                throw new KalahException("there is no game, you should start a game first");

            if (game.getTurn() == game.getPlayer1().getId()) {
                controller.play(game.getPlayer1(), request.getPitNo());
            } else {
                controller.play(game.getPlayer2(), request.getPitNo());
            }

            Response.ResponseBuilder rb = Response.status(Response.Status.OK);
            restResponse = rb.header(CONTENT_TYPE,
                    CONTENT_TYPE_VALUE).build();
        } catch (Exception e) {
            Response.ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            restResponse = rb.header(CONTENT_TYPE,
                    CONTENT_TYPE_VALUE).build();

            log.error("error occurred while playing kalah", e);
        }
        return restResponse;
    }
}