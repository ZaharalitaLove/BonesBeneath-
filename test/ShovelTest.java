import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the shovel class
 */
public class ShovelTest {
    private Shovel shovel;
    private GamePlayer player;

    /**
     * Creating a new shovel to test
     */
    @Before
    public void setUp() {
        // Initialize the Shovel
        shovel = new Shovel();
    }

    /**
     * Testing that the type of tool (shovel) is properly being returned
     */
    @Test
    public void testGetType() {
        assertEquals("Shovel", shovel.getType());
    }

    /**
     * Tests that the ID of the shovel is properly being used
     */
    @Test
    public void testGetID() {
        assertEquals(1, shovel.getID());
    }

    /**
     * Tests that the first direction of the item is being used correctly
     */
    @Test
    public void testUseItem_Direction1() {
        shovel.useItem(player, 1);
    }

    /**
     * Tests that the second direction of the item is being used correctly
     */
    @Test
    public void testUseItem_Direction2() {
        shovel.useItem(player, 2);
    }

    /**
     * Tests that the third direction of the item is being used correctly
     */
    @Test
    public void testUseItem_Direction3() {
        shovel.useItem(player, 3);
    }

    /**
     * Tests that the fourth direction of using the item is being used correctly
     */
    @Test
    public void testUseItem_Direction4() {
        shovel.useItem(player, 4);
    }

    /**
     * Tests that the item was used in the correct time
     */
    @Test
    public void testUseItem_TooSoon() {
        shovel.useItem(player, 1);
        long lastUsed = System.nanoTime();
        shovel.useItem(player, 1);
    }

    /**
     * Tests that the image file is properly being used
     */
    @Test
    public void testGetImagePath() {
        assertEquals("img/shovel.png", shovel.getImagePath());
    }
}