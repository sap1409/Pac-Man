package pacman.model.entity.dynamic.ghost.facade;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.state.*;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;


import static pacman.model.entity.dynamic.ghost.GhostMode.*;

public class GhostManagerFacade {

    private final GhostImpl ghost;

    // States for each mode
    private final GhostState chaseState = new ChaseState();
    private final GhostState scatterState = new ScatterState();
    private final GhostState frightenedState = new FrightenedState();

    public GhostManagerFacade(GhostImpl ghost) {
        this.ghost = ghost;
    }

    /**
     * Sets the current ghost mode and adjusts behavior accordingly.
     */
    public void setGhostMode(GhostMode mode, Image originalImage) {
        if (mode != FRIGHTENED) {
            this.ghost.setImage(originalImage);
        }
        switch (mode) {
            case CHASE -> {ghost.setCurrentState(chaseState); chaseState.enter(ghost);}
            case SCATTER -> {ghost.setCurrentState(scatterState); scatterState.enter(ghost);}
            case FRIGHTENED -> {ghost.setCurrentState(frightenedState); frightenedState.enter(ghost);}
        }
        this.ghost.getCurrentState().enter(ghost);
    }

    /**
     * Returns target location for Ghost object
     * @param currentState
     * @return
     */
    public Vector2D getTargetLocation(GhostState currentState) {
        if (currentState == null){
            return ghost.getTargetCorner();
        }
        return currentState.getTargetLocation(ghost);
    }

    public void manageEatGhost() {
        ghost.eaten();
        ghost.reset();
    }
}
