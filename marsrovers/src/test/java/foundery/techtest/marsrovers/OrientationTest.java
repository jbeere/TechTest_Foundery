package foundery.techtest.marsrovers;

import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.Orientation;
import org.junit.Test;

import static foundery.techtest.marsrovers.model.Direction.LEFT;
import static foundery.techtest.marsrovers.model.Direction.RIGHT;
import static foundery.techtest.marsrovers.model.Orientation.*;
import static org.junit.Assert.assertEquals;

public class OrientationTest {

    @Test
    public void testOrientationTurn() {
        assertEquals(E, N.turn(RIGHT));
        assertEquals(W, N.turn(LEFT));
        assertEquals(N, W.turn(RIGHT));
        assertEquals(S, W.turn(LEFT));
        assertEquals(E, S.turn(LEFT));
        assertEquals(W, S.turn(RIGHT));
        assertEquals(N, E.turn(LEFT));
        assertEquals(S, E.turn(RIGHT));
    }

    @Test
    public void testOrientationMove() {
        checkMove(N, 0, 0, 0, 1);
        checkMove(E, 0, 0, 1, 0);
        checkMove(S, 0, 0, 0, -1);
        checkMove(W, 0, 0, -1, 0);
    }

    private static void checkMove(Orientation orientation, int x0, int y0, int x1, int y1) {
        Coordinate before = new Coordinate(x0, y0);
        Coordinate after = orientation.move(before);
        assertEquals(x1, after.getX());
        assertEquals(y1, after.getY());
    }
}