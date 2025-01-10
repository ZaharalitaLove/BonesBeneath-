import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/** Class is a way of authenticating/referencing,storing, and updating user profile information.
 */
public class UserProfileModel {
    private String userName = "";
    private String passWord = "";
    private ArrayList<Item> inventory = new ArrayList<>();
    private ArrayList<Integer> collectedSprites = new ArrayList<>();
    private int coins = 0;
    private int bones = 0;
    private ImageIcon character = new ImageIcon("/sugar.png");
    private MainController mc;
    private ShopController shopController;
    private ShopModel shopModel;

    /**Constructor takes in the main controller for the game
     */
    public UserProfileModel(MainController mainController) throws SQLException {
        this.mc = mainController;
        this.shopController = new ShopController(mainController);
        this.shopModel = shopController.getShopModel();
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
            return;
        }
    }

    /**Method determines if the provided username/password is valid for a user logging in. Checks that the username/password combo
     * exist, updates the programs current user model to reflect user and returns true.
     *
     * @param username - username entry from user logging in or setting up
     * @param password - password entry from user logging in or setting up
     * @return - status of authentication
     * @throws IOException - thrown if file error occurs
     */
    public boolean authenticate(String username, String password){
        String result = "";

        try {
            String query = "SELECT * from user_profiles WHERE username=? AND password=?";
            mc.setStatement(mc.getConn().prepareStatement(query));
            mc.getStatement().setString(1, username);
            mc.getStatement().setString(2, password);
            ResultSet set = mc.getStatement().executeQuery();

            while (set.next()) {
                String name = set.getString("username");
                String pass = set.getString("password");

                result = name;
            }

            if (result.equals(username)) {
                changeUserProfile(username, password,true);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /** Method determines if the provided username/password is valid. The class checks that the given username is not already
     * in the database of user profiles. If the username isn't taken then it will add the new user to the database,
     * update the programs current user model to reflect that user and return true.
     * @param username
     * @param password
     * @return
     */
    public boolean setupProfile(String username, String password) throws SQLException {
        String query = "SELECT * from user_profiles WHERE username=?";
        mc.setStatement(mc.getConn().prepareStatement(query));
        mc.getStatement().setString(1, username);
        ResultSet set = mc.getStatement().executeQuery();
        if(!set.isBeforeFirst()) {
            try {
                String insertQuery = "INSERT into user_profiles VALUES (?,?,?,?,?)";
                mc.setStatement(mc.getConn().prepareStatement(insertQuery));
                mc.getStatement().setString(1, username);
                mc.getStatement().setString(2, password);
                mc.getStatement().setInt(3,4);
                mc.getStatement().setInt(4, 0);
                mc.getStatement().setInt(5, 0);

                mc.getStatement().executeUpdate();
                changeUserProfile(username,password,false);
                updateInventory(new Shovel(),false);
                addToCollectedSprites(4);
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /** Updates current user's inventory in program and database , either adding or removing items.
     *
     * @param item - item to add or remove
     * @param remove - whether the item should be removed, if false then add item
     */
    public void updateInventory(Item item, boolean remove) throws SQLException {
        try{
        if(remove){
            inventory.remove(item);
                String removing = "Delete from inventory where itemID = ? and userID = ?";
                mc.setStatement( mc.getConn().prepareStatement(removing));
                mc.getStatement().setInt(1, item.getID());
                mc.getStatement().setString(2, userName);
                mc.getStatement().executeUpdate();

        }
        else {inventory.add(item);

                String insert = "INSERT into inventory VALUES (?,?)";
                mc.setStatement(mc.getConn().prepareStatement(insert));
                mc.getStatement().setInt(1, item.getID());
                mc.getStatement().setString(2, userName);
                mc.getStatement().executeUpdate();

        }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Updates current user's coin total in program and database either adding or subtracting coins.
     *
     * @param coins - amount of coins to add or remove , if negative coins are removed
     */
    public void addOrRemoveCoins(int coins)  {
        try{this.coins += coins;
        String insertQuery = "Update user_profiles set coins = ? where username = ?";
        mc.setStatement(mc.getConn().prepareStatement(insertQuery));
        mc.getStatement().setInt(1,this.coins);
        mc.getStatement().setString(2, userName);
        mc.getStatement().executeUpdate();}
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getCoinTotal() {
        return coins;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /** Updates the users character image in the program and database to reflect their chosen sprite.
     *
     * @param charIcon - character icon image
     * @throws SQLException - thrown if SQL exception occurs
     */
    public void setCharacter(ImageIcon charIcon) throws SQLException {
        try{
            this.character = charIcon;
            String insertQuery = "UPDATE user_profiles SET currentsprite = ? WHERE username = ?";
            mc.setStatement(mc.getConn().prepareStatement(insertQuery));
            mc.getStatement().setInt(1, getCharacterID(charIcon.toString()));
            mc.getStatement().setString(2, userName);
            mc.getStatement().executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public String getCharacter() {
        return character.toString();
    }

    public String getUserName() {
        return userName;
    }

   public String getPassWord() {
        return passWord;
    }

    /** When a user logs in or sets up a new profile, this method is used to reset and update class attributes to
     * reflect the current user
     *
     * @param username - current username
     * @param password- current password
     * @throws SQLException
     */
    public void changeUserProfile(String username, String password, boolean existing) throws SQLException {
        try{
            this.userName = username;
            this.passWord = password;
        //reseting info

        coins = 0;
        bones = 0;

            if (existing) {
                inventory.clear();
                collectedSprites.clear();
                String query1 = "SELECT user_profiles.coins,items.item_name,user_profiles.bones from user_profiles, items, inventory where items.itemID = inventory.itemID and inventory.userID = user_profiles.username and user_profiles.username=?";
                mc.setStatement(mc.getConn().prepareStatement(query1));
                mc.getStatement().setString(1, username);
                ResultSet set1 = mc.getStatement().executeQuery();
                ImageIcon image = new ImageIcon();
                int coinTemp = 0;
                int bonesTemp = 0;

                while (set1.next()) {
                    coinTemp = set1.getInt("coins");
                    Item item = getItem(set1.getString("item_name"));
                    inventory.add(item);
                    bonesTemp = set1.getInt("bones");
                }
                coins = coinTemp;
                bones = bonesTemp;


                updateSprite();

            }
        }

        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /** Method used to get an Item instance of an Item based on the parameter type
     *
     * @param type - type of item to return and instance of
     * @return - Item instance correlating to the parameter type
     */
    public Item getItem(String type){
        return switch (type) {
            case "Pickaxe" -> new Pickaxe();
            case "Shovel" -> new Shovel();
            case "Jackhammer" -> new Jackhammer();
            default -> null;
        };
    }

    /**Method used to get the character id that corresponds to the parameter image path
     * @param imgPath - path of character image
     * @return - character id
     */
    public int getCharacterID(String imgPath){

        if(imgPath.equals("/coco.png")||imgPath.equals("img/coco.png")){
            return 1;
        }
        else if(imgPath.equals("/meg.png")||imgPath.equals("img/meg.png")){
            return 2;
        }
        else if(imgPath.equals("/zahara.png")||imgPath.equals("img/zahara.png")){
            return 3;
        }
        else if(imgPath.equals("/sugar.png")||imgPath.equals("img/sugar.png")){
            return 4;
        }
        return 0;
    }

    public int getBones() {
        return bones;
    }

    public void addBones(int bones) {
        try{this.bones += bones;
            String insertQuery = "Update user_profiles set bones = ? where username = ?";
            mc.setStatement(mc.getConn().prepareStatement(insertQuery));
            mc.getStatement().setInt(1,this.bones);
            mc.getStatement().setString(2, userName);
            mc.getStatement().executeUpdate();}
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> getCollectedSprites() {
        return collectedSprites;
    }

    public void addToCollectedSprites(int spriteID) {
        try{ this.collectedSprites.add(spriteID);
            String insertQuery = "Insert into user_sprites values(?,?)";
            mc.setStatement(mc.getConn().prepareStatement(insertQuery));
            mc.getStatement().setInt(1,spriteID);
            mc.getStatement().setString(2, userName);
            mc.getStatement().executeUpdate();}
        catch (SQLException e){
            e.printStackTrace();
        }

    }
    public void updateSprite() throws SQLException {
        String query = "SELECT sprite.path,sprite.spriteID from user_profiles, sprite WHERE user_profiles.currentsprite = sprite.spriteID and  user_profiles.username = ?";
        mc.setStatement(mc.getConn().prepareStatement(query));
        mc.getStatement().setString(1, userName);
        ResultSet set = mc.getStatement().executeQuery();
        String path = "";
        while(set.next()){
            int spriteID = set.getInt("spriteID");
            path = set.getString("path");
            collectedSprites.add(spriteID);
        }
        character = new ImageIcon(path);
    }
}

