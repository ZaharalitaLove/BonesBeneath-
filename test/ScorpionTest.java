import org.junit.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.Assert.*;  // JUnit 4 assertions
import org.junit.Test;

/**
 * JUnit test for the scorpion class
 */
public class ScorpionTest {
    MainController mainController;
    UserProfileModel upm;

    /**
     * Clears the database after all tests are run to ensure consistency.
     * @throws SQLException - thrown if a database error occurs
     */
    @AfterClass
    public static void afterClass() throws SQLException {
        MainController mainController = null;
        try {
            mainController = new MainController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Connection conn = mainController.getConn();
        String removing = "DELETE FROM user_profiles WHERE username IN (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(removing)) {
            stmt.setString(1, "newACC");
            stmt.setString(2, "acc2");
            stmt.setString(3, "lovez");
            stmt.setString(4, "myaccount");
            stmt.setString(5, "ACC123");
            stmt.executeUpdate();
        }
    }

    /**
     * Set up the user profile and main controller before each test.
     * @throws SQLException - thrown if a database error occurs
     */
    @Before
    public void UserProfileSetupAndLogin() throws SQLException {
        try {
            mainController = new MainController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        upm = mainController.getUserProfile();
    }

    /**
     * Tears down the test by simulating an ESC key press after each test.
     * @throws AWTException - thrown if an AWT error occurs
     */
    @After
    public void TearDown() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    /**
     * Tests that the scorpion properly chases the player.
     * @throws IOException - thrown if not correctly created
     * @throws SQLException - thrown if not correctly connected to SQL
     */
    @Test
    public void scorpionDirection() throws IOException, SQLException {
        mainController.displayMainView();
        mainController.getMainView().getPlayButton().doClick();
        mainController.getMainView().getLoginButton().doClick();
        LoginController loginController = mainController.getLoginController();
        LoginView loginView = loginController.getLoginView();
        mainController.showHub();
        mainController.initiateGame();
        Entity enemy = mainController.getGameController().getEnemy();
        Entity player = mainController.getGameController().getPlayer();
        // Checking that scorpion chases player correctly
        player.setDirectionX(1);
        assertEquals(0, enemy.getDirectionX());
        player.setDirectionX(0);
        assertEquals(0, enemy.getDirectionX());
    }

    /**
     * Placeholder test for scorpion's response to an attack.
     */
    @Test
    public void scorpionRespondToAttack() {
        // Implement the test logic for scorpion's response to an attack
        //fail("Not yet implemented");
    }
}