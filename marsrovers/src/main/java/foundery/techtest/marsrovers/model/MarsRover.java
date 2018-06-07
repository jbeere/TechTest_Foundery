package foundery.techtest.marsrovers.model;

import java.util.Observable;

public class MarsRover extends Observable {

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
        this.setChanged();
        this.notifyObservers(coordinate);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        this.setChanged();
        this.notifyObservers(orientation);
    }

}
