package pacman.model.entity.dynamic.ghost;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.facade.GhostManagerFacade;
import pacman.model.entity.dynamic.ghost.state.FrightenedState;
import pacman.model.entity.dynamic.ghost.state.GhostState;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.KinematicStateImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.level.Level;
import pacman.model.maze.Maze;

/**
 * Concrete implementation of Ghost entity in Pac-Man Game
 */
public class GhostImpl implements Ghost {

    private static final int minimumDirectionCount = 8;
    public static final Image frightenedImage = new Image("maze/ghosts/frightened.png");
    private final Layer layer = Layer.FOREGROUND;
    private Image image;
    private final Image originalImage;
    private final BoundingBox boundingBox;
    private final Vector2D startingPosition;
    private final Vector2D targetCorner;
    private KinematicState kinematicState;
    private GhostMode ghostMode;
    private Vector2D targetLocation;
    private Vector2D playerPosition;
    private Direction playerDirection;
    private Direction currentDirection;
    private Set<Direction> possibleDirections;
    private Map<GhostMode, Double> speeds;
    private int currentDirectionCount = 0;
    private ChaseStrategy chaseStrategy;
    private boolean eaten = false;
    private GhostState currentState;
    private int eatenTickCounter = 0;
    private GhostManagerFacade facade;


    public GhostImpl(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, Vector2D targetCorner, ChaseStrategy strategy) {
        this.image = image;
        this.originalImage = image;
        this.boundingBox = boundingBox;
        this.kinematicState = kinematicState;
        this.startingPosition = kinematicState.getPosition();
        this.ghostMode = ghostMode;
        this.possibleDirections = new HashSet<>();
        this.targetCorner = targetCorner;
        this.targetLocation = getTargetLocation();
        this.currentDirection = null;
        this.chaseStrategy = strategy;
        this.facade = new GhostManagerFacade(this);
    }

    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        this.speeds = speeds;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Image getImage() {
        return image;
    }

    public Image getOriginalImage() {
        return originalImage;
    }

    @Override
    public void update() {
        if (eaten){
            increaseEatenTick();
            if (eatenTickCounter == 30){
                eaten = false;
                resetEatenTickCounter();
            } else {
                return;
            }
        }
        this.updateDirection();
        this.kinematicState.update();
        this.boundingBox.setTopLeft(this.kinematicState.getPosition());
    }

    private void updateDirection() {
        // Ghosts update their target location when they reach an intersection
        if (Maze.isAtIntersection(this.possibleDirections)) {
            this.targetLocation = getTargetLocation();
        }

        Direction newDirection = selectDirection(possibleDirections);

        // Ghosts have to continue in a direction for a minimum time before changing direction
        if (this.currentDirection != newDirection) {
            this.currentDirectionCount = 0;
        }
        this.currentDirection = newDirection;

        switch (currentDirection) {
            case LEFT -> this.kinematicState.left();
            case RIGHT -> this.kinematicState.right();
            case UP -> this.kinematicState.up();
            case DOWN -> this.kinematicState.down();
        }
    }

    public ChaseStrategy getChaseStrategy(){
        return chaseStrategy;
    }

    private Vector2D getTargetLocation() {
        if (facade != null) {
            return facade.getTargetLocation(currentState);
        }
        else {
            return targetCorner;
        }
    }

    public Vector2D getFrightenedLocation() {
        Vector2D upVector = new Vector2D(0, 16*2);
        Vector2D downVector = new Vector2D(0, -16*2);
        Vector2D leftVector = new Vector2D(-16*2, 0);
        Vector2D rightVector = new Vector2D(16*2, 0);
        if (getRandomDirection() == Direction.UP){
            return this.getPosition().add(upVector);
        } else if (getRandomDirection() == Direction.DOWN){
            return this.getPosition().add(downVector);
        } else if (getRandomDirection() == Direction.LEFT){
            return this.getPosition().add(leftVector);
        } else {
            return this.getPosition().add(rightVector);
        }
    }

