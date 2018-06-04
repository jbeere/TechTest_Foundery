package foundery.techtest.marsrovers;

import java.io.PrintWriter;

// a renderer that can print a rover in text format
public class PrintRenderer implements Renderer {

    private final PrintWriter out;

    public PrintRenderer(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void render(MarsRover rover) {
        Coordinate coordinate = rover.getCoordinate();
        Orientation orientation = rover.getOrientation();
        out.printf("%d %d %s%n", coordinate.getX(), coordinate.getY(), orientation.name());
        out.flush();
    }
}
