import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JUnit test for the ProfileSetUpView class
 */
public class ProfileSetUpViewTest {
    private ProfileSetUpView profileSetUpView;
    private static MainController mainController;
    private UserProfileModel userProfileModel;
    private ProfileSetUpController profileSetUpController;
    private JFrame gameFrame;

    /** To ensure that each time the test class is run it will perform the same, the
     * text file acting as the database is cleared after all tests are run.
     * (Note: this is for testing purposes only and is not the built-in functionality of UserProfileModel)
     *
     * @throws IOException - thrown if file error occurs
     */
    @AfterAll
    public static void afterClass() throws IOException, SQLException {

        String removing = "Delete from user_profiles where username = ? ";
        PreparedStatement stmt;
        Connection conn = mainController.getConn();
        stmt = conn.prepareStatement(removing);
        stmt.setString(1,"validUsername");

        stmt.executeUpdate();

    }

    /**
     * Method tests if the GUI was set up correctly
     */
    @Before
    public void setUp() throws IOException, SQLException {
        // Initialize the controllers and the main frame
        mainController = new MainController();
        userProfileModel = mainController.getUserProfile();
        profileSetUpController = new ProfileSetUpController(mainController);
        gameFrame = new JFrame();
        // Instantiate the ProfileSetUpView
        profileSetUpView = new ProfileSetUpView(mainController, profileSetUpController);
    }

    /**
     * Method tests if the display account screen success
     */
    @Test
    public void testDisplayAccountCreationScreen() {
        // Execute the display method
        profileSetUpView.displayAccountCreationScreen();
        // Verify that the profile setup panel is created and added to the frame
        JPanel profileSetupPanel = profileSetUpView.getProfileSetupPanel();
        assertNotNull(profileSetupPanel);
        assertEquals(profileSetupPanel, mainController.getGameFrame().getContentPane().getComponent(0));
    }

    /**
     * Method tests if the create profile button has the correct input
     */
    @Test
    public void testCreateProfileButtonNoInput() {
//        // Execute the display method to set up the GUI
//        profileSetUpView.displayAccountCreationScreen();
//        // Find the button and the text fields
//        JTextField usernameField = profileSetUpView.getUsernameField();
//        JPasswordField passwordField = profileSetUpView.getPasswordField();
//        JButton createProfileButton = profileSetUpView.getCreateProfileButton();
//        // Set empty input for username and password
//        usernameField.setText("Enter Username");
//        passwordField.setText("Enter Password");
//        // Simulate clicking the create profile button
//        createProfileButton.doClick();
////        // Check that user profile was updated
//        Assertions.assertFalse(userProfileModel.getUserName().equals("Enter Username"));
    }

    /**
     * Method that tests if the displayed account creation is successful
     */
    @Test
    public void testDisplayAccountCreationResultSuccess() throws IOException, SQLException {
        // Execute the display method
        profileSetUpView.displayAccountCreationScreen();
        // Simulate successful profile creation
        profileSetUpView.displayAccountCreationResult(true);
        // Verify that the profile setup panel is hidden and game initiation is triggered
        assertFalse(profileSetUpView.getProfileSetupPanel().isVisible());
    }

    /**
     * Helper method to check if a frame with a given title is visible and works properly
     * @param title - taking in the title
     * @return - return boolean
     */
    private boolean isFrameVisible(String title) {
        Frame[] frames = JFrame.getFrames();
        for (Frame frame : frames) {
            if (frame.getTitle().equals(title) && frame.isVisible()) {
                return true;
            }
        }
        return false;
    }
}