package pacman.model.entity.staticentity.collectable;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.staticentity.StaticEntityImpl;

/**
 * Represents the Pellet in Pac-Man game
 */
public class Pellet extends StaticEntityImpl implements Collectable {

    private final int points;
    private boolean isCollectable;
    private boolean isPowerPellet;

    public Pellet(BoundingBox boundingBox, Layer layer, Image image, int points, boolean isPowerPellet) {
        super(boundingBox, layer, image);
        this.points = points;
        this.isCollectable = true;
        this.isPowerPellet = isPowerPellet;
    }

    @Override
    public void collect() {
        this.isCollectable = false;
        setLayer(Layer.INVISIBLE);
    }

    @Override
    public void reset() {
        this.isCollectable = true;
        setLayer(Layer.BACKGROUND);
    }

    @Override
    public boolean isCollectable() {
        return this.isCollectable;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    public boolean isPowerPellet() {
        return isPowerPellet;
    }
}
