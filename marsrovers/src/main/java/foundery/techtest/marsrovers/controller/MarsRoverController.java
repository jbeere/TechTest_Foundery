package foundery.techtest.marsrovers.controller;

import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.MarsRover;

import java.util.List;

import static foundery.techtest.marsrovers.controller.MarsRoverUtil.*;
import static java.lang.String.format;

public class MarsRoverController {

    // the list of rovers that will take part in this
    private final List<MarsRover> rovers;

    // the spec didn't mention what to do with the bounds
    private Coordinate bounds;

    // the current line
    private int lineNo;

    // the current rover
    private MarsRover rover;

    public MarsRoverController(List<MarsRover> rovers) {
        this.rovers = rovers;
    }

    // executes the the input line by line
    public void next(String line) {
        int l = this.lineNo++;
        try {
            if (l == 0) {
                // first line is the bounds of the rectangular plateau
                this.bounds = parseBounds(line);
            } else if (l % 2 == 1) {
                // every odd line is a rover definition
                this.rover = parseRover(line);
                this.rovers.add(this.rover);
            } else if (l % 2 == 0) {
                // every even line (except line 0) is a string of commands
                executeCommandString(this.rover, line);
            }
        } catch (Exception e) {
            throw new RuntimeException(format("Error input line %d: %s", lineNo, e.getMessage()), e);
        }
    }
}
