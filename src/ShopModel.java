import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;


/** Model class for shop, hold information about items and character sprites in the shop.
 * Interacts with user profile model to update the shop based on user's progress in game.
 */
public class ShopModel {
    private LinkedHashMap<String, int[]> items = new LinkedHashMap();
    private LinkedHashMap<Integer,Boolean> itemsBought = new LinkedHashMap();
    private LinkedHashMap<String,int[]> characters = new LinkedHashMap();
    private LinkedHashMap<Integer,Boolean> charactersUnlocked = new LinkedHashMap();
    private MainController mc;
    private UserProfileModel upm;

    /** Constructor for shop takes in main controller instance and sets attributes accordingly
     * @param mainController - main controller instance
     */
    public ShopModel(MainController mainController) {
        mc = mainController;
        upm = mc.getUserProfile();
        makeDBConnection();

    }
    /** Makes connection to thePROgrammer's database
     */
    public void makeDBConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
            return;
        }
        try{
            mc.setConn(DriverManager.getConnection("jdbc:mysql://localhost:1521/thePROgrammrs?user=z_love&password=Changeme_00"));
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /** Gets all item info from the database and adds to linked hashmaps that represent all items in the store
     * and all items bought by this user.
     * @throws SQLException - if sql error occurs
     */
    public void setItems() throws SQLException {
        String query = "SELECT * FROM items";
        mc.setStatement(mc.getConn().prepareStatement(query));
        ResultSet set = mc.getStatement().executeQuery();

        while (set.next()) {
            int itemId = set.getInt("itemId");
            String item_name = set.getString("item_name");
            int price = set.getInt("price");
            int[] info = new int[2];
            info[0] = itemId;
            info[1] = price;
            items.put(item_name, info);
            itemsBought.put(itemId, false);
        }
    }

    /** Gets all the current user's purchased items and updates the items bought linked hashmap to
     * reflect this user's progress.
     * @throws SQLException- if sql error occurs
     */
    public void setItemsBought() throws SQLException {

        String query2 = "SELECT * from inventory WHERE userID =?";
        mc.setStatement(mc.getConn().prepareStatement(query2));
        mc.getStatement().setString(1, upm.getUserName());
        ResultSet set2 = mc.getStatement().executeQuery();

        while (set2.next()) {
            int itemId = set2.getInt("itemId");
            itemsBought.put(itemId, true);
        }
    }

    public LinkedHashMap<String,int[]> getItems() {
        return items;
    }

    public LinkedHashMap<Integer,Boolean> getItemsBought(LinkedHashMap itemsAvailable) {
       return itemsBought;
    }
    /** Gets all character sprite info from the database and adds to linked hashmaps that represent all characters in the store
     * and all characters unlocked by this user.
     * @throws SQLException - if sql error occurs
     */
    public void setCharacters() throws SQLException {
        String query = "SELECT * FROM sprite";

        mc.setStatement(mc.getConn().prepareStatement(query));
        ResultSet set2 = mc.getStatement().executeQuery();

        while (set2.next()) {
            int spriteID = set2.getInt("spriteID");
            String path = set2.getString("path");
            int jump_velocity = set2.getInt("jump_velocity");
            int run_velocity = set2.getInt("run_velocity");
            int price = set2.getInt("price");
            int[] info = new int[4];
            info[0] = spriteID;
            info[1] = jump_velocity;
            info[2] = run_velocity;
            info[3] = price;
            characters.put(path, info);
            charactersUnlocked.put(spriteID, false);
        }

    }
    public LinkedHashMap<String,int[]> getCharacters() {
        return characters;
    }

    public LinkedHashMap<Integer,Boolean> getCharactersUnlocked() {
        return charactersUnlocked;
    }
    /** Gets all the current user's unlocked characters and updates the characters unlocked linked hashmap to
     * reflect this user's progress.
     * @throws SQLException- if sql error occurs
     */
    public void setCharactersUnlocked() throws SQLException {
        String query = "SELECT * FROM user_sprites where username= ?";

        mc.setStatement(mc.getConn().prepareStatement(query));
        mc.getStatement().setString(1, upm.getUserName());
        ResultSet set2 = mc.getStatement().executeQuery();

        while (set2.next()) {
            int spriteID = set2.getInt("spriteID");
            charactersUnlocked.put(spriteID, true);
        }
    }

    /** Handles the logic for purchasing an item from the shop. Checks that user has enough coins and that
     * item is not already purchased, if so the item is added to the user's inventory, their coin total is decreased
     * according to the item's price, and returns true. Otherwise, it returns false.
     *
     * @param itemName - name of item to be purchased
     * @param price - price of item to be purchase
     * @return - whether item was successfully purchased
     * @throws SQLException - thrown if sql exception occurs
     */
    public boolean purchaseItem(String itemName, int price) throws SQLException {
        Item item = upm.getItem(itemName);
        if (item != null) {
            System.out.println(item.getID());
            if (itemsBought.get(item.getID())) {
                showCustomPane("img/alreadyOwnedPopUp.png", "img/okayButton.png", "Item Already Owned");
                return false;
            }
            if (upm.getCoinTotal() < price) {
                showCustomPane("img/failedBuyPopUp.png", "img/sorryButton.png", "Insufficient Coins");
                return false;
            }
            upm.updateInventory(item, false);
            itemsBought.replace(item.getID(), true);
            upm.addOrRemoveCoins(-price);
            return true;
        }
        return false;
    }
    private void showCustomPane(String imagePath, String buttonImagePath, String dialogTitle) {
        JPanel panel = new JPanel(new BorderLayout());
        ImageIcon backgroundImage = new ImageIcon(imagePath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        ImageIcon scaledBackgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
        backgroundLabel.setIcon(scaledBackgroundImage);
        backgroundLabel.setBorder(null);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.setPreferredSize(new Dimension(599, 399));
        JButton actionButton = new JButton(HubView.scaleImageAsIcon(buttonImagePath, 350, 100));
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




    /**Handles the logic for unlocking a character sprite from the shop. Checks that user has enough coins and that
     * sprite is not already unlocked, if so the character is added to the user's collected sprites list, their coin total is decreased
     * according to the sprite's price, and returns true. Otherwise, it returns false.
     *
     * @param imgPath - full path of sprite image
     * @param path - partial path of sprite image
     * @param price - price of sprite
     * @return - whether the sprite was successfully purchased
     * @throws SQLException - thrown if sql exception occurs
     */
    public boolean unlockCharacter(String imgPath, String path, int price) throws SQLException {
        int characterID = upm.getCharacterID(imgPath);
        if (characterID != 0) {
            if (charactersUnlocked.get(characterID)) {
                showCustomPane("img/alreadyOwnedCharPopUp.png", "img/okayButton.png", "Character Already Unlocked");
                return false;
            }
            if (upm.getCoinTotal() < price) {
                showCustomPane("img/failedBuyPopUp.png", "img/sorryButton.png", "Insufficient Coins");
                return false;
            }
            upm.addToCollectedSprites(characterID);
            charactersUnlocked.replace(characterID, true);
            upm.setCharacter(new ImageIcon(path));
            upm.addOrRemoveCoins(-price);
            return true;
        }
        return false;
    }

}
