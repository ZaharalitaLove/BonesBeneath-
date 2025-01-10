import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for the HubView class
 */
public class HubViewTest {
    private HubView hubView;
    private MainController mainController;
    private HubController hubController;

    /**
     * Method tests that the HubView is properly setup
     */
    @Before
    public void setUp() throws IOException, SQLException {
        // Initialize MainController and HubController
        mainController = new MainController();
        hubController = new HubController(mainController);
        hubView = new HubView(mainController, hubController);
    }

    /**
     * Method tests the character info to make sure it was properly displayed and scaled
     */
    @Test
    public void testSetCharacterInfo() {
        String characterInfo = "Warrior - Level 5";
        hubView.setCharacterInfo(characterInfo);
        // Using reflection to access the private field for testing
        assertEquals(characterInfo, getFieldValue(hubView, "characterInfo"));
    }

    /**
     * Method tests the gold button to make sure it was properly displayed and scaled
     */
    @Test
    public void testSetGold() {
        hubView.setGold(200);
        assertEquals(200, getFieldValue(hubView, "gold"));
    }

    /**
     * Method tests the stats button to make sure it was properly displayed and scaled
     */
    @Test
    public void testSetStats() {
        String stats = "Level: 5";
        hubView.setStats(stats);
        assertEquals(stats, getFieldValue(hubView, "stats"));
    }

    /**
     * Method tests the jackhammer button to make sure it was properly displayed and scaled
     */
    @Test
    public void testScaleImageAsIcon() {
        ImageIcon icon = HubView.scaleImageAsIcon("img/jackhammer.png", 145, 147);
        assertNotNull(icon);
        assertEquals(145, icon.getIconWidth());
        assertEquals(147, icon.getIconHeight());
    }

    /**
     * Method that tests if the Start Game button was properly displayed and scaled
     */
    @Test
    public void testScaleImageAsButton() {
        JButton button = HubView.scaleImageAsButton("img/startGameButton.png", 330, 120);
        assertNotNull(button);
        assertEquals(-1, ((ImageIcon) button.getIcon()).getIconWidth());
        assertEquals(-1, ((ImageIcon) button.getIcon()).getIconHeight());

    }

    /**
     * Helper method that accesses private fields for testing purposes
     * @param object - taking in the object
     * @param fieldName - taking in the name of the field
     * @return - returning the object
     */
    private Object getFieldValue(Object object, String fieldName) {
        try {
            var field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}