import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the LoginView class
 */
public class LoginViewTest {
    private MainController mainController;
    private LoginController loginController;
    private LoginView loginView;

    /**
     * Method tests the setup of the panel and the controllers
     */
    @Before
    public void setUp() throws IOException, SQLException {
        mainController = new MainController();
        loginController = new LoginController(mainController);
        loginView = new LoginView(mainController, loginController, null);
    }

    /**
     * Method tests the login screen display and the functions are correct
     */
    @Test
    public void testDisplayLoginScreen() {
        loginView.displayLoginScreen();
        JPanel loginPanel = loginView.getLoginPanel();
        assertNotNull(loginPanel, "Login panel should be initialized");
        assertEquals(4, loginPanel.getComponentCount(), "Login panel should contain 4 components");
        JTextField usernameField = loginView.getUsernameField();
        JPasswordField passwordField = loginView.getPasswordField();
        JButton loginButton = loginView.getLoginButton();
        assertNotNull(usernameField, "Username field should be initialized");
        assertNotNull(passwordField, "Password field should be initialized");
        assertNotNull(loginButton, "Login button should be initialized");
        assertEquals("", usernameField.getText(), "Username field should be empty initially");
        assertEquals("", String.valueOf(passwordField.getPassword()), "Password field should be empty initially");
    }

    /**
     * Method tests if the login results failed
     * @throws IOException - throwing exceptions to understand issues
     */
    @Test
    public void testDisplayLoginResultsFailure() throws IOException, SQLException {
        boolean loginSuccess = false;
        loginView.displayLoginResults(loginSuccess);
        // Assert that error frame is displayed on failure
        JFrame errorFrame = loginView.getErrorFrame();
        assertNotNull(errorFrame, "Error frame should be initialized on login failure");
        assertEquals("Error", errorFrame.getTitle(), "Error frame should have the correct title");
        assertTrue(errorFrame.isVisible(), "Error frame should be visible after login failure");
    }

    /**
     * Method tests if the action listener for the login button does not work with
     * empty fields given
     */
    @Test
    public void testLoginButtonActionListenerWithEmptyFields() {
        loginView.displayLoginScreen();
        JButton loginButton = loginView.getLoginButton();
        // Simulate a button click
        loginButton.doClick();
        // Assert that the error message is displayed
        assertFalse(JOptionPane.getRootFrame().isShowing(), "Error dialog should be shown for empty fields");
    }
}