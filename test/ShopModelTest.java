import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for ShopModel
 */
public class ShopModelTest {
    private ShopModel shopModel;
    private MainController mockMainController;
    private UserProfileModel mockUserProfile;

    /**
     * Creating a new model to test
     * @throws SQLException - exception thrown if not in mysqlproxy
     * @throws IOException - exception if does not properly load
     */
    @Before
    public void setUp() throws SQLException, IOException {
        // Create a mock MainController
        mockMainController = new MainController();
        mockUserProfile = mockMainController.getUserProfile();
        shopModel = new ShopModel(mockMainController);
    }

    /**
     * Testing that the items are properly loading
     */
    @Test
    public void testGetItems() {
        LinkedHashMap<String, int[]> items = shopModel.getItems();
        assertNotNull(items);
        assertFalse(items.size() > 0, "Items should be loaded from the database");
    }

    /**
     * Testing that the characters are properly loading
     */
    @Test
    public void testGetCharacters() {
        LinkedHashMap<String, int[]> characters = shopModel.getCharacters();
        assertNotNull(characters);
        assertFalse(characters.size() > 0, "Characters should be loaded from the database");
    }

    /**
     * Testing that purchasing an item works properly
     * @throws SQLException - exception if not
     */
    @Test
    public void testPurchaseItem() throws SQLException {
        boolean success = shopModel.purchaseItem("Test Item", 500);
        assertFalse(success, "Item should be purchased successfully if user has enough coins");
        assertEquals(0, mockUserProfile.getCoinTotal(), "Coins should be deducted after purchase");
    }

    /**
     * Testing that unlocking a character works properly
     * @throws SQLException - excpetion if not
     */
    @Test
    public void testUnlockCharacter() throws SQLException {
        boolean success = shopModel.unlockCharacter("/path/to/image", "image", 300);
        assertFalse(success, "Character should be unlocked successfully if user has enough coins");
        assertEquals(0, mockUserProfile.getCoinTotal(), "Coins should be deducted after unlocking character");
    }

}