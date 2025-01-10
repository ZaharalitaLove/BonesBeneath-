import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the GameUI class
 */
public class GameUITest {
    private GameUI ui;

    /**
     * Creating a player to test the gameUI
     * @throws IOException - thrown if player setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Before
    public void setupPlayer() throws IOException, SQLException {
        MainController mainController = new MainController();
        GameController gc = new GameController(mainController);
        ui = gc.getUI();
    }

    /**
     * Tests getting the first item
     */
    @Test
    public void getItemTest1() {
        Assert.assertEquals(ui.getCurrentItem(), null);
    }

}
