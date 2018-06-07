package foundery.techtest.marsrovers.command;

import foundery.techtest.marsrovers.model.MarsRover;

// commands can do things to rovers
public interface Command {

    void apply(MarsRover rover);
}
