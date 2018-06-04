package foundery.techtest.marsrovers;

import joptsimple.OptionException;
import org.junit.Test;

import java.io.PrintWriter;

public class MainTest {

    @Test(expected = OptionException.class)
    public void testEmpty() throws Exception {
        Main.execute(new PrintWriter(System.out), "");
    }

    @Test(expected = OptionException.class)
    public void testNoArg() throws Exception {
        Main.execute(new PrintWriter(System.out), "-f");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputFileNotExists() throws Exception {
        Main.execute(new PrintWriter(System.out), "-f exist.not");
    }
}