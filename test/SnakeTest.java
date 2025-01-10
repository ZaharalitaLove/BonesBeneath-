import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JUnit test for the Snake class
 */
public class SnakeTest {
    MainController mainController;
    UserProfileModel upm;

    /**
     * To ensure that each time the test class is run it will perform the same, the
     * text file acting as the database is cleared after all tests are run.
     * @throws IOException - thrown if file error occurs
     * @throws SQLException - thrown if a database error occurs
     */
    @AfterClass
    public static void afterClass() throws IOException, SQLException {
        // Clear the database for the tests
        MainController mainController = new MainController();
        String removing = "DELETE FROM user_profiles WHERE username = ? OR username = ? OR username = ? OR username = ? OR username = ?";
        try (Connection conn = mainController.getConn();
             PreparedStatement stmt = conn.prepareStatement(removing)) {
            stmt.setString(1, "newACC");
            stmt.setString(2, "acc2");
            stmt.setString(3, "lovez");
            stmt.setString(4, "myaccount");
            stmt.setString(5, "ACC123");
            stmt.executeUpdate();
        }
    }

    /**
     * Sets up before tests run
     * @throws IOException - thrown if file error occurs
     * @throws SQLException - thrown if a database error occurs
     */
    @Before
    public void setUp() throws IOException, SQLException {
        mainController = new MainController();
    }

    /**
     * Testing tear down
     * @throws AWTException - exception
     */
    @After
    public void tearDown() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    /**
     * Testing the direction of the snake
     * @throws IOException - thrown if not properly setup
     * @throws SQLException - thrown if not properly connected to SQL
     */
    @Test
    public void testSnakeDirection() throws IOException, SQLException {
        GameController gameController = mainController.getGameController();
        GameView gameView = gameController.getGameView();
        HubController hubController = mainController.getHubController();
        MainView mainView = mainController.getMainView();

        mainController.displayMainView();
        mainView.getPlayButton().doClick();
        mainView.getLoginButton().doClick();

        LoginController loginController = mainController.getLoginController();
        LoginView loginView = loginController.getLoginView();
        // Add additional assertions to verify the direction of the snake here
    }

    /**
     * Testing the attack from a snake
     * @throws IOException - thrown if not properly parsed
     * @throws SQLException - thrown if not properly connected to SQL
     */
    @Test
    public void testSnakeAttack() throws IOException, SQLException {
        // Implement the attack logic and assertions here
        // Example:
        // Snake snake = new Snake();
        // assertTrue(snake.attack());
    }

    /**
     * Testing the response to an attack
     */
    @Test
    public void testSnakeRespondToAttack() {
    }
}