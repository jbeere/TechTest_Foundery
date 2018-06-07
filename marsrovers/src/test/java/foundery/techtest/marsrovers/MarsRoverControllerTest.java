package foundery.techtest.marsrovers;

import foundery.techtest.marsrovers.controller.MarsRoverController;
import foundery.techtest.marsrovers.model.MarsRover;
import foundery.techtest.marsrovers.view.PrintRenderer;
import foundery.techtest.marsrovers.view.Renderer;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarsRoverControllerTest {

    @Test
    public void testInput() throws Exception {
        String expected = String.format("1 3 N%n5 1 E%n");
        List<MarsRover> rovers = new ArrayList<>();

        MarsRoverController controller = new MarsRoverController(rovers);
        controller.next("5 5");
        controller.next("1 2 N");
        controller.next("LMLMLMLMM");
        controller.next("3 3 E");
        controller.next("MMRMMRMRRM");

        testResults(rovers, expected);
    }

    @Test
    public void testDoNothing() throws Exception {
        String expected = String.format("0 0 E%n");
        List<MarsRover> rovers = new ArrayList<>();

        MarsRoverController controller = new MarsRoverController(rovers);
        controller.next("5 5");
        controller.next("0 0 E");
        controller.next(""); // do nothing

        testResults(rovers, expected);
    }

    @Test(expected = RuntimeException.class)
    public void testBadBounds() {
        List<MarsRover> rovers = new ArrayList<>();
        MarsRoverController controller = new MarsRoverController(rovers);
        controller.next("A B");
    }

    @Test(expected = RuntimeException.class)
    public void testBadRover() {
        List<MarsRover> rovers = new ArrayList<>();
        MarsRoverController controller = new MarsRoverController(rovers);
        controller.next("5 5");
        controller.next("0 0 EEE");
    }

    @Test(expected = RuntimeException.class)
    public void testBadCommands() {
        List<MarsRover> rovers = new ArrayList<>();
        MarsRoverController controller = new MarsRoverController(rovers);
        controller.next("5 5");
        controller.next("0 0 E");
        controller.next("E");
    }

    private void testResults(List<MarsRover> rovers, String expected) {
        StringWriter strw = new StringWriter();
        Renderer renderer = new PrintRenderer(new PrintWriter(strw));
        rovers.forEach(renderer::render);

        String output = strw.toString();
        assertEquals(expected, output);
    }
}