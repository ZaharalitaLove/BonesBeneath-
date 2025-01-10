import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * JUnit test for the MapModel class
 */
public class MapModelTest {
    MapModel map;

    /**
     * Setting up a map to test
     * @throws IOException - thrown if map setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Before
    public void setupMap() throws IOException, SQLException {
        MainController mainController = new MainController();
        GameController gameController = new GameController(mainController);
        map = new MapModel(gameController);
    }

    /**
     * Testing that the tile layout is correct
     */
    @Test
    public void testTileLayouts(){
         ArrayList<int[]> tiles = new ArrayList<int[]>();
        map.setTileLayouts(tiles);
        Assert.assertEquals(map.getTileLayouts(), tiles);
    }

    /**
     * Testing that the current map is set up correctly
     */
    @Test
    public void testCurrentMap(){
        int[] maptest = new int[]{1,1,1,0,1,1,1,1,0,1};
        map.updateMapData(maptest);
        Assert.assertEquals(map.accessMapData(), maptest);
    }
}
