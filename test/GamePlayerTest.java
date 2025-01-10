import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the GamePlayer class
 */
public class GamePlayerTest {
    private int maxVelocity = 20;
    private int runVelocity =8;
    private GamePlayer player;

    /**
     * Creating a player to test
     * @throws IOException - thrown if game controller setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Before
    public void setupPlayer() throws IOException, SQLException {
        MainController mainController = new MainController();
        GameController gc = new GameController(mainController);
        player = gc.getPlayer();
    }

    /**
     * Tests that the player is able to properly jump
     */
    @Test
    public void testJump(){
        Assert.assertEquals(player.getYVelocity(), 0);
        player.Jump();
        Assert.assertEquals(player.getYVelocity(), -player.getJumpVelocity());
    }

    /**
     * Tests that the player is able to properly move to the left
     */
    @Test
    public void testMoveLeft(){
        Assert.assertEquals(player.getXVelocity(), 0);
        player.moveLeft();
        Assert.assertEquals(player.getXVelocity(), -1);
    }

    /**
     * Tests that the player is able to properly move to the right
     */
    @Test
    public void testMoveRight(){
        Assert.assertEquals(player.getXVelocity(), 0);
        player.moveRight();
        Assert.assertEquals(player.getXVelocity(), 1);
    }

    /**
     * Tests that the maximum velocity of the player is correct
     */
    @Test
    public void testMaxVel(){
        player.setMaxVelocity(30);
        Assert.assertEquals(player.getMaxVelocity(), 30);
    }

    /**
     * Tests that the Y axis on the screen is correct
     */
    @Test
    public void testScreenY(){
        player.setScreenY(100);
        Assert.assertEquals(player.getScreenY(), 100);
    }

    /**
     * Tests that the X axis on the screen is correct
     */
    @Test
    public void testScreenX(){
        player.setScreenX(100);
        Assert.assertEquals(player.getScreenX(), 100);
    }

    /**
     * Tests that the center is correct for the player - x-axis
     */
    @Test
    public void testCenterX(){
        player.setCenterX(100);
        Assert.assertEquals(player.getCenterX(), 100);
    }

    /**
     * Tests that the center is correct for the player - y-axis
     */
    @Test
    public void testCenterY(){
        player.setCenterX(100);
        Assert.assertEquals(player.getCenterX(), 100);
    }

    /**
     * Tests that the center of the world X coordinate is correct
     */
    @Test
    public void testCenterWorldX(){
        player.setCenterWorldX(100);
        Assert.assertEquals(player.getCenterWorldX(), 100);
    }

    /**
     * Tests that the center of the world Y coordinate is correct
     */
    @Test
    public void testCenterWorldY(){
        player.setCenterWorldY(100);
        Assert.assertEquals(player.getCenterWorldY(), 100);
    }

    /**
     * Tests that the camera distance is correct
     */
    @Test
    public void testCameraTrackDist(){
        player.setCamTrackDist(20);
        Assert.assertEquals(player.getCamTrackDist(), 20);
    }

    /**
     * Tests that the camera boundaries for the X axis are correctly created
     */
    @Test
    public void testCameraBoundX(){
        player.setCameraboundX(200);
        Assert.assertEquals(player.getCameraboundX(), 200);
    }

    /**
     * Tests that the camera boundaries for the Y axis are correctly created
     */
    @Test
    public void testCameraBoundY(){
        player.setCameraboundY(200);
        Assert.assertEquals(player.getCameraboundY(), 200);
    }
}
