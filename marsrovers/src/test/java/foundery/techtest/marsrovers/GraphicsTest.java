package foundery.techtest.marsrovers;

import com.google.common.io.CharSource;
import foundery.techtest.marsrovers.view.PrintRenderer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Resources.asCharSource;
import static com.google.common.io.Resources.getResource;

public class GraphicsTest {

    private static final CharSource TEST_INPUT = asCharSource(getResource("test_input.txt"), UTF_8);

    public static void main(String ... args) throws IOException {
        StringWriter strw = new StringWriter();
        PrintWriter out = new PrintWriter(strw);
        PrintRenderer renderer = new PrintRenderer(out);
        Main.execute(renderer, TEST_INPUT, true);
        System.out.println(strw);
    }
}
