package pacman.model.entity.dynamic.ghost.state;

import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.Vector2D;

public class ScatterState implements GhostState {

    @Override
    public void enter(GhostImpl ghost) {
        ghost.startMode(GhostMode.SCATTER);
    }

    public Vector2D getTargetLocation(GhostImpl ghost) {
        return ghost.getTargetCorner();
    }
}
