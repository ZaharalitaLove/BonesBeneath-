import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for the Drill class
 */
public class DrillTest {
    private Drill drill;

    /**
     * Creating a new drill to test
     */
    @Before
    public void setUp() {
        // Initialize the Drill
        drill = new Drill();
    }

    /**
     * Testing that the type of tool (drill) is properly being returned
     */
    @Test
    public void testGetType() {
        assertEquals("Drill", drill.getType());
    }

    /**
     * Tests that the ID of the drill is properly being used
     */
    @Test
    public void testGetID() {
        assertEquals(3, drill.getID());
    }

    /**
     * Tests that the image file is properly being used
     */
    @Test
    public void testGetImagePath() {
        assertEquals("img/drill.png", drill.getImagePath());
    }
}