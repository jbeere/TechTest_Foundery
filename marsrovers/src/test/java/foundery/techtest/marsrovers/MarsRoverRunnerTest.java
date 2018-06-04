package foundery.techtest.marsrovers;

import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MarsRoverRunnerTest {

    @Test
    public void testInput() throws Exception {
        List<MarsRover> rovers = new ArrayList<>();

        MarsRoverRunner runner = new MarsRoverRunner(rovers);
        runner.execute("5 5");
        runner.execute("1 2 N");
        runner.execute("LMLMLMLMM");
        runner.execute("3 3 E");
        runner.execute("MMRMMRMRRM");

        StringWriter strw = new StringWriter();
        Renderer renderer = new PrintRenderer(new PrintWriter(strw));
        rovers.forEach(renderer::render);

        String expected = String.format("1 3 N%n5 1 E%n");
        String output = strw.toString();
        assertEquals(expected, output);
    }
}