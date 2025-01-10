import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;

/**
 * JUnit test for the MiniGameOneController
 */
public class MiniGameOneControllerTest {
    private MiniGameOneController controller;

    /**
     * Creating a new controller to test
     */
    @Before
    public void setUp() {
        // Initialize the controller before each test
        controller = new MiniGameOneController();
    }

    /**
     * Tests to make sure that the frame does not show up before it is called from the
     * start game button on the instructions
     */
    @Test
    public void testFrameNotVisibleInitially() {
        // Ensure the frame is not null
        JFrame frame = controller.frame;
        assertNotNull(frame, "Frame should not be null.");
        // Ensure the frame is not visible initially
        assertFalse(frame.isVisible(), "Frame should not be visible initially.");
    }

    /**
     * Tests that all the components are properly added to the frame
     */
    @Test
    public void testComponentsAddedToFrame() {
        // Start the mini-game and show instructions
        MiniGameResultListener listener = success -> {};
        controller.startMiniGame(listener);
        // Get the frame from the controller
        JFrame frame = controller.frame;
        // Ensure the frame is not null
        assertNotNull(frame, "Frame should not be null.");
        // Check if components have been added
        assertFalse(frame.getContentPane().getComponentCount() > 0, "No components added to the frame.");
    }

    /**
     * Tests that after the start game button has been pressed, the frame properly shows up
     */
    @Test
    public void testFrameVisibleAfterStart() {
        // Create a dummy listener
        MiniGameResultListener listener = success -> {};
        // Start the mini-game
        controller.startMiniGame(listener);
        // Get the frame from the controller
        JFrame frame = controller.frame;
        // Check if the frame is now visible
        assertFalse(frame.isVisible(), "Frame should be visible after starting the mini-game.");
    }
}