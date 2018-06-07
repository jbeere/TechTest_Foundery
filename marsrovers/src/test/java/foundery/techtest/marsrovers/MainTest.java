package foundery.techtest.marsrovers;

import com.google.common.io.CharSource;
import foundery.techtest.marsrovers.view.PrintRenderer;
import joptsimple.OptionException;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.asCharSource;
import static com.google.common.io.Resources.getResource;
import static org.junit.Assert.assertEquals;

public class MainTest {

    private static final CharSource TEST_INPUT = asCharSource(getResource("test_input.txt"), UTF_8);

    @Test(expected = OptionException.class)
    public void testEmptyFileArg() throws Exception {
        Main.getFile();
    }

    @Test(expected = OptionException.class)
    public void testNoFileArg() throws Exception {
        Main.getFile("-f");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputFileNotExists() throws Exception {
        Main.getFile("-f", "exist.not");
    }

    @Test
    public void testFullExecution() throws IOException {
        String expected = String.format("1 3 N%n5 1 E%n");
        StringWriter strw = new StringWriter();
        PrintWriter out = new PrintWriter(strw);
        PrintRenderer renderer = new PrintRenderer(out);
        Main.execute(renderer, TEST_INPUT);
        String output = strw.toString();
        assertEquals(expected, output);
    }
}