import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnemyFactoryTest {



    static MainController mainController;
    UserProfileModel upm;

    /**
     * Clears the database after all tests are run to ensure consistency.
     *
     * @throws SQLException - thrown if an SQL error occurs
     */
    @AfterClass
    public static void afterClass() throws SQLException {
        String removing = "DELETE FROM user_profiles WHERE username = ?";
        PreparedStatement stmt;
        Connection conn = mainController.getConn();
        stmt = conn.prepareStatement(removing);
        stmt.setString(1, "ACC123");
        stmt.executeUpdate();
    }

    /**
     * Sets up the UserProfile and MainController before each test.
     *
     * @throws IOException - thrown if an I/O error occurs
     * @throws SQLException - thrown if an SQL error occurs
     */
    @Before
    public void UserProfileSetupAndLogin() throws IOException, SQLException {
        mainController = new MainController();
        upm = new UserProfileModel(mainController);
    }

    /**
     * Tests that the enemy spawner instantiates the correct enemy.
     */
    @Test
    public void spawnEnemy() throws SQLException, IOException {
        GameController gameController = mainController.getGameController();
        Entity enemy = gameController.getEnemy();
        upm.setupProfile("ACC123", "something");
        mainController.initiateGame();

        assertTrue(enemy.isEnemyAlive());
    }
}