    private Direction getRandomDirection() {
        Random random = new Random();
        if (possibleDirections.isEmpty()) {
            return null;  // No directions available
        }

        // Convert the set to an array
        Direction[] directionsArray = possibleDirections.toArray(new Direction[0]);

        // Pick a random index
        int randomIndex = random.nextInt(directionsArray.length);

        // Return the direction at the random index
        return directionsArray[randomIndex];
    }

    private Direction selectDirection(Set<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return currentDirection;
        }

        // ghosts have to continue in a direction for a minimum time before changing direction
        if (currentDirection != null && currentDirectionCount < minimumDirectionCount) {
            currentDirectionCount++;
            return currentDirection;
        }

        Map<Direction, Double> distances = new HashMap<>();

        for (Direction direction : possibleDirections) {
            // ghosts never choose to reverse travel
            if (currentDirection == null || direction != currentDirection.opposite()) {
                distances.put(direction, Vector2D.calculateEuclideanDistance(this.kinematicState.getPotentialPosition(direction), this.targetLocation));
            }
        }

        // only go the opposite way if trapped
        if (distances.isEmpty()) {
            return currentDirection.opposite();
        }

        // select the direction that will reach the target location fastest
        return Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public boolean isFrightened() {
        return ghostMode == GhostMode.FRIGHTENED;
    }

    public void eaten(){
//        setPosition(startingPosition);
//        setGhostMode(GhostMode.getNextGhostMode(ghostMode));
        eaten = true;
    }

    @Override
    public void setGhostMode(GhostMode ghostMode) {
        if(this.speeds == null){
            this.ghostMode = ghostMode;
            return;
        }
        facade.setGhostMode(ghostMode, originalImage);
    }

    public void setCurrentState(GhostState state) {
        this.currentState = state;
    }

    public GhostState getCurrentState() {
        return currentState;
    }

    public GhostMode getGhostMode(){
        return ghostMode;
    }

    public void startMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
        this.kinematicState.setSpeed(speeds.get(ghostMode));
        // ensure direction is switched
        this.currentDirectionCount = minimumDirectionCount;
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return boundingBox.collidesWith(kinematicState.getSpeed(), kinematicState.getDirection(), renderable.getBoundingBox());
    }

    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (level.isPlayer(renderable)) {
            if (currentState instanceof FrightenedState) {
                facade.manageEatGhost();
                level.addConsecutiveGhostsEaten();
                level.addGhostEatingPoints();
            } else {
                level.handleLoseLife();
            }
        }
    }

    @Override
    public void update(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return this.kinematicState.getPreviousPosition();
    }

    @Override
    public double getHeight() {
        return this.boundingBox.getHeight();
    }

    @Override
    public double getWidth() {
        return this.boundingBox.getWidth();
    }

    @Override
    public Vector2D getPosition() {
        return this.kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D position) {
        this.kinematicState.setPosition(position);
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void reset() {
        // return ghost to starting position
        this.kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(startingPosition)
                .build();
        this.boundingBox.setTopLeft(startingPosition);
        setGhostMode(GhostMode.SCATTER);
        this.currentDirectionCount = minimumDirectionCount;
        this.currentDirection = selectDirection(possibleDirections);
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        this.possibleDirections = possibleDirections;
    }

    @Override
    public Direction getDirection() {
        return this.kinematicState.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return new Vector2D(boundingBox.getMiddleX(), boundingBox.getMiddleY());
    }

    public Vector2D getPlayerPosition() {
        return this.playerPosition;
    }

    public Vector2D getTargetCorner() {
        return this.targetCorner;
    }

    @Override
    public void update(Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    public Direction getPlayerDirection() {
        return playerDirection;
    }

    public void increaseEatenTick(){
        eatenTickCounter++;
    }

    public int getEatenTickCounter(){
        return eatenTickCounter;
    }

    public void resetEatenTickCounter(){
        eatenTickCounter = 0;
    }
}
