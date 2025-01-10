import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Unit test for ShopController class.
 */
public class ShopControllerTest {
    private ShopController shopController;
    private MainController mainController;

    /**
     * Setting up the controller to test
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException {
        // Initialize a MainController object
        try {
            mainController = new MainController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Initialize the ShopController object
        shopController = new ShopController(mainController);
    }

    /**
     * Testing that there are items to sell and are properly displayed and functioning
     */
    @Test
    public void testGetItemsToSell() {
        // Get the list of items to sell from the shop
        List<Item> itemsToSell = shopController.getItemsToSell();
        // Check that the list contains exactly 3 items
        assertEquals(3, itemsToSell.size());
        // Check that the items are of the correct type and order
        assertTrue(itemsToSell.get(0) instanceof Shovel);
        assertTrue(itemsToSell.get(1) instanceof Pickaxe);
        assertTrue(itemsToSell.get(2) instanceof Jackhammer);
    }
}