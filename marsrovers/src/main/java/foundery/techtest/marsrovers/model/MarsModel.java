package foundery.techtest.marsrovers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MarsModel extends Observable {

    private Coordinate bounds;
    private List<MarsRover> rovers;

    public MarsModel() {
        this(new ArrayList<>());
    }

    public void addRover(MarsRover rover) {
        rover.addObserver((o, arg) -> {
           MarsModel.this.setChanged();
           MarsModel.this.notifyObservers(arg);
        });
        this.rovers.add(rover);
        setChanged();
        notifyObservers();
    }

    public MarsModel(List<MarsRover> rovers) {
        this.rovers = rovers;
    }

    public Coordinate getBounds() {
        return bounds;
    }

    public void setBounds(Coordinate bounds) {
        this.bounds = bounds;
        setChanged();
        notifyObservers();
    }

    public List<MarsRover> getRovers() {
        return rovers;
    }

    public void setRovers(List<MarsRover> rovers) {
        this.rovers = rovers;
    }
}
