import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the GameView class
 */
public class GameViewTest {
    GameView gv;

    /**
     * Creating a GameController to test
     * @throws IOException - thrown if game controller setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Before
    public void setupView() throws IOException, SQLException {
        MainController mainController = new MainController();
        GameController gc = new GameController(mainController);
        gv = gc.getGameView();
    }

    /**
     * Tests that the height of the game is correct
     */
    @Test
    public void testGameHeight(){
        gv.setScreenHeight(333);
        Assert.assertEquals(gv.getScreenHeight(), 333);
    }

    /**
     * Tests that the width of the game is correct
     */
    @Test
    public void testGameWidth(){
        gv.setScreenWidth(333);
        Assert.assertEquals(gv.getScreenWidth(), 333);
    }
}
