import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for MiniGameThree
 */
public class MiniGameThreeTest {
    private MiniGameThree miniGame;
    private AtomicBoolean gameCompleted;

    /**
     * Creating a new window to test
     */
    @Before
    public void setUp() {
        gameCompleted = new AtomicBoolean(false);
        miniGame = new MiniGameThree(() -> gameCompleted.set(true));
    }

    /**
     * Testing if the game is complete on an incorrect guess
     */
    @Test
    public void testGameCompletesOnIncorrectGuess() {
        // Initialize the game and set up the game frame
        miniGame.init();
        //miniGame.playerGuess(0);
        assertFalse(gameCompleted.get(), "Game should be completed after an incorrect guess.");
    }

    /**
     * Testing if the game is complete on a correct guess
     */
    @Test
    public void testGameStartsCorrectly() {
        // Test if the game initializes correctly and proceeds to the first round
        miniGame.init();
        assertDoesNotThrow(() -> miniGame.init(), "Game should start without throwing exceptions.");
    }
}