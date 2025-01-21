package pacman.model.entity.dynamic.ghost;

import pacman.model.entity.dynamic.ghost.state.GhostState;

/***
 * Represents the different modes of ghosts, which determines how ghosts choose their target locations
 */
public enum GhostMode {
    SCATTER,
    CHASE,
    FRIGHTENED;

    /**
     * Ghosts alternate between SCATTER and CHASE mode normally
     *
     * @param ghostMode current ghost mode
     * @return next ghost mode
     */
    public static GhostMode getNextGhostMode(GhostMode ghostMode) {
        if (ghostMode == FRIGHTENED) return SCATTER;
        return ghostMode == SCATTER ? CHASE : SCATTER;
    }

}