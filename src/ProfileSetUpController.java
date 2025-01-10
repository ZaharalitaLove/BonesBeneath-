import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/** Controller class for profile setup process, controls profile setup view and communicates information to
 * user profile model.
 */
public class ProfileSetUpController {

    private MainController mainController;
    private ProfileSetUpView profileSetUpView;

    /** Constructor takes in the main controller instance for the program, sets its main controller attribute, and
     * instantiates the profile setup view.
     *
     * @param mc - main controller instance
     */
    public ProfileSetUpController(MainController mc) {
        mainController = mc;
        profileSetUpView = new ProfileSetUpView(mainController,this);
    }

    /** Displays profile setup screen using profile setup view
     */
    public void createNewAccount() {
        profileSetUpView.displayAccountCreationScreen();
    }

    /** Displays profile setup results for successful profile setup
     */
    public void onProfileCreationSuccess() throws IOException, SQLException {
        //TODO: Handle profile creation if successful
        profileSetUpView.displayAccountCreationResult(true);
    }

    /** Displays profile setup results for unsuccessful profile setup
     */
    public void onProfileCreationFailure() throws IOException, SQLException {
        //TODO: Handle profile creation if failed
        profileSetUpView.displayAccountCreationResult(false);
    }

//    /** Checks authentication status or login input
//     *
//     * @param userName -  username from user entry
//     * @param password -  password from user entry
//     * @return - status of authentication according to database reference
//     */
//    public boolean getStatus(String userName , String password) throws SQLException {
//        UserProfileModel userProfileModel = mainController.getUserProfile();
//        boolean status = userProfileModel.setupProfile(userName, password);
//        userProfileModel.closeDatabse();
//        return status;
//    }
    /** Method collects input from username and password text fields, checks authentication status,
     * and communicates information to profile setup view to display results.
     *
     * @param usernameField - textField with saved username information from user entry
     * @param passwordField - textField with saved password information from user entry
     * @throws IOException - thrown if file error occurs
     */
    public void collectNewUserInfo(JTextField usernameField, JTextField passwordField) throws IOException, SQLException {
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();
        UserProfileModel userProfileModel = mainController.getUserProfile();
        boolean success = userProfileModel.setupProfile(newUsername, newPassword);
        if (success) {
            onProfileCreationSuccess();
            mainController.showHub();
        }
        else{
            onProfileCreationFailure();
        }
    }
    public ProfileSetUpView getProfileSetUpView() {
        return profileSetUpView;
    }

}
