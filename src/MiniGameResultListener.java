/**
 * Interface to determine if the mini-game was won or lost
 */
public interface MiniGameResultListener {
    /**
     * Returning true if the mini-game was won
     * @param won - retunring the boolean
     */
    void onMiniGameComplete(boolean won);
}
