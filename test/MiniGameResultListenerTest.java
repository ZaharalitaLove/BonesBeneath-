import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the MiniGameResultListener class interface
 */
class MiniGameResultListenerTest {

    /**
     * Private lass to test the result listener
     */
    private static class TestMiniGameResultListener implements MiniGameResultListener {
        boolean wasGameWon = false;

        /**
         * Creating a new mini-game to test
         * @param won - returning the boolean
         */
        @Override
        public void onMiniGameComplete(boolean won) {
            wasGameWon = won;
        }
    }

    /**
     * Testing if the mini-game was won
     */
    @Test
    void testMiniGameWon() {
        TestMiniGameResultListener listener = new TestMiniGameResultListener();
        listener.onMiniGameComplete(true);
        assertTrue(listener.wasGameWon, "The game should be marked as won.");
    }

    /**
     * Testing if the mini-game was lost
     */
    @Test
    void testMiniGameLost() {
        TestMiniGameResultListener listener = new TestMiniGameResultListener();
        listener.onMiniGameComplete(false);
        assertFalse(listener.wasGameWon, "The game should be marked as lost.");
    }
}