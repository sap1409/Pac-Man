package pacman.model.entity.dynamic.player.observer;

import pacman.model.entity.dynamic.physics.Direction;

/***
 * Observer for PlayerPositionObserver
 */
public interface PlayerDirectionObserver {

    /**
     * Updates observer with the new position of the player
     *
     * @param direction the player's direction
     */
    void update(Direction direction);
}
