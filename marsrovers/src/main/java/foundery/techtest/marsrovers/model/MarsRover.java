package foundery.techtest.marsrovers.model;

// the rover has coordinates
public class MarsRover {

    private Coordinate coordinate;
    private Orientation orientation;

    public MarsRover(Coordinate coordinate, Orientation orientation) {
        this.coordinate = coordinate;
        this.orientation = orientation;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

}
