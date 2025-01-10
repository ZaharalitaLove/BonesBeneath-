import java.awt.image.BufferedImage;

/**
 * Holds properties of each type of tile
 */

public class Tile {

    // the tiles image
   private BufferedImage image;

   //whether the tile can colide with entities
    private boolean collision;

    //the tiles value in case of coin tiles
    private int value;


    public BufferedImage getImage(){
        return image;
    }

    public void setImage(BufferedImage im){
        image = im;
    }
 public boolean getCollision(){
        return collision;
    }
 public void setCollision(boolean col){
        this.collision = col;
 }

    public int getValue(){
        return value;
    }
    public void setValue(int val){
        value = val;
    }

}


