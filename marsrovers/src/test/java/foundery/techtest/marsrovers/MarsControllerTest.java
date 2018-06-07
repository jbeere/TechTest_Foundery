package foundery.techtest.marsrovers;

import foundery.techtest.marsrovers.controller.MarsController;
import foundery.techtest.marsrovers.model.MarsModel;
import foundery.techtest.marsrovers.view.PrintRenderer;
import foundery.techtest.marsrovers.view.Renderer;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class MarsControllerTest {

    @Test
    public void testInput() throws Exception {
        String expected = String.format("1 3 N%n5 1 E%n");
        MarsModel model = new MarsModel();

        MarsController controller = new MarsController(model);
        controller.next("5 5");
        controller.next("1 2 N");
        controller.next("LMLMLMLMM");
        controller.next("3 3 E");
        controller.next("MMRMMRMRRM");

        testResults(model, expected);
    }

    @Test
    public void testDoNothing() throws Exception {
        String expected = String.format("0 0 E%n");
        MarsModel model = new MarsModel();

        MarsController controller = new MarsController(model);
        controller.next("5 5");
        controller.next("0 0 E");
        controller.next(""); // do nothing

        testResults(model, expected);
    }

    @Test(expected = RuntimeException.class)
    public void testBadBounds() {
        MarsModel model = new MarsModel();
        MarsController controller = new MarsController(model);
        controller.next("A B");
    }

    @Test(expected = RuntimeException.class)
    public void testBadRover() {
        MarsModel model = new MarsModel();
        MarsController controller = new MarsController(model);
        controller.next("5 5");
        controller.next("0 0 EEE");
    }

    @Test(expected = RuntimeException.class)
    public void testBadCommands() {
        MarsModel model = new MarsModel();
        MarsController controller = new MarsController(model);
        controller.next("5 5");
        controller.next("0 0 E");
        controller.next("E");
    }

    private void testResults(MarsModel model, String expected) {
        StringWriter strw = new StringWriter();
        Renderer renderer = new PrintRenderer(new PrintWriter(strw));
        renderer.render(model);
        String output = strw.toString();
        assertEquals(expected, output);
    }
}