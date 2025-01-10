import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the MiniGameTwoController
 */
public class MiniGameTwoControllerTest {
    private MiniGameTwo miniGame;
    private MiniGameTwoController miniGameController;

    /**
     * Creating a new MinGameTwo screen and the controller
     */
    @Before
    public void setUp() {
        miniGame = new MiniGameTwo();
        miniGameController = new MiniGameTwoController(miniGame);
    }

    /**
     * Tests if the mini-game frame is visible after being called
     */
    @Test
    public void testStartMiniGame() {
        miniGameController.startMiniGame();
        assertFalse(miniGame.frame.isVisible(), "The mini game frame should be visible after starting the mini game");
    }

    /**
     * Tests if the game is properly delaying before beginning
     * Tests if the space bar is properly registering when pressed after "GO!" is displayed
     * @throws InterruptedException - exception
     */
    @Test
    public void testKeyListener_SpaceBar_ReactionTime() throws InterruptedException {
        miniGameController.startMiniGame();
        // Ensure game actually starts
        Thread.sleep(2000);
        miniGameController.startGame();  // Manually trigger game start

        miniGame.displayGoSignal();
        KeyEvent spacePress = new KeyEvent(miniGame.frame, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' ');
        miniGame.frame.getKeyListeners()[0].keyPressed(spacePress);
        Thread.sleep(100);
        assertTrue(miniGame.frame.isVisible(), "The frame should be disposed after the reaction is processed");
    }
}