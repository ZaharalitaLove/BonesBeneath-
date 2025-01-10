
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/** Main controller and executable for the program
 */
public class MainController {
    private LoginController loginController = new LoginController(this);
    private ProfileSetUpController profileController = new ProfileSetUpController(this);
    private HubController hubController = new HubController(this);
    private MiniGameOneController minigameController = new MiniGameOneController();
    private GameController gameController = new GameController(this);
    private MainView mainView = new MainView(this);
    private HubView hubView = new HubView(this, hubController);
    private ScrapbookController scrapbookController = new ScrapbookController(this);
    private UserProfileModel userProfileModel = new UserProfileModel(this);
    private ShopController shopController = new ShopController(this);
    private Connection conn;
    private PreparedStatement statement;

    private boolean gameBegan = false;

    public MainController() throws IOException, SQLException {

        userProfileModel.makeDBConnection();
    }

    /*** Runs the program by calling the main view
     * @param args - system input
     */
    public static void main(String[] args) throws SQLException, IOException {
            MainController mainController = new MainController();
            mainController.displayMainView();
    }

    /**
     * Initiates the playable game through game controller
     */
    public void initiateGame() throws IOException, SQLException {
        if(!gameBegan){
            gameController = new GameController(this);
            gameController.setUserProfile(userProfileModel);
            gameController.loadSprite();
            mainView.getFrame().add(gameController.getGameView());
            mainView.getFrame().pack();
            gameController.getGameView().setVisible(true);
            gameController.getGameView().requestFocus();
            gameController.startGameThread();
            gameBegan = true;
        }else{
            gameController.setUserProfile(userProfileModel);
            gameController.loadSprite();
            mainView.getFrame().add(gameController.getGameView());
            mainView.getFrame().pack();
            gameController.getGameView().setVisible(true);
            gameController.getGameView().requestFocus();
            gameController.resetGame();

        }

    }

    /**Makes login request through login controller
     */
    public void requestLogin() {
        loginController.loginToAccount();
    }

    /**Makes profile setup request through profile controller
     * @throws IOException - if file error occurs
     */
    public void requestProfileSetup() throws IOException {
        profileController.createNewAccount();
    }

    public JFrame getGameFrame() {
        return mainView.getFrame();
    }

    /**
     * Runs the opening screen for the program through main view
     */
    public void displayMainView() {
        mainView.displayOpeningScreen();
    }

    public MainView getMainView() {
        return mainView;
    }

    public void disableHubViewButtons(){
        hubView.disableHubButtons();
    }

    public LoginController getLoginController() {
        return loginController;
    }

    public ProfileSetUpController getProfileController() {
        return profileController;
    }

    /** Retrieves a user model according to the given username
     *
     * @return - user model that has correct username
     */
    public UserProfileModel getUserProfile() {
         return userProfileModel;
    }

    /** Displays the shop using shopController
     *
     */
    public void displayShop() throws SQLException{
        shopController.displayShopView();
    }

    public void showHub() throws SQLException {
        hubController.displayHubView();
    }

    public ShopController getShopController() {
        return shopController;
    }
    public GameController getGameController() {
        return gameController;
    }
    public LoginController getLoginController1() {
        return loginController;
    }
    public HubController getHubController() {
        return hubController;
    }
 public void showScrapbook(){
        scrapbookController.displayScrapbookView();
 }

    /**Closes the connection to the database when program ends
     * @throws SQLException - thrown if sql error occurs
     */
 public void closeDatabse() throws SQLException{
        if(statement != null && conn != null){
        statement.close();
        conn.close();}
 }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public void setStatement(PreparedStatement statement) {
        this.statement = statement;
    }

}