import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Handles the transactions from the user to the panel
 * Handles when the buttons are pressed and updates accordingly
 */
public class ShopController {
    private MainController mainController;
    private ShopView shopView;
    private int gold;
    private List<String> itemsForSale;
    private List<String> inventory;
    ImageIcon shovelIcon = new ImageIcon("src/shovel.png");
    ImageIcon pickaxeIcon = new ImageIcon("src/pickaxe.png");
    private Shovel shovel = new Shovel();
    private Pickaxe pickaxe = new Pickaxe();
    private Jackhammer jackhammer = new Jackhammer();
    private List<Item> itemsToSell = Arrays.asList(shovel,pickaxe,jackhammer);
    private ShopModel shopModel;
    /**
     * Constructor for the ShopController class that creates the shop and handles the different
     * attributes of the shop
     */
    public ShopController(MainController mainController) throws SQLException {
        this.mainController = mainController;
        this.shopView = new ShopView(mainController,this);
        this.shopModel = new ShopModel(mainController);

    }

    /**
     * Displaying the ShopView
     */
    public void displayShopView() throws SQLException {
        shopView.displayShopScreen();
    }



    /**
     * Updates the inventory in the view.
     */
    public void updateInventory() {
        //shopView.updateInventoryList(inventory);
    }

    /**
     * Updates the gold amount in the view.
     */
    public void updateGoldAmount() {
        //shopView.updateGoldAmountLabel(gold);
    }

    /**
     * Gets the sale price for an item (placeholder method).
     */
    private int getSalePrice(String itemName) {
        // Define sale prices for items
        switch (itemName) {
            case "Shovel":
                return 50;
            case "Pickaxe":
                return 100;
            case "Jackhammer":
                return 250;
            default:
                return 0;
        }
    }
    public List<Item> getItemsToSell() {
        return itemsToSell;
    }

    public boolean makePurchase(String itemName,int price) throws SQLException {
        return shopModel.purchaseItem(itemName,price);

    }
    public boolean unlockSprite(String imgPath,String path,int price) throws SQLException {
        return shopModel.unlockCharacter(imgPath,path,price);
    }
    public LinkedHashMap<String,int[]> getItemsFromModel(){
        return shopModel.getItems();
    }
    public  LinkedHashMap<String,int[]> getSpritesFromModel(){
        return shopModel.getCharacters();
    }
    public ShopModel getShopModel() {
        return shopModel;
    }
}