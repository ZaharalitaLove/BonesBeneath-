import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the MainView class
 */
public class MainViewTest {

    /**
     * Testing the JFrame for the MainView
     * @throws IOException - thrown if main view setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Test
    public void TestingJFrame() throws IOException, SQLException {
        MainController mainController = new MainController();
        MainView mainView = mainController.getMainView();
        mainView.displayOpeningScreen();

        JFrame frame = mainView.getFrame();
        Assertions.assertNotNull(frame);
        Assertions.assertTrue(frame.isVisible());

        JPanel gamePanel = mainView.getBackgroundPanel();
        Assertions.assertNotNull(gamePanel);
        Assertions.assertTrue(gamePanel.isVisible());

    }

    /**
     * Testing the JPanel
     * @throws IOException - thrown if JPanel setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Test
    public void testingJPanel() throws IOException, SQLException {
        MainController mainController = new MainController();
        MainView mainView = mainController.getMainView();
        mainView.displayOpeningScreen();

        JPanel gamePanel = mainView.getBackgroundPanel();
        Assertions.assertNotNull(gamePanel);
        Assertions.assertTrue(gamePanel.isVisible());

    }

    /**
     * Testing the play button
     * @throws IOException - thrown if play button setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Test
    public void testingPlayButton() throws IOException, SQLException {
        MainController mainController = new MainController();
        MainView mainView = mainController.getMainView();
        mainView.displayOpeningScreen();
        JButton play = mainView.getPlayButton();


        play.doClick();
        JPanel optionPanel = mainView.getOptionPanel();
        Assertions.assertTrue(optionPanel.isVisible());
    }

    /**
     * Testing the login button
     * @throws IOException - thrown if login button setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Test
    public void testingLoginButton() throws IOException, SQLException {
        MainController mainController = new MainController();
        MainView mainView = mainController.getMainView();
        mainView.displayOpeningScreen();
        JButton play = mainView.getPlayButton();

        play.doClick();
        JPanel optionPanel = mainView.getOptionPanel();

        JButton login = mainView.getLoginButton();

        login.doClick();
        Assertions.assertFalse(optionPanel.isVisible());

    }

    /**
     * Testing the signup button
     * @throws IOException - thrown if signup button setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Test
    public void testingSignUpButton() throws IOException, SQLException {
        MainController mainController = new MainController();
        MainView mainView = mainController.getMainView();
        mainView.displayOpeningScreen();
        JButton play = mainView.getPlayButton();
        play.doClick();

        JPanel optionPanel = mainView.getOptionPanel();

        JButton signup = mainView.getSignUpButton();

        signup.doClick();
        Assertions.assertFalse(optionPanel.isVisible());

    }
}