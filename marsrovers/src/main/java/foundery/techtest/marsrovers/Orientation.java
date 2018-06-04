package foundery.techtest.marsrovers;

// each orientation is aware of it's effect on a coordinate and in which direction other orientations are
public enum Orientation {

    N(0, 1, 3, 1), // moves x+0, y+1, turns left=west, right=east
    E(1, 0, 0, 2),
    S(0, -1, 1, 3),
    W(-1, 0, 2, 0);

    private final int xDelta;
    private final int yDelta;
    private final int leftIndex;
    private final int rightIndex;

    Orientation(int xDelta, int yDelta, int leftIndex, int rightIndex) {
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    public Coordinate move(Coordinate start) {
        return new Coordinate(start.getX() + xDelta, start.getY() + yDelta);
    }

    public Orientation turn(Direction direction) {
        // since it's only left and right, a switch makes the code quite clear
        switch (direction) {
            case LEFT:
                return values()[leftIndex];
            case RIGHT:
                return values()[rightIndex];
            default:
                // shouldn't happen
                throw new IllegalArgumentException(direction.name());
        }
    }
}
