import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.JButton;
import java.util.List;

/**
 * JUnit test for MiniGameOne class
 */
public class MiniGameOneTest {
    private MiniGameOne miniGame;

    /**
     * Creating a new MiniGame to test
     */
    @Before
    public void setUp() {
        miniGame = new MiniGameOne();
    }

    /**
     * Tests if the game starting properly initializes the timer
     */
    @Test
    public void testStartGameInitializesTimer() {
        // Start the game
        miniGame.startGame();
        // Check if timer is not null and started
        assertNotNull(miniGame.timer, "Timer should be initialized");
        assertTrue(miniGame.timer.isRunning(), "Timer should be running after starting the game");
    }

    /**
     * Tests if all the buttons were clicked
     */
    @Test
    public void testGameCompletesOnClickingAllButtons() {
        // Start the game
        miniGame.startGame();
        // Click all buttons
        for (JButton button : miniGame.buttons) {
            button.doClick();
        }
        // Verify that the current number is reset (meaning all buttons were clicked)
        assertEquals(2, miniGame.currentNumber, "All buttons should have been clicked");
    }
}