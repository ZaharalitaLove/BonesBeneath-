import org.junit.jupiter.api.AfterAll;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

/**
 * JUnit test for the MainController class
 */
public class MainControllerTest {
    private static MainController mainController;
    private ProfileSetUpController profileSetUpController;


    /**
     * Setting up the main controller to test
     * @throws SQLException - thrown if not connected to SQL properly
     * @throws IOException - thrown if main controller not properly created
     */
    @Before
    public void setUp() throws SQLException, IOException {
        mainController = new MainController();
        profileSetUpController = new ProfileSetUpController(mainController);
    }

    /**
     * Method cleans up the database after all the tests have run
     * @throws SQLException - exception thrown if not connected to SQL proxy
     */
    @AfterAll
    public static void cleanupDatabase() throws SQLException {
        Connection conn = mainController.getConn();
        String sql = "DELETE FROM user_profiles WHERE username IN (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "newACC");
            stmt.setString(2, "acc2");
            stmt.setString(3, "lovez");
            stmt.setString(4, "myaccount");
            stmt.setString(5, "ACC123");
            stmt.executeUpdate();
        }
    }

    /**
     * Testing the initiation of the game
     */

    @Test
    public void initiateGame() throws SQLException, IOException {
        MainController mainController = new MainController();
        GameController gameController = mainController.getGameController();
        GameView gameView = gameController.getGameView();
        mainController.initiateGame();
        Assertions.assertNotNull(gameController);
        Assertions.assertNotNull(gameView);
        Assertions.assertTrue(gameView.isVisible());

    }

    /**
     * Testing login functionality in MainController
     */
    @Test
    public void requestLogin() {
        JFrame frame = mainController.getGameFrame();
        mainController.requestLogin();
        Assertions.assertNotNull(frame, "Game frame should not be null after login request.");
        LoginController loginController = mainController.getLoginController();
        LoginView loginView = loginController.getLoginView();

        Assertions.assertNotNull(loginView, "LoginView should not be null after login request.");
        JPanel loginPanel = loginView.getLoginPanel();
        Assertions.assertNotNull(loginPanel);

    }

    /**
     * Testing profile setup functionality in MainController
     * @throws IOException - thrown if profile is not correctly set up
     */
    @Test
    public void requestProfileSetup() throws IOException {
        JFrame frame = mainController.getGameFrame();
        mainController.requestProfileSetup();
        Assertions.assertNotNull(frame, "Game frame should not be null after profile setup request.");
        ProfileSetUpController profileSetUpController = mainController.getProfileController();
        Assertions.assertNotNull(profileSetUpController, "ProfileSetUpController should not be null.");
    }

    /**
     * Testing addition of UserProfileModel to MainController's user models
     */
    @Test
    public void addToUserModels() {
        UserProfileModel upm = mainController.getUserProfile();
        ProfileSetUpController profileSetUpController = mainController.getProfileController();
        Assertions.assertNotNull(upm, "UserProfileModel should be initialized.");
        ProfileSetUpView profileSetUpView = profileSetUpController.getProfileSetUpView();
        JPanel profileSetupPanel = profileSetUpView.getProfileSetupPanel();


        Assertions.assertNull(profileSetupPanel);
    }

    /**
     * Testing the display of the main view
     */
    @Test
    public void displayMainView() {
        JFrame frame = mainController.getGameFrame();
        mainController.displayMainView();
        Assertions.assertNotNull(frame, "Game frame should not be null when displaying the main view.");
        MainView mainView = mainController.getMainView();
        Assertions.assertNotNull(mainView.getBackgroundPanel(), "MainView's background panel should not be null.");
    }
}