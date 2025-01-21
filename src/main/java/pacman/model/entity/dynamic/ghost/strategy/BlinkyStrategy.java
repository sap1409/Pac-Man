package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public class BlinkyStrategy implements ChaseStrategy {
    @Override
    public Vector2D getTargetLocation(GhostImpl ghost) {
        return ghost.getPlayerPosition();  // Target Pac-Man’s current position
    }
}
