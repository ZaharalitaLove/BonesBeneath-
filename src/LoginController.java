import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/** Controller class for login process, controls login view and communicates information to
 * user profile model.
 */
public class LoginController {
    private LoginView loginView;
    private MainController mainController;

    /** Constructor takes in the main controller instance for the program, sets its main controller attribute, and
     * instantiates the login view.
     *
     * @param mc - main controller instance
     */
    public LoginController(MainController mc) {
        this.mainController = mc;
        // Pass a null for now as hubController is not initialized yet
        loginView = new LoginView(mainController, this, null);
    }
    /** Displays login screen using login view
     */
    public void loginToAccount() {
        loginView.displayLoginScreen();
    }
    /** Displays login results for successful login
     */
    public void onLoginSuccess() throws IOException, SQLException {
        loginView.displayLoginResults(true);
    }

    /**Displays login results for unsuccessful login
     */
    public void onLoginFailure() throws IOException, SQLException {
        loginView.displayLoginResults(false);
    }
    /** Method collects input from username and password text fields, checks authentication status,
     * and communicates information to login view to display results.
     *
     * @param usernameField-  username information from user entry
     * @param passwordField - password information from user entry
     * @throws IOException - thrown if file error occurs
     */
    public void collectUserCredentials(JTextField usernameField, JPasswordField passwordField) throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean success = false;
        UserProfileModel userProfileModel = mainController.getUserProfile();
        success = userProfileModel.authenticate(username, password);

        if (success) {
            onLoginSuccess();
        } else {
            onLoginFailure();
        }
    }

    public LoginView getLoginView() {
        return loginView;
    }
}
