import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/** Class extends abstract Entity class. Makes up functionality of Snake enemy objects
 */
public class Snake extends Entity{
    private GameController gc;
    private GamePlayer player;
    private BufferedImage leftImg;
    private BufferedImage rightImg;
    private KeyBoardHandler keyH;
    private MapModel map;
    private CollisionChecker colCheck;
    private boolean enemyAlive;
    private String type = "Snake";

    //which direction the player is moving in x 0 is left, 1 is right, 2 is none
    private int directionX;

    //which direction the player is moving in y 0 is up, 1 is down, 2 is none
    private int directionY;

    //the players x position in the game relative to the gameview
    private int screenX;

    //the players y position in the game relative to the gameview

    private int screenY;

    //the maximum number of pixels per frame the player can move
    private int maxVelocity = 20;

    //the number of pixels per frame the player can run
    private int runVelocity =11;

    /** Constructor initializes the enemy's attributes.
     *
     * @param gameController - game controller instance
     * @throws IOException -  thrown if file error occurs
     */
    public Snake(GameController gameController) throws IOException {
        this.gc = gameController;
        this.keyH = gc.getKeyH();
        this.map = gc.getMap();
        this.player = gc.getPlayer();

        //intial varible set up
        screenX = (gc.getScreenWidth()/2) - 32;
        screenY = (gc.getScreenHeight()/2) - 32;

        solidArea = new Rectangle(12,12,38,30);

        enemyAlive = true;
        int[] spawn = map.getRandomBackgroundCoordinantes();
        setDefaultValues(spawn[0],spawn[1]);
        getEntityImage();
    }

    @Override
    public String getType() {
        return type;
    }

    /** Method used when player/enemy side collision occurs. Enemy does damage by decreasing player's coins.
     */
    public void attack() {
        gc.getCoins(-5);
    }

    /** Method draws enemy sprite to screen
     * @param g - graphics2d object instance
     */
    public void drawEntity(Graphics2D g) {
        screenX = worldX - player.getCenterWorldX() + player.getCenterX();
        screenY = worldY - player.getCenterWorldY() + player.getCenterY();

        if(directionX == 0){
            g.drawImage(leftImg, screenX, screenY, gc.getTileSize(),gc.getTileSize(), null);
        }

        else if (directionX == 1){
            g.drawImage(rightImg, screenX, screenY, gc.getTileSize(),gc.getTileSize(), null);
        }

    }

    /** If enemy is attacked by player, enemy will cease to exist in game. Method handles changing enemy image and
     * setting boolean enemyAlive to false.
     * @throws IOException - if file error occurs
     */
    public void respondToAttack() {
        this.enemyAlive = false;
    }

    /**Determines movement based on player's movement and enemy's relative location to player.
     */
    public void update() {
        directionX = 2;
        directionY = 2;

            if (worldX > player.getWorldX()) {
                directionX = 0;
            } else {
                directionX = 1;
            }

        try {
            move();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**Moves player right
     */
    public void moveRight(){
        xVelocity += 1;
    }

    /** Moves player left
     */
    public void moveLeft() {
        xVelocity -= 1;
    }

    /** Makes enemy move based on player's movement and enemy's relative location to player.
     *  Makes enemy behave as if chasing player.
     * @throws IOException -  thrown if file error occurs
     */
    public void move() throws IOException, SQLException {
        //adds gravity
        yVelocity += 1;

        //assume no collisions before check
        colliding = false;
        grounded = false;
        //check if player is colliding or grounded
        colCheck.checkTileNoCollect(this);


        //apply movements if allowed by collision status
        if(grounded  && !colliding ) {
            if (directionX == 0) {
                if (xVelocity > -runVelocity) {
                    moveLeft();
                }
            } else if (directionX == 1) {
                if (xVelocity < runVelocity) {
                    moveRight();
                }
            }
            else if (directionY == 2) {
                xVelocity = 0;
            }
        }
        else if(!colliding && !grounded) {

            if (directionX == 0) {
                if (xVelocity > -runVelocity) {
                    moveLeft();
                }
            } else if (directionX == 1) {
                if (xVelocity < runVelocity) {
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

        //apply velocity to the player position
        worldX += xVelocity;
        worldY += yVelocity;
    }

    //setters and getters
    public boolean isEnemyAlive(){
        return this.enemyAlive;
    }
    public void setEnemyAlive(boolean alive) {
        this.enemyAlive = alive;
    }
    public void setRespondingToAttack(boolean responding) {

    }
    public boolean respondingToAttack() {
        return false;
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

    @Override
    public void setColCheck(CollisionChecker collisionChecker) {
        this.colCheck = collisionChecker;
    }

    public void getEntityImage() throws IOException {
        this.rightImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/snakeRight.png")));
        this.leftImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/snakeLeft.png")));
    }

    public void setDefaultValues(int x, int y){
        worldX = x;
        worldY = y;
    }

}
