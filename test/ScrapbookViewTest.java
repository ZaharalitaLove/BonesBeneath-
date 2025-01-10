import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * JUnit test for the ScrapbookView class
 */
public class ScrapbookViewTest {
    private MainController mainController;
    private ScrapbookController scrapbookController;
    ScrapbookView scrapbookView;

    /**
     * Creating a player to test
     *@throws IOException - thrown if player setup fails
     * @throws SQLException - thrown if SQL is not running properly
     */
    @Before
    public void setupPlayer() throws IOException, SQLException {
        mainController = new MainController();
        scrapbookController = new ScrapbookController(mainController);
       scrapbookView = new ScrapbookView(mainController, scrapbookController);
    }

    /**
     * Testing for the boneStage image
     */
    @Test
    public void getDinoImageTest() {
        scrapbookView.displayScrapbook();
        Assert.assertTrue(scrapbookView.getImage() instanceof ImageIcon);
    }
}
