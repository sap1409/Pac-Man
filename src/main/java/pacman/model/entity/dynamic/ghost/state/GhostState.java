// GhostState.java
package pacman.model.entity.dynamic.ghost.state;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.physics.Vector2D;

public interface GhostState {
//    void update(GhostImpl ghost);   // Update ghost behavior for this state
    void enter(GhostImpl ghost);    // Actions to perform upon entering this state
    Vector2D getTargetLocation(GhostImpl ghost);   // Actions to get the target location
}
