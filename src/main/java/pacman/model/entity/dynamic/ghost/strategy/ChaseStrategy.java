package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.level.Level;

public interface ChaseStrategy {

    Vector2D getTargetLocation(GhostImpl ghost);
}
