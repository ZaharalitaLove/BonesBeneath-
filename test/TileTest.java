import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * JUnit test for the Tile class
 */
public class TileTest {
    Tile tile;

    /**
     * Creating a tile manager to test the tile
     */
    @Before
    public void setupTileManager(){
        tile = new Tile();
    }

    /**
     * Testing the tile collision
     */
    @Test
    public void testTileCollision(){
        tile.setCollision(true);
        Assert.assertEquals(tile.getCollision(), true);
    }

    /**
     * Testing that the tile image path is correct
     * @throws IOException
     */
    @Test
    public void testTileImage() throws IOException {
        BufferedImage testimg = ImageIO.read(getClass().getResourceAsStream("/sandblock2.png"));
        tile.setImage(testimg);
        Assert.assertEquals(tile.getImage(), testimg);
    }
}
