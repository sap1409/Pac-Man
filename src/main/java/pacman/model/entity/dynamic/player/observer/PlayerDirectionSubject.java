package pacman.model.entity.dynamic.player.observer;

/***
 * Subject that is being observed by PlayerPositionObserver
 */
public interface PlayerDirectionSubject {

    /**
     * Adds an observer to list of observers for subject
     *
     * @param observer observer for PlayerPositionSubject
     */
    void registerDirectionObserver(PlayerDirectionObserver observer);

    /**
     * Removes an observer from list of observers for subject
     *
     * @param observer observer for PlayerPositionObserver
     */
    void removeDirectionObserver(PlayerDirectionObserver observer);

    /**
     * Notifies observer of change in player's position
     */
    void notifyObservers();
}
