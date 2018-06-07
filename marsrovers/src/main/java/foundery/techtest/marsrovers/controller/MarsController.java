package foundery.techtest.marsrovers.controller;

import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.MarsModel;
import foundery.techtest.marsrovers.model.MarsRover;

import static foundery.techtest.marsrovers.controller.MarsUtil.*;
import static java.lang.String.format;

public class MarsController {

    // the list of rovers that will take part in this
    private final MarsModel model;

    // the time to wait between commands
    private int wait;

    // the current line
    private int lineNo;

    // the current rover
    private MarsRover rover;

    public MarsController(MarsModel model) {
        this(model, 0);
    }

    public MarsController(MarsModel model, int wait) {
        this.model = model;
        this.wait = wait;
    }

    // executes the the input line by line
    public void next(String line) {
        int l = this.lineNo++;
        try {
            if (l == 0) {
                // first line is the bounds of the rectangular plateau
                Coordinate bounds = parseBounds(line);
                this.model.setBounds(bounds);
            } else if (l % 2 == 1) {
                // every odd line is a rover definition
                this.rover = parseRover(line);
                this.model.addRover(this.rover);
            } else if (l % 2 == 0) {
                // every even line (except line 0) is a string of commands
                executeCommandString(this.rover, line, wait);
            }
        } catch (Exception e) {
            throw new RuntimeException(format("Error input line %d: %s", lineNo, e.getMessage()), e);
        }
    }
}
