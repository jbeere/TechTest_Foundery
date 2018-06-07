package foundery.techtest.marsrovers;

import com.google.common.io.CharSource;
import foundery.techtest.marsrovers.controller.MarsRoverController;
import foundery.techtest.marsrovers.model.MarsRover;
import foundery.techtest.marsrovers.view.PrintRenderer;
import foundery.techtest.marsrovers.view.Renderer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Files.asByteSource;

public class Main {

    public static void main(String... args) {
        try {
            File inputFile = getFile(args);
            CharSource input = asByteSource(inputFile).asCharSource(UTF_8);
            Renderer renderer = new PrintRenderer(System.out);
            execute(renderer, input);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void execute(Renderer renderer, CharSource input) throws IOException {
        // this model will be populated during the execution
        List<MarsRover> rovers = new ArrayList<>();

        // this is where the magic happens
        MarsRoverController controller = new MarsRoverController(rovers);

        // loop through each line in the input and have the runner execute it
        try (BufferedReader reader = input.openBufferedStream()) {
            reader.lines().forEach(controller::next);
        }

        // finally use the supplied renderer to output the results
        rovers.forEach(renderer::render);
    }

    public static File getFile(String... args) {
        // using jOpt was probably overkill, but hey, it cuts down on that kind of code!
        OptionParser optionParser = new OptionParser();
        OptionSpec<File> fileArg = optionParser.accepts("f").withRequiredArg().ofType(File.class).required();

        OptionSet optionSet = optionParser.parse(args);
        File inputFile = fileArg.value(optionSet);

        if (!inputFile.exists()) {
            throw new IllegalArgumentException(String.format("file %s does not exist", inputFile.getAbsolutePath()));
        }
        return inputFile;
    }
}
