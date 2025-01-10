import org.junit.Before;
import org.junit.Test;
import javax.swing.*;

import static org.junit.Assert.*;

/**
 * JUnit test for the Item class interface
 */
public class ItemTest {  // Made the class public
    private MockItem item;

    /**
     * Creating a mock item to test
     */
    @Before
    public void setUp() {
        item = new MockItem("Pickaxe", 1, "img/pickaxe.png");
    }

    /**
     * Tests that the right type of tool has been selected
     */
    @Test
    public void testGetType() {
        assertEquals("Pickaxe", item.getType());
    }

    /**
     * Tests that the correct ID has been obtained
     */
    @Test
    public void testGetID() {
        assertEquals(1, item.getID());
    }

    /**
     * Tests that the correct image path has been used
     */
    @Test
    public void testGetImagePath() {
        assertEquals("img/pickaxe.png", item.getImagePath());
    }

    /**
     * Tests that the correct item has been set
     */
    @Test
    public void testSetItem() {
        item.setItem("Pickaxe", new ImageIcon("img/pickaxe.png"));
        assertEquals("Pickaxe", item.getType());
        assertNotNull(item.getImagePath());
    }

    /**
     * Tests using the item
     */
    @Test
    public void testUseItem() {
        // Implementation for testing useItem goes here
    }

    /**
     * Creating a mock class to create an item to test
     */
    public static class MockItem implements Item {  // Made MockItem a static class
        private String name;
        private ImageIcon itemIcon;
        private String type;
        private int id;
        private String imagePath;

        /**
         * Constructor for the mock item
         * @param type - item type
         * @param id - item ID
         * @param imagePath - image path for the item
         */
        public MockItem(String type, int id, String imagePath) {
            this.type = type;
            this.id = id;
            this.imagePath = imagePath;
        }

        /**
         * Testing using the item
         * @param player - player
         * @param direction - taking the direction
         */
        @Override
        public void useItem(GamePlayer player, int direction) {
            // Mock implementation for testing useItem
        }

        /**
         * Testing setting the item
         * @param name - name of the item
         * @param itemIcon - image icon of the item
         */
        @Override
        public void setItem(String name, ImageIcon itemIcon) {
            this.name = name;
            this.itemIcon = itemIcon;
        }

        /**
         * Testing getting the type of the item
         * @return - returning the item type
         */
        @Override
        public String getType() {
            return type;
        }

        /**
         * Testing getting the ID
         * @return - returning the ID
         */
        @Override
        public int getID() {
            return id;
        }

        /**
         * Testing getting the proper image path
         * @return - returning the image path
         */
        @Override
        public String getImagePath() {
            return imagePath;
        }
    }
}
