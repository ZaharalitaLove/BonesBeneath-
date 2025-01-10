import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;


/**
 * the main controller for the excavation level, has the main game loop, and holds all related classes with only
 * one instance ie, gameplayer, tilemanager, keyboard handler, map model, gameview and collsion checker
 */

public class GameController implements Runnable{
    private GamePlayer player;
    private TileManager tileManager;
    private KeyBoardHandler keyH;
    private GameView gameView;
    private CollisionChecker collisionChecker;
    private Thread gameThread;
    private MapModel map;
    private UserProfileModel userProfile;
    private EnemyFactory enemyFactory;
    private Entity enemy;
    private MainController mc;
    private GameUI ui;
    //number of pixels per tile
    private int tileSize = 64;
    //tiles in map horizontally
    private int maxWorldCol = 40;
    //tiles in map vertically
    private int maxWorldRow = 40;

    //frames per second
    private int FPS = 60;

    //whether gamethread has been started yet
    private boolean started = false;
    private int type = 1;


    public GameController(MainController mc) throws SQLException, IOException {
        this.mc = mc;

        enemyFactory = new EnemyFactory(this);
        keyH = new KeyBoardHandler();
        map = new MapModel(this);
        gameView = new GameView(keyH);
        player = new GamePlayer(this,keyH);
        enemy = enemyFactory.spawnEnemy("Scorpion");
        tileManager = new TileManager(map,this,player);
        collisionChecker = new CollisionChecker(this,map,tileManager);
        player.setColCheck(collisionChecker);
        enemy.setColCheck(collisionChecker);
        gameView.setPlayer(player);
        gameView.setEnemy(enemy);
        gameView.setTileManager(tileManager);
        userProfile = mc.getUserProfile();
        ui = new GameUI(userProfile);
        ui.changedWindow(getScreenWidth());
        gameView.setUi(ui);
        player.setUi(ui);

    }

    // Getters and setters
    public GamePlayer getPlayer() {
        return player;
    }

    public MapModel getMap(){return map;}
    public TileManager getTileManager() {
        return tileManager;
    }

    public GameUI getUI(){
        return ui;
    }

