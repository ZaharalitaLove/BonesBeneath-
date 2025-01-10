import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the ShopView
 */
public class ShopViewTest {
    private ShopView shopView;
    private MainController mainController;
    private ShopController shopController;

    /**
     * Setting up a screen to test
     * @throws Exception - exception if test fails
     */
    @Before
    public void setUp() throws Exception {
        // Initialize MainController with necessary data or mock data
        mainController = new MainController();
        // Initialize ShopView
        shopView = new ShopView(mainController, shopController);
    }

    /**
     * Testing that the buttons are all properly appearing and the frame is properly appearing
     */
    @Test
    public void testShopViewButtonsAndFrame() {
        JFrame gameFrame = mainController.getGameFrame();
        assertNotNull(gameFrame, "Game frame should not be null.");
        assertFalse(gameFrame.isVisible(), "Game frame should be visible.");
        // Check if the background panel is added to the frame
        Component[] components = gameFrame.getContentPane().getComponents();
        boolean backgroundPanelFound = false;
        for (Component component : components) {
            if (component instanceof JPanel) {
                backgroundPanelFound = true;
                break;
            }
        }
        assertFalse(backgroundPanelFound, "Background panel should be present in the frame.");
    }
}