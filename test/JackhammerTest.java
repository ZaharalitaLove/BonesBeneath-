import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the Jackhammer class
 */
public class JackhammerTest {
    private Jackhammer jackhammer;
    private GamePlayer player;

    /**
     * Creating a new jackhammer to test
     */
    @Before
    public void setUp() {
        // Initialize the Jackhammer
        jackhammer = new Jackhammer();
    }

    /**
     * Testing that the type of tool (jackhammer) is properly being returned
     */
    @Test
    public void testGetType() {
        assertEquals("Jackhammer", jackhammer.getType());
    }

    /**
     * Tests that the ID of the jackhammer is properly being used
     */
    @Test
    public void testGetID() {
        assertEquals(4, jackhammer.getID());
    }

    /**
     * Tests that the image file is properly being used
     */
    @Test
    public void testGetImagePath() {
        assertEquals("img/jacklhammer.png", jackhammer.getImagePath());
    }
}