    public GameView getGameView() {
        return gameView;
    }


    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }


    public int getTileSize() {
        return tileSize;
    }

    public int getMaxWorldCol(){
        return maxWorldCol;
    }

    public int getMaxWorldRow(){
        return maxWorldRow;
    }

    public int getScreenWidth() {
        // TODO: handle getting the width of the screen
        return gameView.getScreenWidth();
    }

    public int getScreenHeight() {
        // TODO: handle getting the width of the screen
        return gameView.getScreenHeight();
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }
    public UserProfileModel getUserProfile() {
        return mc.getUserProfile();
    }
    //contray to my terrible naming this is not a getter rather it updates the user profile models coins
    public void getCoins(int val) {
        userProfile.addOrRemoveCoins(val);
        ui.updateCoins();
    }
    public MainController getMainController() {
        return mc;
    }

    /**
     * Bone method handles when the user encounters a bone in the game, and they have to successfully complete a mini-game
     * in order to obtain the bone
     * there are three mini-game options and one is randomly chosen every time that the user encounters a bone
     */
    public void bone() {
        // Randomly select between MiniGameOne, MiniGameTwo, and MiniGameThree
        keyH.noInput();
        Random random = new Random();
        int miniGameChoice = random.nextInt(3); // 0, 1, or 2
        switch (miniGameChoice) {
            case 0:
                // Set up MiniGameOne
                MiniGameOneController miniGameOneController = new MiniGameOneController();
                miniGameOneController.startMiniGame(won -> {
                    onMiniGameComplete(won);
                });
                break;
            case 1:
                // Set up MiniGameTwo
                MiniGameTwo miniGameTwo = new MiniGameTwo();
                MiniGameTwoController miniGameTwoController = new MiniGameTwoController(miniGameTwo);
                miniGameTwoController.startMiniGame(); // Start MiniGameTwo with instructions
                break;
            case 2:
                // Set up MiniGameThree
                MiniGameThree miniGameThree = new MiniGameThree(() -> onMiniGameComplete(true)); // Pass callback
                MiniGameThreeController miniGameThreeController = new MiniGameThreeController(miniGameThree);
                miniGameThreeController.startMiniGameThree(); // Start MiniGameThree
                break;
        }
    }

    /**
     * Method resumes the game after the MiniGame is over
     */
    public void resumeGame() {
        // Logic to resume the main game goes here
        System.out.println("Resuming the main game...");
    }

    /**
     * TODO: CHANGE LATER -
     * Currently determins if the player won or lost and prints the result to the terminal
     * replace with bone adding logic
     * @param won - taking in if the player won or lost
     */
    public void onMiniGameComplete(boolean won) {
        if (won) {
            userProfile.addBones(1);
            showCustomPane("img/youWonPopUp.png", "You Won!");
        } else {
            showCustomPane("img/youLostPopUp.png", "You Lost.");
        }
    }

    private void showCustomPane(String imagePath, String dialogTitle) {
        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon backgroundImage = new ImageIcon(imagePath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        ImageIcon scaledBackgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
        backgroundLabel.setIcon(scaledBackgroundImage);
        backgroundLabel.setBorder(null);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.setPreferredSize(new Dimension(599, 399));
        JButton actionButton = new JButton(HubView.scaleImageAsIcon("img/thanksButton.png", 350, 100));
        actionButton.setBorder(new EmptyBorder(0, 0, 50, 0));
        backgroundLabel.add(actionButton, BorderLayout.SOUTH);
        panel.add(backgroundLabel, BorderLayout.CENTER);
        actionButton.setOpaque(false);
        actionButton.setContentAreaFilled(false);
        actionButton.setBorderPainted(false);
        panel.setOpaque(true);
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = optionPane.createDialog(dialogTitle);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        actionButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // go back to hub
    public void escapeGame() throws SQLException {
        gameView.setVisible(false);

        mc.showHub();
    }

    public void resetGame() throws IOException {
        player.setWorldX(320);
        player.setWorldY(320);
        player.setScreenX((getScreenWidth()/2) - 32);
        player.setScreenY((getScreenHeight()/2) - 32);
        player.setCenterY(player.getScreenY());
        player.setCenterX(player.getScreenX());
        player.setCameraboundX(player.getScreenX());
        player.setCameraboundY(player.getScreenY());
        player.setCenterWorldY(420);
        player.setCenterWorldX(500);
        map.newMap();
        String enType;
        Random rand = new Random();
        int type = rand.nextInt(2);
        if(type == 1){
            enType = "Scorpion";

        }else{
            enType = "Snake";
        }

        enemy = enemyFactory.spawnEnemy(enType);
        enemy.setColCheck(collisionChecker);
        gameView.setEnemy(enemy);
        ui.setSelectedSquare(1);

    }
    // the main game loop calling update and draw FPS times per second
    public void run(){
        double delta = 0;
        double drawInterval = 1000000000/FPS;
        long currentTime;
        long lastTime = System.nanoTime();
        //long timer = 0;
        //int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
             //timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1) {

                try {
                    update();
                    gameView.repaint();
                    delta--;
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //starts the game thread
    public void startGameThread() throws SQLException {

        if (started == false) {
            gameThread = new Thread(this);
            gameThread.start();
            started = true;
            //updates ui now that everything is loaded
            ui.setSelectedSquare(1);
            ui.updateCoins();

        }
    }
    //update everything that needs to update every frame
    public void update() throws IOException, SQLException {
        player.update();

        if(enemy.isEnemyAlive()){
        enemy.update();
        }

    }
    public KeyBoardHandler getKeyH() {
        return keyH;
    }
    public Entity getEnemy(){
        return this.enemy;
    }
    public void loadSprite(){
        player.getPlayerImage();
        player.setRunVelocity();
        player.setJumpVelocity();
    }


}