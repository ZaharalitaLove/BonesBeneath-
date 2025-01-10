import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit test for MiniGameTwo class
 */
public class MiniGameTwoTest {
    private MiniGameTwo miniGame;

    /**
     * Creating a new MiniGameTwo
     */
    @Before
    public void setUp() {
        miniGame = new MiniGameTwo();
    }

    /**
     * Tests the "Go" signal that the user must then react to
     * Checks that it is properly displayed
     */
    @Test
    public void testDisplayGoSignal() {
        miniGame.displayGoSignal();
        JLabel instructionLabel = (JLabel) miniGame.miniGamePanel.getComponent(0);
        assertTrue(instructionLabel.getText().equals("GO!"), "Instruction label should display 'GO!'");
    }

    /**
     * Tests if the game properly stores the reaction time if the user wins
     */
    @Test
    public void testShowReactionTime_Win() {
        miniGame.showReactionTime(400, () -> {});
        assertTrue(miniGame.isGameWon(), "The game should be won with a reaction time less than 450ms");
    }

    /**
     * Tests if the game properly stores the reaction time if the user loses
     */
    @Test
    public void testShowReactionTime_Loss() {
        miniGame.showReactionTime(500, () -> {});
        assertFalse(miniGame.isGameWon(), "The game should be lost with a reaction time greater than or equal to 450ms");
    }
}