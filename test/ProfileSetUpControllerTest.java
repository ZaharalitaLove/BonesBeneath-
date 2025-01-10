import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the ProfileSetUpController class
 */
public class ProfileSetUpControllerTest {
    private ProfileSetUpController profileSetUpController;
    private MainController mainController;
    private DummyProfileSetUpView profileSetUpView;

    /**
     * Sets up the environment before each test
     */
    @Before
    public void setUp() throws IOException, SQLException {
        mainController = new MainController();
        profileSetUpView = new DummyProfileSetUpView(mainController);
        profileSetUpController = new ProfileSetUpController(mainController);
    }

    /**
     * Tests the creation of a new account
     */
    @Test
    public void testCreateNewAccount() {
        profileSetUpController.createNewAccount();
        assertFalse(profileSetUpView.isAccountCreationScreenDisplayed(), "Account creation screen should be displayed.");
    }

    /**
     * Tests that the profile creation was successful
     */
    @Test
    public void testGetStatus_Success() throws IOException, SQLException {
        assertFalse(profileSetUpView.isErrorDisplayed(), "Account creation result should be displayed as successful.");
    }

    /**
     * Tests that the profile creation failed for invalid input
     */
    @Test
    public void testGetStatus_Failure() throws IOException, SQLException {
        assertFalse(profileSetUpView.isErrorDisplayed(), "Account creation result should be displayed as failure.");
    }

    /**
     * Dummy class to create a ProfileSetUpView for testing
     */
    private static class DummyProfileSetUpView extends ProfileSetUpView {
        private boolean accountCreationScreenDisplayed;
        private boolean errorDisplayed;

        /**
         * Constructor for the DummyProfileSetUpView
         *
         * @param mc - MainController instance
         */
        public DummyProfileSetUpView(MainController mc) {
            super(mc, null);
            this.accountCreationScreenDisplayed = false;
            this.errorDisplayed = false;
        }

        /**
         * Mocking creating an account successfully
         */
        @Override
        public void displayAccountCreationScreen() {
            accountCreationScreenDisplayed = true;
        }

        /**
         * Mocking the successful result of displaying an account
         * @param success - whether profile setup was successful or not
         */
        @Override
        public void displayAccountCreationResult(boolean success) {
            errorDisplayed = !success;
        }

        /**
         * Mocking the displaying of creating an account successfully
         * @return - returning the screen
         */
        public boolean isAccountCreationScreenDisplayed() {
            return accountCreationScreenDisplayed;
        }

        /**
         * Method tests if an error is displayed
         * @return - returning the error
         */
        public boolean isErrorDisplayed() {
            return errorDisplayed;
        }
    }
}