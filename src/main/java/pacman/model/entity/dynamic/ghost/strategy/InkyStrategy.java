package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyStrategy implements ChaseStrategy {

    private static Ghost blinky;
    private static final double TILE_SIZE = 16.0;
    private static final int DISTANCE_AHEAD = 2;

    @Override
    public Vector2D getTargetLocation(GhostImpl ghost) {
        Vector2D playerPosition = ghost.getPlayerPosition();
        Direction playerDirection = ghost.getPlayerDirection();

        Vector2D targetPosition = calculatePacmanTargetPosition(playerPosition, playerDirection);
        return calculateFinalTarget(targetPosition);
    }

    private Vector2D calculatePacmanTargetPosition(Vector2D playerPosition, Direction playerDirection) {
        double targetX = playerPosition.getX();
        double targetY = playerPosition.getY();

        switch (playerDirection) {
            case RIGHT:
                targetX += TILE_SIZE * DISTANCE_AHEAD;
                break;
            case LEFT:
                targetX -= TILE_SIZE * DISTANCE_AHEAD;
                break;
            case UP:
                targetY -= TILE_SIZE * DISTANCE_AHEAD;
                break;
            case DOWN:
                targetY += TILE_SIZE * DISTANCE_AHEAD;
                break;
        }

        return new Vector2D(targetX, targetY);
    }

    private Vector2D calculateFinalTarget(Vector2D targetPosition) {

        double finalX = 2 * (targetPosition.getX() - blinky.getPosition().getX());
        double finalY = 2 * (targetPosition.getY() - blinky.getPosition().getY());
        return new Vector2D(finalX, finalY);
    }

    public static void setBlinky(Ghost ghost) {
        blinky = ghost;
    }
}

