import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * JUnit test for the CollisionChecker
 */
public class CollisionCheckerTest {
    CollisionChecker cc;
    GamePlayer player;

    /**
     * Creating a player to test the collision detector
     * @throws IOException - exception if the player does not properly set up
     * @throws SQLException - exception if the SQL is not signed in
     */
    @Before
    public void setupPlayer() throws IOException, SQLException {
        MainController mainController = new MainController();
        GameController gc = new GameController(mainController);
        cc = gc.getCollisionChecker();
        player = gc.getPlayer();

    }

    /**
     * Testing the collision detector
     * @throws IOException - exception if it does not work
     */
    @Test
    public void testCollsionCheckinAir() throws IOException {
        // Set player's position to be above the ground
        player.worldX = 100;
        player.worldY = 100;
        player.yVelocity = 0;
        player.grounded = false;
        cc.checkTile(player);
        assertEquals(0, player.yVelocity);
        assertFalse(player.colliding);
    }
}
