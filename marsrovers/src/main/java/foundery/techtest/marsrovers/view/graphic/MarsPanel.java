package foundery.techtest.marsrovers.view.graphic;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import foundery.techtest.marsrovers.model.Coordinate;
import foundery.techtest.marsrovers.model.MarsModel;
import foundery.techtest.marsrovers.model.MarsRover;
import foundery.techtest.marsrovers.model.Orientation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.Color.*;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class MarsPanel extends JPanel {

    public static final ByteSource BACKGROUND = Resources.asByteSource(Resources.getResource("mars.jpg"));
    private static final Color[] COLORS = {
            BLUE, GREEN, RED, YELLOW, PINK
    };

    private final MarsModel model;
    private final Image image;

    public MarsPanel(MarsModel model) {
        this.model = model;
        try (InputStream in = BACKGROUND.openStream()) {
            this.image = ImageIO.read(in);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, new AffineTransform(), null);
            g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            Coordinate bounds = model.getBounds();
            if (bounds != null) {
                g2.setColor(Color.WHITE);
                float areaWidth = this.getWidth();
                float areaHeight = this.getHeight();
                int cols = bounds.getX() + 1;
                int rows = bounds.getY() + 1;
                float width = areaWidth / cols;
                float height = areaHeight / rows;
                for (int x = 0; x <= bounds.getX(); x++) {
                    g2.draw(new Line2D.Float(x * width, 0, x * width, areaHeight));
                }
                for (int y = 0; y <= bounds.getY(); y++) {
                    g2.draw(new Line2D.Float(0, y * height, areaWidth, y * height));
                }

                java.util.List<MarsRover> rovers = model.getRovers();
                for (int i = 0; i < rovers.size(); i++) {
                    g2.setColor(COLORS[i % COLORS.length]);
                    MarsRover rover = rovers.get(i);
                    Orientation orientation = rover.getOrientation();
                    Coordinate coordinate = rover.getCoordinate();
                    Shape roverShape = createRoverShape();
                    AffineTransform transform = new AffineTransform();
                    int x = coordinate.getX();
                    int y = coordinate.getY();
                    float w2 = width / 2;
                    float h2 = height / 2;
                    transform.translate((width * x) + w2, areaHeight - (y * height) - h2);
                    transform.rotate(orientation.getAngle());
                    transform.scale(w2, h2);
                    Shape transformed = transform.createTransformedShape(roverShape);
                    g2.fill(transformed);
                    g2.setColor(Color.WHITE);
                    g2.draw(transformed);
                }
            }
        }
    }

    private Shape createRoverShape() {
        GeneralPath path = new GeneralPath();
        path.moveTo(0, 0);
        path.lineTo(-1, 1);
        path.lineTo(1, 0);
        path.lineTo(-1, -1);
        path.closePath();
        return path;
    }
}
