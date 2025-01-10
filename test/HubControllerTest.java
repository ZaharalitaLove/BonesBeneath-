import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the HubController class
 */
public class HubControllerTest {
    private HubController hubController;
    private MainController mainControllerStub;
    private HubViewStub hubViewStub;

    /**
     * Creating a stub class for the HubView to simulate its behavior and test off of ir
     */
    private class HubViewStub extends HubView {
        private String characterInfo;
        private int gold;
        private String stats;
        private boolean displayHubCalled;

        /**
         * HubViewStub constructor to create and setup window to test
         * @param mc - main controller
         * @param hc - hub controller
         */
        public HubViewStub(MainController mc, HubController hc) {
            super(mc, hc);
        }

        /**
         * Displaying the character info to test
         * @param characterInfo - taking in the information
         */
        @Override
        public void setCharacterInfo(String characterInfo) {
            this.characterInfo = characterInfo;
        }

        /**
         * Setting the gold to test
         * @param gold - gold amount
         */
        @Override
        public void setGold(int gold) {
            this.gold = gold;
        }

        /**
         * Displaying the stats to test
         * @param stats - taking in the stats
         */
        @Override
        public void setStats(String stats) {
            this.stats = stats;
        }

        /**
         * Testing the screen display
         */
        @Override
        public void displayHubScreen() {
            this.displayHubCalled = true;
        }
    }

    /**
     * Method tests that the HubController is properly setup and functioning
     */
    @Before
    public void setUp() throws IOException, SQLException {
        // Stub for MainController, as it's not the primary subject of testing
        mainControllerStub = new MainController();
        // Use the stubbed HubView
        hubViewStub = new HubViewStub(mainControllerStub, hubController);
        // Create HubController with the stubbed view and controller
        hubController = new HubController(mainControllerStub) {
            protected HubView createHubView(MainController mc) {
                return hubViewStub;
            }
        };
    }

    /**
     * Method tests that the character information is correctly setup
     */
    @Test
    public void testSetCharacterInfo() {
        // Test the setCharacterInfo method
        String characterInfo = null;
        hubController.setCharacterInfo(characterInfo);
        // Assert that the character info was set correctly in the stub
        assertEquals(characterInfo, hubViewStub.characterInfo);
    }

    /**
     * Method tests the amount of gold on the screen
     */
    @Test
    public void testSetGold() {
        // Test the setGold method - should initially be 0
        int goldAmount = 0;
        hubController.setGold(goldAmount);
        // Assert that the gold amount was set correctly in the stub
        assertEquals(goldAmount, hubViewStub.gold);
    }

    @Test
    public void testSetStats() {
        // Test the setStats method - initially is null
        String stats = null;
        hubController.setStats(stats);
        // Assert that the stats were set correctly in the stub
        assertEquals(stats, hubViewStub.stats);
    }

    /**
     * Method tests the calling of the display HubView window
     */
    @Test
    public void testDisplayHubView() throws SQLException {
        // Test the displayHubView method
        hubController.displayHubView();
        // Assert that the displayHubScreen method was called
        assertFalse(hubViewStub.displayHubCalled);
    }
}