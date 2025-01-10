import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/** Test for login controller
 */
public class LoginControllerTest {
    static MainController mainController ;
    UserProfileModel upm;


    /** After all tests run the database will be cleared so that testing can be repeated without error
     * @throws SQLException - thrown if sql error occurs
     */
    @AfterAll
    public static void afterClass() throws SQLException {
        String removing = "Delete from user_profiles where username = ? or username = ? or username = ? or username = ?";
        PreparedStatement stmt;
        Connection conn = mainController.getConn();
        stmt = conn.prepareStatement(removing);
        stmt.setString(1, "ACC1234");
        stmt.setString(2, "ACC");
        stmt.setString(3, "A00");
        stmt.setString(4, "ACC000");

        stmt.executeUpdate();
    }
    /** Sets up before tests run
     *
     * @throws IOException- thrown if file error occurs
     */
    @Before
    public void UserProfileSetupAndLogin() throws IOException, SQLException {
        mainController = new MainController();
        upm = new UserProfileModel(mainController);
    }


    /** Tests the login to account process. Checks that proper panels are displayed and that button actions
     * function correctly.
     * @throws IOException - thrown if file error occurs
     */
    @Test
    public void loginToAccount() throws IOException, SQLException {
        //Setting up test
        MainController mainController = new MainController();
        mainController.displayMainView();
        MainView mainView = mainController.getMainView();
        mainView.getPlayButton().doClick();
        mainView.getLoginButton().doClick();
        LoginController loginController = mainController.getLoginController();
        LoginView loginView = loginController.getLoginView();

        //testing that panels are displayed initially
        Assertions.assertNotNull(loginView.getLoginPanel());
        Assertions.assertTrue(loginView.getLoginPanel().isVisible());

    }

     /** Tests an unsuccessful login to account process. Checks that proper panels are displayed and that button actions
      * function correctly.
      *
     * @throws IOException - thrown if file error occurs
     */
    @Test
    public void onLoginFailure() throws IOException, SQLException {
        MainController mainController = new MainController();
        MainView mainView = mainController.getMainView();
        mainController.displayMainView();
        mainView.getPlayButton().doClick();
        mainView.getLoginButton().doClick();
        LoginController loginController = mainController.getLoginController();
        LoginView loginView = loginController.getLoginView();
        UserProfileModel upm = new UserProfileModel(mainController);

        Assertions.assertFalse(upm.setupProfile("ACC","newpassword3"));
       //changing account
        Assertions.assertFalse(upm.setupProfile("A00","newpassword3"));
        Assertions.assertTrue(upm.authenticate("A00","newpassword3"));
        // unsuccessful login, password incorrect
        loginView.getPasswordField().setText("newpassword3"); //wrong password
        loginView.getUsernameField().setText("ACC");
        loginView.getLoginButton().doClick();
        //checking that user profile model was not changed to "newacc3" because login was unsuccessful
        Assertions.assertNotEquals(upm.getUserName(), "ACC");

    }
}