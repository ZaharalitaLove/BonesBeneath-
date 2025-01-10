import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the TileManager class
 */
public class TileManagerTest {
    TileManager tileManager;

    /**
     * Creating a tile manager to test
     * @throws SQLException - exception thrown if not in SQL proxy
     * @throws IOException - exception if does not properly load
     */
    @Before
    public void setupTileManager() throws IOException, SQLException {
        MainController mainController = new MainController();
        GameController gc = new GameController(mainController);
        tileManager = gc.getTileManager();
    }

    /**
     * Tests colliding into first tile
     */
    @Test
    public void testTile0Collsion(){
        Assert.assertEquals(tileManager.tilenumCollides(0), false);
    }

    /**
     * Tests colliding into second tile
     */
    @Test
    public void testTile1Collsion(){
        Assert.assertEquals(tileManager.tilenumCollides(1), true);
    }


}
