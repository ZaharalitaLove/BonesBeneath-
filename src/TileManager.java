import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;

/** Manages Tile objects. Handles setting tile images, drawing tiles to screen, and traking
 * collision.
 */
public class TileManager {
    private Tile[] tiles;
    private GameController gc;
    private GamePlayer player;
    private MapModel map;

    /** Constructor takes in class instances and sets attribute accordingly,
     * instantiates tile array and sets up tiles.
     *
     * @param mm - map model instance
     * @param gc - game controller instance
     * @param player - game player instance
     */
    public TileManager(MapModel mm,GameController gc, GamePlayer player) {
        this.map = mm;
        this.player = player;
        this.gc = gc;

        tiles = new Tile[10];

        tileSetUp();
    }

    /** Sets up the tiles for the program. Adds tiles to tile array, sets the image for
     * the types of tiles, and sets the collision ability
     */
    public void tileSetUp() {
        try {
            tiles[0] = new Tile();
            tiles[0].setImage(ImageIO.read(getClass().getResourceAsStream("/sand3.png")));

            tiles[1] = new Tile();
            tiles[1].setImage(ImageIO.read(getClass().getResourceAsStream("/terracotta4.png")));
            tiles[1].setCollision(true);

            tiles[2] = new Tile();
            tiles[2].setImage(ImageIO.read(getClass().getResourceAsStream("/obsidian1.png")));
            tiles[2].setCollision(true);

            tiles[3] = new Tile();
            tiles[3].setImage(ImageIO.read(getClass().getResourceAsStream("/gold.png")));
            tiles[3].setValue(50);

            tiles[4] = new Tile();
            tiles[4].setImage(ImageIO.read(getClass().getResourceAsStream("/bones.png")));
            tiles[4].setValue(1);

        }catch(IOException e) {

            e.printStackTrace();
        }

    }

    /** Tracking if tile collision can occur with this type of tile
     *
     * @param x - tile number
     * @return -  if tile can collide
     */
    public boolean tilenumCollides(int x){
        return tiles[x].getCollision();
    }

    public int tilenumValue(int x){
        return tiles[x].getValue();
    }

    public void draw(Graphics2D g){
        //Draw tiles on screen
        int worldCol = 0;
        int worldRow = 0;
        int worldX;
        int worldY;
        int screenX;
        int screenY;
        int tileNum;
        int playerYOffCam;
        int playerXOffCam;



        while(worldCol < gc.getMaxWorldCol() && worldRow < gc.getMaxWorldRow()) {


            tileNum = map.accessMapData()[worldCol + (worldRow * gc.getMaxWorldCol())];

            worldX = worldCol * gc.getTileSize();
            worldY = worldRow * gc.getTileSize();
            screenX = worldX - player.getCenterWorldX() + player.getCenterX();
            screenY = worldY - player.getCenterWorldY() + player.getCenterY();


            if(worldX + gc.getTileSize()  > player.getCenterWorldX() - (player.getCenterX()+128) &&
                    worldX - gc.getTileSize() < player.getCenterWorldX() + (player.getCenterX()+128)  &&
                    worldY + gc.getTileSize() > player.getCenterWorldY() - (player.getCenterY()+128) &&
                    worldY - gc.getTileSize() < player.getCenterWorldY() + (player.getCenterY()+128)) {
                if(tileNum == 0 || tileNum == 1 || tileNum == 2){
                    g.drawImage(tiles[tileNum].getImage(), screenX, screenY, gc.getTileSize(), gc.getTileSize(), null);
                }else if(tileNum == 3){
                    g.drawImage(tiles[0].getImage(), screenX, screenY, gc.getTileSize(), gc.getTileSize(), null);
                    g.drawImage(tiles[tileNum].getImage(), screenX, screenY, gc.getTileSize(), gc.getTileSize(), null);
                }else{
                    g.drawImage(tiles[1].getImage(), screenX, screenY, gc.getTileSize(), gc.getTileSize(), null);
                    g.drawImage(tiles[tileNum].getImage(), screenX, screenY, gc.getTileSize(), gc.getTileSize(), null);
                }


            }

            worldCol++;

            if(worldCol == gc.getMaxWorldCol()) {
                worldCol = 0;

                worldRow++;
            }

        }
    }

}
