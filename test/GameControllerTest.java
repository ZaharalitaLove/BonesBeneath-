import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the GameController class
 */
public class GameControllerTest {
    GameController gc;

    /**
     * Creating a controller to test
     * @throws IOException - thrown if game controller setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Before
    public void setupController() throws IOException, SQLException {
        MainController mc = new MainController();
        gc = new GameController(mc);
    }

    /**
     * Tests that the tiles are the correct size
     */
    @Test
    public void testTileSize(){
        Assert.assertEquals(gc.getTileSize(), 64);
    }

    /**
     * Tests that the world correctly creates the right amount of maximum columns
     */
    @Test
    public void testMaxWorldCol(){
        Assert.assertEquals(gc.getMaxWorldCol(), 40);
    }

    /**
     * Tests that the world correctly creates the right amount of maximum rows
     */
    @Test
    public void testMaxWorldRow(){
        Assert.assertEquals(gc.getMaxWorldRow(), 40);
    }

}
