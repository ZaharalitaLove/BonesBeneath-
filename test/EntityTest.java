import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the Entity class
 */
public class EntityTest {
    private GamePlayer player;

    /**
     * Creating a player to test the Entity class on
     * @throws IOException - exception if player setup fails
     * @throws SQLException - exception if SQL is not running
     */
    @Before
    public void setupPlayer() throws IOException, SQLException {
        KeyBoardHandler keyH = new KeyBoardHandler();
        MainController mainController = new MainController();
        GameController gc = new GameController(mainController);
        player = gc.getPlayer();
    }

    /**
     * Tests that the WorldX is properly engaging
     */
    @Test
    public void testWorldX(){
        player.setWorldX(10);
        Assert.assertEquals(player.getWorldX(), 10);
    }

    /**
     * Tests that the WorldY is properly engaging
     */
    @Test
    public void testWorldY(){
        player.setWorldY(10);
        Assert.assertEquals(player.getWorldY(), 10);
    }

    /**
     * Tests the X velocity to ensure it is properly working
     */
    @Test
    public void testXVel(){
        player.setXVelocity(10);
        Assert.assertEquals(player.getXVelocity(), 10);
    }

    /**
     * Tests the Y velocity to ensure it is properly working
     */
    @Test
    public void testYVel(){
        player.setYVelocity(10);
        Assert.assertEquals(player.getYVelocity(), 10);
    }

    /**
     * Tests the grounding
     */
    @Test
    public void testGrounded(){
        player.setGrounded(true);
        Assert.assertEquals(player.getGrounded(), true);
    }

    /**
     * Tests two objects colliding together
     */
    @Test
    public void testColliding(){
        player.setCollision(true);
        Assert.assertEquals(player.getCollision(), true);
    }
}
