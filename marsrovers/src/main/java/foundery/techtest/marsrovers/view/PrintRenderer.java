package foundery.techtest.marsrovers.view;

import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.MarsModel;
import foundery.techtest.marsrovers.model.MarsRover;
import foundery.techtest.marsrovers.model.Orientation;

import java.io.PrintStream;
import java.io.PrintWriter;

// a renderer that can print a rover in text format
public class PrintRenderer implements Renderer {

    private final PrintWriter out;

    public PrintRenderer(PrintStream out) {
        this(new PrintWriter(out));
    }

    public PrintRenderer(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void render(MarsModel model) {
        for (MarsRover rover : model.getRovers()) {
            Coordinate coordinate = rover.getCoordinate();
            Orientation orientation = rover.getOrientation();
            out.printf("%d %d %s%n", coordinate.getX(), coordinate.getY(), orientation.name());
        }
        out.flush();
    }
}
