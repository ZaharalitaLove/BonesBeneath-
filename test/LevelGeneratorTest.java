import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the LevelGenerator class
 */
public class LevelGeneratorTest {
    private LevelGenerator levelGenerator;
    private MainController mainController = new MainController();
    private  GameController gameController = new GameController(mainController);

    /**
     * Level Generator creation to test
     * @throws IOException - thrown if level generator setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    public LevelGeneratorTest() throws IOException, SQLException {
    }

    /**
     * Creating a player to test
     * @throws IOException - thrown if player setup fails
     */
    @Before
    public void setupPlayer() throws IOException {
        levelGenerator = new LevelGenerator(gameController);
    }

    /**
     * Tests that the array to makeRoom works
     */
    @Test
    public void testMakeRoomRow(){
        int[] testarray = levelGenerator.makeRoomRow();
        Assert.assertEquals(testarray.length, 400);
    }

    /**
     * Tests that the map is properly being made
     */
    @Test
    public void testMakeMap(){
        int[] testarray = levelGenerator.makeMap();
        Assert.assertEquals(testarray.length, 1600);
    }
}
