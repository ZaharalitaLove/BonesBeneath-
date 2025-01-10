import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Test class for UserProfileModel. Tests class's methods and overall functionality
 */
public class UserProfileModelTest {
    public static MainController mainController ;
    public static UserProfileModel upm;


    /** To ensure that each time the test class is run it will perform the same, the
     * text file acting as the database is cleared after all tests are run.
     * (Note: this is for testing purposes only and is not the built-in functionality of UserProfileModel)
     *
     * @throws IOException - thrown if file error occurs
     */
    @AfterAll
    public static void afterClass() throws IOException, SQLException {

        String removing = "Delete from user_profiles where username = ? or username = ? or username = ? or username = ? or username = ?";
        PreparedStatement stmt;
        Connection conn = mainController.getConn();
        stmt = conn.prepareStatement(removing);
        stmt.setString(1,"newACC");
        stmt.setString(2,"acc2");
        stmt.setString(3,"lovez");
        stmt.setString(4,"myaccount");
        stmt.setString(5,"ACC123");

        stmt.executeUpdate();

    }

    /** Sets up before tests run
     *
     * @throws IOException- thrown if file error occurs
     */
    @Before
    public void UserProfileSetupAndLogin() throws IOException, SQLException {
         mainController = new MainController();
         upm = mainController.getUserProfile();
    }

    /** Testing login and setup functionality.
     *
     * @throws IOException- thrown if file error occurs
     */
    @org.junit.jupiter.api.Test
    public void TestLoginAndSetup () throws IOException, SQLException {
        mainController.displayMainView();
        //testing login with acc that doesn't exist
        Assertions.assertFalse(upm.authenticate("newACC","1222"));
        //creating the account
        Assertions.assertTrue(upm.setupProfile("newACC", "123"));
        //trying to create same account w/ diff password
        Assertions.assertFalse(upm.setupProfile("newACC", "12333"));
        //setting up a new account with the same password  & diff username should work
        Assertions.assertTrue(upm.setupProfile("acc2", "123"));
        //trying to create same account
        Assertions.assertFalse(upm.setupProfile("acc2", "123"));
        //logging into my account
        Assertions.assertTrue(upm.authenticate("acc2", "123"));
        Assertions.assertEquals(upm.getUserName(),"acc2");
        Assertions.assertEquals(upm.getPassWord(),"123");
        //trying to log in with wrong password
        Assertions.assertFalse(upm.setupProfile("acc2", "12"));

    }

    /** Testing add and remove method for coins
     *
     * @throws IOException- thrown if file error occurs
     */
    @org.junit.jupiter.api.Test
    public void CoinMethods() throws SQLException {
        Assertions.assertTrue(upm.setupProfile("ACC123", "password"));
        //Testing add
        upm.addOrRemoveCoins(10);
        Assertions.assertEquals(upm.getCoinTotal(), 10);
        //Testing remove
        upm.addOrRemoveCoins(-9);
        Assertions.assertEquals(upm.getCoinTotal(), 1);

        upm.addOrRemoveCoins(20);
        Assertions.assertEquals(upm.getCoinTotal(), 21);
    }

    /** Testing inventory methods
     *
     * @throws IOException- thrown if file error occurs
     */
    @org.junit.jupiter.api.Test
    public void InventoryMethods() throws SQLException {
        Assertions.assertTrue(upm.setupProfile("lovez", "passWord"));
        // all players start with a shovel, checking that initial inventory only contains one item
        Assertions.assertEquals(upm.getInventory().size(),1);
        Pickaxe pickaxe = new Pickaxe();
        upm.updateInventory(pickaxe,false);
        Assertions.assertTrue(upm.getInventory().contains(pickaxe));
        upm.updateInventory(pickaxe,true);
        Assertions.assertFalse(upm.getInventory().contains(pickaxe));
        Assertions.assertEquals(upm.getInventory().size(),1);

    }

    /**Testing character methods
     *
     * @throws IOException- thrown if file error occurs
     */
    @org.junit.jupiter.api.Test
    public void CharacterMethods() throws IOException, SQLException {

        Assertions.assertTrue(upm.setupProfile("myaccount", "somepassword"));
        // testing that character sprite not null
        ImageIcon zaharaIcon = new ImageIcon("src/zahara.png");
        upm.setCharacter(zaharaIcon);
        Assertions.assertNotNull(upm.getCharacter());

    }
}