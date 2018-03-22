package rest;

import java.io.Serializable;

/**
 * @author Alper Ulu
 * <p>
 * request object to play game, it has pit number
 */
public class PlayRequest implements Serializable {

    private int pitNo;

    public int getPitNo() {
        return pitNo;
    }

}
