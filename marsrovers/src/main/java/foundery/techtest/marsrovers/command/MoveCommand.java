package foundery.techtest.marsrovers.command;

import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.MarsRover;
import foundery.techtest.marsrovers.model.Orientation;

public class MoveCommand implements Command {

    @Override
    public void apply(MarsRover rover) {
        // the Orientation class knows how to move to another coordinate
        Orientation orientation = rover.getOrientation();
        Coordinate before = rover.getCoordinate();
        Coordinate after = orientation.move(before);
        rover.setCoordinate(after);
    }
}
