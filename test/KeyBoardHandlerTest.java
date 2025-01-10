import org.junit.Before;
import org.junit.Test;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the KeyBoardHandler class
 */
public class KeyBoardHandlerTest {
    private KeyBoardHandler keyboardHandler;
    private JPanel component;

    /**
     * Creating and setting up a new KeyBoard handler method to test
     */
    @Before
    public void setUp() {
        keyboardHandler = new KeyBoardHandler();
        component = new JPanel();
    }

    /**
     * Testing that the key W when pressed is properly registered
     */
    @Test
    public void testKeyPressedW() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isUp(), "W key should set 'up' to true");
    }

    /**
     * Testing that the key W when released is properly registered
     */
    @Test
    public void testKeyReleasedW() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isUp(), "W key should set 'up' to true");

        keyboardHandler.keyReleased(keyEvent);
        assertFalse(keyboardHandler.isUp(), "W key release should set 'up' to false");
    }

    /**
     * Testing that the key A when pressed is properly registered
     */
    @Test
    public void testKeyPressedA() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isLeft(), "A key should set 'left' to true");
    }

    /**
     * Testing that the key A when released is properly registered
     */
    @Test
    public void testKeyReleasedA() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isLeft(), "A key should set 'left' to true");

        keyboardHandler.keyReleased(keyEvent);
        assertFalse(keyboardHandler.isLeft(), "A key release should set 'left' to false");
    }

    /**
     * Testing that the key D when pressed is properly registered
     */
    @Test
    public void testKeyPressedD() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isRight(), "D key should set 'right' to true");
    }

    /**
     * Testing that the key D when released is properly registered
     */
    @Test
    public void testKeyReleasedD() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isRight(), "D key should set 'right' to true");

        keyboardHandler.keyReleased(keyEvent);
        assertFalse(keyboardHandler.isRight(), "D key release should set 'right' to false");
    }

    /**
     * Testing that the key "escape" when pressed is properly registered
     */
    @Test
    public void testKeyPressedEscape() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isEscape(), "Escape key should set 'escape' to true");
    }

    /**
     * Testing that the key "escape" when released is properly registered
     */
    @Test
    public void testKeyReleasedEscape() {
        KeyEvent keyEvent = new KeyEvent(component, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
        keyboardHandler.keyPressed(keyEvent);
        assertTrue(keyboardHandler.isEscape(), "Escape key should set 'escape' to true");

        keyboardHandler.keyReleased(keyEvent);
        assertFalse(keyboardHandler.isEscape(), "Escape key release should set 'escape' to false");
    }

    /**
     * Testing when there is no input that it is parsing correctly
     */
    @Test
    public void testNoInput() {
        keyboardHandler.noInput();
        assertFalse(keyboardHandler.isUp(), "'up' should be false after calling noInput()");
        assertFalse(keyboardHandler.isEscape(), "'escape' should be false after calling noInput()");
        assertFalse(keyboardHandler.isLeft(), "'left' should be false after calling noInput()");
        assertFalse(keyboardHandler.isRight(), "'right' should be false after calling noInput()");
    }
}