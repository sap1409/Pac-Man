package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.collectable.Pellet;

/**
 * Concrete renderable factory for Pellet objects
 */
public class PelletFactory implements RenderableFactory {
    private static final Image PELLET_IMAGE = new Image("maze/pellet.png");
    private static final int REGULAR_PELLET_POINTS = 10;
    private static final int POWER_PELLET_POINTS = 50;
    private static final double POWER_PELLET_SCALE = 2.0;  // Scale factor for power pellet
    private static final Vector2D POWER_PELLET_OFFSET = new Vector2D(-8, -8);  // Offset for power pellet
    private final Renderable.Layer layer = Renderable.Layer.BACKGROUND;
    private final boolean isPowerPellet;

    public PelletFactory(char pelletType) {
        if (pelletType == 'z') {
            this.isPowerPellet = true;
        } else {
            this.isPowerPellet = false;
        }
    }

    @Override
    public Renderable createRenderable(
            Vector2D position
    ) {
        try {

            Image pelletImage;
            int points;

            if (isPowerPellet) {
                pelletImage = new Image(PELLET_IMAGE.getUrl(),
                        PELLET_IMAGE.getWidth() * POWER_PELLET_SCALE,
                        PELLET_IMAGE.getHeight() * POWER_PELLET_SCALE,
                        false, false);  // Resize the image for power pellet
                points = POWER_PELLET_POINTS;
                position = position.add(POWER_PELLET_OFFSET);  // Apply offset for power pellet
            } else {
                pelletImage = PELLET_IMAGE;  // Use original image size for regular pellet
                points = REGULAR_PELLET_POINTS;
            }

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    pelletImage.getHeight(),
                    pelletImage.getWidth()
            );

            return new Pellet(
                    boundingBox,
                    layer,
                    pelletImage,
                    points,
                    isPowerPellet
            );

        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid pellet configuration | %s", e));
        }
    }
}
