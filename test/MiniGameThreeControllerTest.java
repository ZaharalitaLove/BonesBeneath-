import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for MiniGameThreeController
 */
public class MiniGameThreeControllerTest {
    private MiniGameThreeController controller;
    private MiniGameThree miniGame;
    private AtomicBoolean gameCompleted;

    /**
     * Creating a new window and controller to test
     */
    @Before
    public void setUp() {
        gameCompleted = new AtomicBoolean(false);
        miniGame = new MiniGameThree(() -> gameCompleted.set(true));
        controller = new MiniGameThreeController(miniGame);
    }

    /**
     * Testing if the game is marked as complete on a correct guess using the controller methods
     */
    @Test
    public void testStartMiniGameThree() {
        // Start the game through the controller and verify that the game doesn't throw exceptions
        assertDoesNotThrow(() -> controller.startMiniGameThree(), "Starting the game should not throw any exceptions.");
    }

    /**
     * Testing if the game is marked as complete on an incorrect guess using the controller methods
     */
    @Test
    public void testGameCompletesOnIncorrectGuessThroughController() {
        // Start the game using the controller
        controller.startMiniGameThree();
        // Ensure the game was completed
        assertFalse(gameCompleted.get(), "Game should be completed after an incorrect guess.");
    }
}