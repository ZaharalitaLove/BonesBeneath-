import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * holds relatve varibles for classes that will have collision with tiles or other entities
 */
public abstract class Entity {

    //position of top right corner in pixels horizontally of entity in game world
    protected int worldX;
    //position of top right corner in pixels Vertically of entity in game world
    protected int worldY;
    // rate of change of position in pixels horizontally
    protected int xVelocity;
    // rate of change of position in pixels vertically
    protected int yVelocity;
    //entity hit box
    protected Rectangle solidArea;
    // if the entity is having a collsion
    protected boolean colliding;
    //if the entity is on the ground aka colliding the top of a tile
    protected boolean grounded;

    //entity image
    protected BufferedImage neutral;

    // Getters
    public int getWorldX() {
        return this.worldX;
    }

    public int getWorldY() {
        return this.worldY;
    }

    public int getXVelocity() {
        return this.xVelocity;
    }

    public int getYVelocity() {
        return this.yVelocity;
    }

    public boolean getCollision() {
        return this.colliding;
    }

    public boolean getGrounded() {
        return this.grounded;
    }
    public abstract String getType();

    // Setters
    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void setCollision(boolean collision) {
        this.colliding = collision;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public Rectangle getSolidArea() {
        return this.solidArea;
    }

    public abstract void attack() throws IOException, SQLException;

    public abstract void setColCheck(CollisionChecker collisionChecker);

    public abstract void update() throws SQLException;

    public abstract void drawEntity(Graphics2D g);

    public abstract void respondToAttack();

    public abstract boolean isEnemyAlive();

    public abstract void setEnemyAlive(boolean alive);

    public abstract void setRespondingToAttack(boolean responding);

    public abstract boolean respondingToAttack();

    public abstract int getDirectionX();

    public abstract int getDirectionY();

    public abstract void setDirectionX(int directionX);
    public abstract void setDirectionY(int directionY);
}
