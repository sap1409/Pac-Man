package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class ClydeStrategy implements ChaseStrategy {

    private static final int DISTANCE_AHEAD = 8;

    @Override
    public Vector2D getTargetLocation(GhostImpl ghost) {
        double distance = Vector2D.calculateEuclideanDistance(ghost.getPosition(), ghost.getPlayerPosition());
        if (distance > DISTANCE_AHEAD) {
            return ghost.getPlayerPosition();  // Chase Pac-Man
        } else {
            return ghost.getTargetCorner();  // Head to the bottom-left corner
        }
    }
}
