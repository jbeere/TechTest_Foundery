package foundery.techtest.marsrovers;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class MarsRoverRunner {

    private static final Pattern BOUNDS_PATTERN = Pattern.compile("^(\\d+)\\s+(\\d+)$"); // e.g. "5 5"
    private static final Pattern ROVER_PATTERN = Pattern.compile("^(\\d+)\\s+(\\d+)\\s+([NESW])$"); // e.g. "1 2 N"
    private static final Pattern COMMAND_PATTERN = Pattern.compile("^[LRM]+$"); // e.g. "LMLMLMLMM"

    // command map for easy lookup from the command string of characters, commands are immutable and reusable
    private static final Map<Integer, Command> COMMAND_MAP = ImmutableMap.<Integer, Command>builder()
            .put((int) 'L', new TurnCommand(Direction.LEFT))
            .put((int) 'R', new TurnCommand(Direction.RIGHT))
            .put((int) 'M', new MoveCommand())
            .build();

    // the list of rovers that will take part in this
    private final List<MarsRover> rovers;

    // the spec didn't mention what to do with the bounds
    private Coordinate bounds;

    // the current line
    private int lineNo;

    // the current rover
    private MarsRover rover;

    public MarsRoverRunner(List<MarsRover> rovers) {
        this.rovers = rovers;
    }

    // executes the the input line by line
    public void execute(String line) {
        int l = this.lineNo++;
        if (l == 0) {
            // first line is the bounds of the rectangular plateau
            this.bounds = parseBounds(line);
        } else if (l % 2 == 1) {
            // every odd line is a rover definition
            this.rover = parseRover(line);
            this.rovers.add(this.rover);
        } else if (l % 2 == 0) {
            // every even line (except line 1) is a string of commands
            executeCommandString(this.rover, line);
        }
    }

    private Coordinate parseBounds(String input) {
        Matcher matcher = BOUNDS_PATTERN.matcher(input);
        if (matcher.matches()) {
            int x = parseInt(matcher.group(1));
            int y = parseInt(matcher.group(2));
            return new Coordinate(x, y);
        } else {
            throw new IllegalArgumentException(lineNo + ": invalid bounds: " + input);
        }
    }

    private MarsRover parseRover(String input) {
        Matcher matcher = ROVER_PATTERN.matcher(input);
        if (matcher.matches()) {
            int x = parseInt(matcher.group(1));
            int y = parseInt(matcher.group(2));
            String o = matcher.group(3);
            Coordinate coordinate = new Coordinate(x, y);
            Orientation orientation = Orientation.valueOf(o);
            return new MarsRover(coordinate, orientation);
        } else {
            throw new IllegalArgumentException(lineNo + ": invalid rover initializer: " + input);
        }
    }

    private void executeCommandString(MarsRover rover, String commandString) {
        Matcher matcher = COMMAND_PATTERN.matcher(commandString);
        if (matcher.matches()) {
            // stream through the characters in the command string
            commandString.chars().sequential()
                    // lookup the matching command in the command map
                    .mapToObj(COMMAND_MAP::get)
                    // tell the rover to execute the command
                    .forEach(rover::execute);
        } else {
            throw new IllegalArgumentException(lineNo + ": invalid command input: " + commandString);
        }
    }
}
