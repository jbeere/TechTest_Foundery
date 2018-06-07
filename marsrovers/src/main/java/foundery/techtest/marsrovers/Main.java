package foundery.techtest.marsrovers;

import com.google.common.io.CharSource;
import foundery.techtest.marsrovers.controller.MarsController;
import foundery.techtest.marsrovers.model.MarsModel;
import foundery.techtest.marsrovers.view.PrintRenderer;
import foundery.techtest.marsrovers.view.Renderer;
import foundery.techtest.marsrovers.view.graphic.MarsPanel;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Files.asByteSource;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    public static void main(String... args) {
        try {
            File inputFile = getFile(args);
            CharSource input = asByteSource(inputFile).asCharSource(UTF_8);
            Renderer renderer = new PrintRenderer(System.out);
            execute(renderer, input, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void execute(Renderer renderer, CharSource input) throws IOException {
        execute(renderer, input, false);
    }

    public static void execute(Renderer renderer, CharSource input, boolean ui) throws IOException {
        // this model will be populated during the execution
        MarsModel model = new MarsModel();

        int wait = 0;
        if (ui) {
            wait = 1000;
            renderGraphical(model);
        }

        // this is where the magic happens
        MarsController controller = new MarsController(model, wait);

        // loop through each line in the input and have the runner execute it
        try (BufferedReader reader = input.openBufferedStream()) {
            reader.lines().forEach(controller::next);
        }

        // finally use the supplied renderer to output the results
        renderer.render(model);
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

    private static void renderGraphical(MarsModel model) {
        JFrame frame = new JFrame();
        MarsPanel marsPanel = new MarsPanel(model);
        model.addObserver((o, arg) -> marsPanel.repaint());
        frame.add(marsPanel);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
