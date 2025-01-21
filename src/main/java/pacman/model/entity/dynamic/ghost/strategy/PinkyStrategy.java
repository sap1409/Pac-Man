package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class PinkyStrategy implements ChaseStrategy {

    private static final double TILE_SIZE = 16.0;
    private static final int DISTANCE_AHEAD = 4;

    @Override
    public Vector2D getTargetLocation(GhostImpl ghost) {
        Vector2D playerPosition = ghost.getPlayerPosition();
        Direction direction = ghost.getPlayerDirection();
        return calculateTargetPosition(playerPosition, direction);
    }

    private Vector2D calculateTargetPosition(Vector2D playerPosition, Direction direction) {
        double targetX = playerPosition.getX();
        double targetY = playerPosition.getY();

        switch (direction) {
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
}
