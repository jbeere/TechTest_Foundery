package foundery.techtest.marsrovers;

// commands can do things to rovers
public interface Command {

    void apply(MarsRover rover);
}
