package foundery.techtest.marsrovers.controller;

import com.google.common.collect.ImmutableMap;
import foundery.techtest.marsrovers.command.Command;
import foundery.techtest.marsrovers.command.MoveCommand;
import foundery.techtest.marsrovers.command.TurnCommand;
import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.MarsRover;
import foundery.techtest.marsrovers.model.Orientation;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static foundery.techtest.marsrovers.model.Direction.LEFT;
import static foundery.techtest.marsrovers.model.Direction.RIGHT;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class MarsRoverUtil {

    private static final Pattern BOUNDS_PATTERN = Pattern.compile("^(\\d+)\\s+(\\d+)$"); // e.g. "5 5"
    private static final Pattern ROVER_PATTERN = Pattern.compile("^(\\d+)\\s+(\\d+)\\s+([NESW])$"); // e.g. "1 2 N"
    private static final Pattern COMMAND_PATTERN = Pattern.compile("^[LRM]*$"); // e.g. "LMLMLMLMM"

    // command map for easy lookup from the command string of characters, commands are immutable and reusable
    private static final Map<Integer, Command> COMMAND_MAP = ImmutableMap.<Integer, Command>builder()
            .put((int) 'L', new TurnCommand(LEFT))
            .put((int) 'R', new TurnCommand(RIGHT))
            .put((int) 'M', new MoveCommand())
            .build();

    public static Coordinate parseBounds(String input) {
        Matcher matcher = BOUNDS_PATTERN.matcher(input);
        if (matcher.matches()) {
            int x = parseInt(matcher.group(1));
            int y = parseInt(matcher.group(2));
            return new Coordinate(x, y);
        } else {
            throw new IllegalArgumentException(format("invalid bounds: '%s'", input));
        }
    }

    public static MarsRover parseRover(String input) {
        Matcher matcher = ROVER_PATTERN.matcher(input);
        if (matcher.matches()) {
            int x = parseInt(matcher.group(1));
            int y = parseInt(matcher.group(2));
            String o = matcher.group(3);
            Coordinate coordinate = new Coordinate(x, y);
            Orientation orientation = Orientation.valueOf(o);
            return new MarsRover(coordinate, orientation);
        } else {
            throw new IllegalArgumentException(format("invalid rover initializer: '%s'", input));
        }
    }

    public static void executeCommandString(MarsRover rover, String commandString) {
        Matcher matcher = COMMAND_PATTERN.matcher(commandString);
        if (matcher.matches()) {
            // stream through the characters in the command string
            commandString.chars().sequential()
                    // lookup the matching command in the command map
                    .mapToObj(COMMAND_MAP::get)
                    // tell the rover to execute the command
                    .forEach(c -> c.apply(rover));
        } else {
            throw new IllegalArgumentException(format("invalid command input: '%s'", commandString));
        }
    }
}
