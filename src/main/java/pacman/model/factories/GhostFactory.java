package pacman.model.factories;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.strategy.BlinkyStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ClydeStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyStrategy;
import pacman.model.entity.dynamic.ghost.strategy.PinkyStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.KinematicStateImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

/**
 * Concrete renderable factory for Ghost objects
 */
public class GhostFactory implements RenderableFactory {

    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;

    private static final Map<Character, Image> GHOST_IMAGES = new HashMap<>();
    private static final Image BLINKY_IMAGE = new Image("maze/ghosts/blinky.png");
    private static final Image INKY_IMAGE = new Image("maze/ghosts/inky.png");
    private static final Image CLYDE_IMAGE = new Image("maze/ghosts/clyde.png");
    private static final Image PINKY_IMAGE = new Image("maze/ghosts/pinky.png");
    static {
        GHOST_IMAGES.put(RenderableType.BLINKY, BLINKY_IMAGE);
        GHOST_IMAGES.put(RenderableType.INKY, INKY_IMAGE);
        GHOST_IMAGES.put(RenderableType.CLYDE, CLYDE_IMAGE);
        GHOST_IMAGES.put(RenderableType.PINKY, PINKY_IMAGE);
    }
    private final Image GHOST_IMAGE;
    private final Vector2D TARGET_CORNER;
    private static final Map<Character, Vector2D> targetCorners = Map.of(
            RenderableType.BLINKY, new Vector2D(RIGHT_X_POSITION_OF_MAP, TOP_Y_POSITION_OF_MAP),
            RenderableType.PINKY, new Vector2D(0, TOP_Y_POSITION_OF_MAP),
            RenderableType.INKY, new Vector2D(RIGHT_X_POSITION_OF_MAP, BOTTOM_Y_POSITION_OF_MAP),
            RenderableType.CLYDE, new Vector2D(0, BOTTOM_Y_POSITION_OF_MAP)
    );
    private final char ghostType;


    public GhostFactory(char ghostType) {
        this.GHOST_IMAGE = GHOST_IMAGES.get(ghostType);
        this.TARGET_CORNER = targetCorners.get(ghostType);
        this.ghostType = ghostType;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public Renderable createRenderable(
            Vector2D position
    ) {
        try {

            ChaseStrategy strategy = switch (this.ghostType) {
                case 'b' -> new BlinkyStrategy();
                case 's' -> new PinkyStrategy();
                case 'i' -> new InkyStrategy();
                case 'c' -> new ClydeStrategy();
                default -> throw new IllegalArgumentException("Unknown ghost type");
            };

            position = position.add(new Vector2D(4, -4));

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    GHOST_IMAGE.getHeight(),
                    GHOST_IMAGE.getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            return new GhostImpl(
                    GHOST_IMAGE,
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER,
                    TARGET_CORNER,
                    strategy);
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e));
        }
    }


}
