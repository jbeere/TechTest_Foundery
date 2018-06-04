package foundery.techtest.marsrovers;

public class TurnCommand implements Command {

    private final Direction direction;

    public TurnCommand(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void apply(MarsRover rover) {
        // the Orientation class knows which orientation is in which direction
        Orientation before = rover.getOrientation();
        Orientation after = before.turn(direction);
        rover.setOrientation(after);
    }
}
