
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * the game player encompasses the games charater and camera, and all relavant attributes of them outside
 * of those covered in entity which this class extends
 */
public class GamePlayer extends Entity{
    //which direction the player is moving in x 0 is left, 1 is right, 2 is none
    private int directionX;

    //which direction the player is moving in y 0 is up, 1 is down, 2 is none
    private int directionY;

    //the players x position in the game relative to the gameview
    private int screenX;

    //the players y position in the game relative to the gameview

    private int screenY;

    //the x position of the center of the game view
    private int centerX;

    //the y position of the center of the game view
    private int centerY;

    //the y position of the center of the game view in terms of world
    private int centerWorldY;

    //the x position of the center of the game view in terms of world
    private int centerWorldX;

    //the x limits for camera motion in the x direction as to constrain view to the tiles
    private int cameraboundX;

    //the y limits for camera motion in the y direction as to constrain view to the tiles
    private int cameraboundY;

    //the maximum number of pixels per frame the player can move
    private int maxVelocity = 20;

    //the number of pixels per frame the player can run
    private int runVelocity =8;

    //the number of pixels per frame the player can jump when grounded
    private int jumpVelocity = 16;

    //how close to the player the camera must stay
    private int camTrackDist = 40;

    private int playercamoffsetX;
    private int playercamoffsetY;
    private BufferedImage playerStanding;
    private BufferedImage playerRunningRight;
    private BufferedImage playerRunningLeft;
    private BufferedImage playerDigging;
    private GameController gc;
    private KeyBoardHandler keyH;
    private Entity enemy;
    MapModel map;
    private CollisionChecker colCheck;
    private int move = 0;
    private boolean respondingToAttack;
    private GameUI ui;
    private Item currentItem;
    private UserProfileModel upm;
    private MainController mainController;
    private ShopController shopController;


    public GamePlayer(GameController gc, KeyBoardHandler keyH) {
        this.gc = gc;
        this.keyH = keyH;
        this.map = gc.getMap();
        upm = gc.getUserProfile();
        mainController = gc.getMainController();
        shopController = mainController.getShopController();


        //intial varible set up
        screenX = (gc.getScreenWidth()/2) - 32;
        screenY = (gc.getScreenHeight()/2) - 32;
        centerX = screenX;
        centerY = screenY;
        cameraboundY = screenY;
        cameraboundX = screenX;
        centerWorldX = 400;
        centerWorldY = 310;

        solidArea = new Rectangle(8,8,48,50);


        setDefaultValues();
    }

    public void setDefaultValues() {

        worldX = 320;
        worldY = 320;


    }

    public void setCurrentItem(){
        this.currentItem = ui.getCurrentItem();
    }

