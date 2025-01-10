import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the Pickaxe class
 */
public class PickaxeTest {
    private Pickaxe pickaxe;
    private GamePlayer player;

    /**
     * Creating a new pickaxe to test
     */
    @Before
    public void setUp() {
        // Initialize the Pickaxe
        pickaxe = new Pickaxe();
    }

    /**
     * Testing that the type of tool (pickaxe) is properly being returned
     */
    @Test
    public void testGetType() {
        assertEquals("Pickaxe", pickaxe.getType());
    }

    /**
     * Tests that the ID of the pickaxe is properly being used
     */
    @Test
    public void testGetID() {
        assertEquals(2, pickaxe.getID());
    }

    /**
     * Tests that the first direction of the item is being used correctly
     */
    @Test
    public void testUseItem_Direction1() {
        pickaxe.useItem(player, 1);
    }

    /**
     * Tests that the second direction of the item is being used correctly
     */
    @Test
    public void testUseItem_Direction2() {
        pickaxe.useItem(player, 2);
    }

    /**
     * Tests that the third direction of the item is being used correctly
     */
    @Test
    public void testUseItem_Direction3() {
        pickaxe.useItem(player, 3);
    }

    /**
     * Tests that the fourth direction of using the item is being used correctly
     */
    @Test
    public void testUseItem_Direction4() {
        pickaxe.useItem(player, 4);
    }

    /**
     * Tests that the item was used in the correct time
     */
    @Test
    public void testUseItem_TooSoon() throws InterruptedException {
        pickaxe.useItem(player, 1);
        long lastUsed = System.nanoTime();
        pickaxe.useItem(player, 1);
    }

    /**
     * Tests that the image file is properly being used
     */
    @Test
    public void testGetImagePath() {
        assertEquals("img/pickaxe.png", pickaxe.getImagePath());
    }
}