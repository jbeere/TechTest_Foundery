package foundery.techtest.marsrovers;

import com.google.common.io.CharSource;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Files.asByteSource;

public class Main {

    public static void main(String... args) {
        try {
            PrintWriter out = new PrintWriter(System.out);
            execute(out, args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void execute(PrintWriter out, String... args) throws Exception {
        // using jOpt was probably overkill, but hey, it cuts down on that kind of code!
        OptionParser optionParser = new OptionParser();
        OptionSpec<File> fileArg = optionParser.accepts("f").withRequiredArg().ofType(File.class).required();

        OptionSet optionSet = optionParser.parse(args);
        File inputFile = fileArg.value(optionSet);

        if (!inputFile.exists()) {
            throw new IllegalArgumentException(String.format("file %s does not exist", inputFile.getAbsolutePath()));
        }

        // guava has become almost mandatory, if available, use it
        CharSource input = asByteSource(inputFile).asCharSource(UTF_8);

        // this list will be populated during the execution
        List<MarsRover> rovers = new ArrayList<>();

        // this is where the magic happens
        MarsRoverRunner runner = new MarsRoverRunner(rovers);

        // loop through each line in the input and have the runner execute it
        try (BufferedReader reader = input.openBufferedStream()) {
            reader.lines().forEach(runner::execute);
        }

        // finally use our fancy print renderer to output the results
        PrintRenderer renderer = new PrintRenderer(out);
        rovers.forEach(renderer::render);
    }
}