    public void getPlayerImage() {
        try {
//            upm = gc.getUserProfile();
            String[] path = upm.getCharacter().split(".png");
            String partialPath = path[0];
            playerStanding = ImageIO.read(getClass().getResourceAsStream(upm.getCharacter()));
            playerRunningRight = ImageIO.read(getClass().getResourceAsStream(partialPath + "RunRight.png"));
            playerRunningLeft = ImageIO.read(getClass().getResourceAsStream(partialPath + "RunLeft.png"));
            playerDigging = ImageIO.read(getClass().getResourceAsStream(partialPath + "Dig.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public void attack() throws IOException {

    }
    public void setUi(GameUI ui){
        this.ui = ui;
        setCurrentItem();

    }

    public void setColCheck(CollisionChecker colCheck){
        this.colCheck = colCheck;
    }
    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getCenterWorldY() {
        return centerWorldY;
    }

    public void setCenterWorldY(int centerWorldY) {
        this.centerWorldY = centerWorldY;
    }

    public int getCenterWorldX() {
        return centerWorldX;
    }

    public void setCenterWorldX(int centerWorldX) {
        this.centerWorldX = centerWorldX;
    }

    public int getCameraboundX() {
        return cameraboundX;
    }

    public void setCameraboundX(int cameraboundX) {
        this.cameraboundX = cameraboundX;
    }

    public int getCameraboundY() {
        return cameraboundY;
    }

    public void setCameraboundY(int cameraboundY) {
        this.cameraboundY = cameraboundY;
    }

    public int getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(int maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public int getRunVelocity() {
        return runVelocity;
    }

    public void setRunVelocity() {
        shopController = mainController.getShopController();
        LinkedHashMap<String,int[]> sprites = shopController.getSpritesFromModel();
        for(String path : sprites.keySet()){
            if(path.equals(upm.getCharacter().toString())){
                int[] info = sprites.get(path);
                this.runVelocity = info[2];
            }
        }
    }

    public int getJumpVelocity() {
        return jumpVelocity;
    }

    public void setJumpVelocity() {
        shopController = mainController.getShopController();
        LinkedHashMap<String,int[]> sprites = shopController.getSpritesFromModel();
        for(String path : sprites.keySet()){
            if(path.equals(upm.getCharacter().toString())){
                int[] info = sprites.get(path);
                this.jumpVelocity = info[1];
            }
        }

    }

    public int getCamTrackDist() {
        return camTrackDist;
    }

    public void setCamTrackDist(int camTrackDist) {
        this.camTrackDist = camTrackDist;
    }
    public ShopController getShopController() {
        return shopController;
    }

    //the players update function handles key inputs from keyboardhandler and then acts acoordingly
    public void update() throws SQLException {
        // update player action states based on input and collision

        directionX = 2;
        directionY = 2;
        if(keyH.isUp()) {

            directionY = 0;
        }
        if(keyH.isLeft()) {
            //worldX -= speed;
            directionX = 0;
        }
        if(keyH.isRight()) {
            //worldX += speed;
            directionX = 1;
        }
        if(keyH.isDarrow()){
            setCurrentItem();
            digDown();
            move = 3;
        }
        if(keyH.isLarrow()){
            setCurrentItem();
            digLeft();
        }
        if(keyH.isRarrow()){
            setCurrentItem();
            digRight();
        }
        if(keyH.isUarrow()){
            setCurrentItem();
            digUp();
        }
        if(keyH.isEscape()){
            escapeGame();
        }
        if(keyH.isOne()){
            ui.setSelectedSquare(1);
        }
        if(keyH.isTwo()){
            ui.setSelectedSquare(2);
        }
        if(keyH.isThree()){
            ui.setSelectedSquare(3);
        }
        if(keyH.isFour()){
            ui.setSelectedSquare(4);
        }
        try {
            move();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cameraMove();
    }
    //escapes game and return to hub
    public void escapeGame() throws SQLException {
        gc.escapeGame();
        keyH.setEscape(false);
    }
    // draws player in proper frame depedning on movement
    public void drawEntity(Graphics2D g){

        screenX = worldX - centerWorldX + centerX;
        screenY = worldY - centerWorldY + centerY;
        if(move == 0){g.drawImage(playerStanding, screenX, screenY, gc.getTileSize(),gc.getTileSize(), null);}
        if(move == 1){g.drawImage(playerRunningLeft, screenX, screenY, gc.getTileSize(),gc.getTileSize(), null);}
        if(move == 2){ g.drawImage(playerRunningRight, screenX, screenY, gc.getTileSize(),gc.getTileSize(), null);}
        if(move == 3){g.drawImage(playerDigging, screenX, screenY, gc.getTileSize(),gc.getTileSize(), null);}
    }

    @Override
    public void respondToAttack() {
        if(directionX == 0){
            directionX = 1;
        }
        if(directionX == 1){
            directionX = 0;
        }
    }

    @Override
    public boolean isEnemyAlive() {
        return false;
    }

    @Override
    public void setEnemyAlive(boolean alive) {

    }

    @Override
    public void setRespondingToAttack(boolean responding) {
        this.respondingToAttack = responding;
    }

    @Override
    public boolean respondingToAttack() {
        return respondingToAttack;
    }

    @Override
    public int getDirectionX() {
        return directionX;
    }

    @Override
    public int getDirectionY() {
        return directionY;
    }

    @Override
    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    @Override
    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    //moves left
    public void moveLeft(){
        xVelocity -= 1;
    }
    //moves right
    public void moveRight(){
        xVelocity += 1;
    }
    // determines tile below player the mines it
    public void digDown(){

        if(currentItem != null) {
            currentItem.useItem(this, 3);
        }
    }
    // determines tile above player the mines it
    public void digUp(){

        if(currentItem != null) {
            currentItem.useItem(this, 1);
        }
    }


    // determines tile to left of player the mines it
    public void digLeft(){

        if(currentItem != null) {
            currentItem.useItem(this, 4);
        }
    }
    // determines tile to right of player the mines it
    public void digRight(){

        if(currentItem != null) {
            currentItem.useItem(this, 2);
        }
    }
    public void mineTile(int x, int y){
        map.mineTile(x,y);
    }
    //jumps
    public void Jump(){

        yVelocity -= jumpVelocity;

    }
    //determines movement based on keyboard input and collsion status
    public void move() throws IOException {
        // TODO: handle moving
        //adds gravity
        yVelocity += 1;

        //assume no collsions before check
        colliding = false;
        grounded = false;
        //check if player is collding or grounded
       colCheck.checkTile(this);

       if(gc.getEnemy().isEnemyAlive()){
           try {
               colCheck.checkEntityCollision(this, gc.getEnemy());
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
       }


        //applie movments if allowed by collsion status
        if(grounded && !colliding) {
            if (directionX == 0) {
                if (xVelocity > -runVelocity) {

                    move = 1;
                    moveLeft();

                }
            } else if (directionX == 1) {
                if (xVelocity < runVelocity) {

                    move = 2;
                    moveRight();
                }
            } else if (directionY == 2) {

                xVelocity = 0;
            }
            if (directionY == 0) {
                Jump();


            }
        }else if(!colliding && !grounded) {

            if (directionX == 0) {
                if (xVelocity > -runVelocity) {

                    move = 1;
                    moveLeft();

                }
            } else if (directionX == 1) {
                if (xVelocity < runVelocity) {

                    move = 2;
                    moveRight();

                }
            }


        }


        //limit physics to max Velocity
        if(xVelocity > maxVelocity) {
            xVelocity = maxVelocity;
        }else if(yVelocity > maxVelocity) {
            yVelocity = maxVelocity;
        }

        if(xVelocity < -maxVelocity) {
            xVelocity = -maxVelocity;
        }else if(yVelocity < -maxVelocity) {
            yVelocity = -maxVelocity;
        }

        //apply velocity to the player posiiton
        worldX += xVelocity;
        worldY += yVelocity;
    }


    //handles moving the varibles related to the camera as long as it wouldnt move the camera out of bounds
    public void cameraMove(){
        // TODO: handle moving the camera
        playercamoffsetX = worldX - centerWorldX;
        playercamoffsetY = worldY - centerWorldY;

        if(playercamoffsetX < -camTrackDist && centerWorldX > cameraboundX) {
            centerWorldX += xVelocity;
            if(xVelocity == 0) {
                centerWorldX -= 4;
            }
        }else if(playercamoffsetX > camTrackDist && centerWorldX < (2496-cameraboundX)) {
            centerWorldX += xVelocity;
            if(xVelocity == 0) {
                centerWorldX += 4;
            }
        }
        if(playercamoffsetY < -camTrackDist && centerWorldY > cameraboundY) {
            centerWorldY += yVelocity -1;
        }else if(playercamoffsetY > camTrackDist && centerWorldY < (2496-cameraboundY)) {
            centerWorldY += yVelocity +1;
        }
    }

}