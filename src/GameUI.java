import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class creates the game ui for the inventory and determines which item the player is currently holding
 *
 */
public class GameUI {

    //ui images
    private BufferedImage square;
    private BufferedImage selectedSquare;
    private BufferedImage shovel;
    private BufferedImage pickaxe;
    private BufferedImage drill;
    private BufferedImage jackhammer;
    private int coinCount;
    //screen width
    private int width;

    Color myOvalColor = new Color(242, 216, 170);

    //inventory direct form user profile model
    private ArrayList<Item> inventory;

    //the background inventory boxes
    private int[] selection = new int[4];

    //array of item id's in each slot
    private int[] iven = new int[4];

    //current item
    private Item currentItem;

    private UserProfileModel userProfileModel;

    public GameUI(UserProfileModel userProfileModel){
        //link user profile model and set inital inventory
        this.userProfileModel = userProfileModel;
        imageSetUp();
        for(int i = 0; i < 4; i++){
            iven[i] = 0;
        }

    }

    public void updateCoins(){
        coinCount = userProfileModel.getCoinTotal();
    }

    /**
     * sets background iventory graphic and changes current item
     * @param slot: which iventory slot to set as current item
     */
    public void setSelectedSquare(int slot){
        //update inventory to user profile model
        inventory = userProfileModel.getInventory();
        //change slot num to index num
        slot = slot-1;
        for(int i = 0; i < 4; i++){
            if(i == slot) {
                selection[i] = 1;
            }else {
                selection[i] = 0;
            }
        }
        updateIventory();
        //set current item to the current item in new selected slot
        if (slot < inventory.size()) {
            if (inventory.get(slot) != null) {
                currentItem = inventory.get(slot);
            }
        }else{
            currentItem = null;
        }

    }

    //getter for GamePlayer
    public Item getCurrentItem() {
        return currentItem;
    }


   // Updates the id's in iven
    public void updateIventory(){
        for(int i = 0; i < 4; i++) {
            if (i < inventory.size()) {
                if (inventory.get(i) != null) {
                    iven[i] = inventory.get(i).getID();
                }
            }
        }
    }


    //change when window is resized
    public void changedWindow(int x){
        width = x;

    }

    //set images for ui on construction
    public void imageSetUp() {
        try {
            square = ImageIO.read(getClass().getResourceAsStream("/selectframe.png"));
            selectedSquare = ImageIO.read(getClass().getResourceAsStream("/uiframe.png"));
            pickaxe = ImageIO.read(getClass().getResourceAsStream("/pickaxe.png"));
            shovel = ImageIO.read(getClass().getResourceAsStream("/shovel.png"));
            drill = ImageIO.read(getClass().getResourceAsStream("/drill.png"));
            jackhammer = ImageIO.read(getClass().getResourceAsStream("/jackhammer.png"));

        }catch(IOException e) {

            e.printStackTrace();
        }

    }
    //draw ui over gameplay
    public void draw(Graphics2D g){
        //set space in between boxes
        int buffer = (width - (80*5))/6;
        //draw boxes
        for(int i = 0; i < 4; i++){
            if (selection[i] == 0){
                g.drawImage(square, buffer+((buffer+80)*i), 32, 80, 80, null);
            }else{
                g.drawImage(selectedSquare, buffer+((buffer+80)*i), 32, 80, 80, null);
            }
            //draw item images over their boxes
            if(iven[i]!=0){
              if(iven[i] == 1){
                  g.drawImage(shovel, 8+buffer+((buffer+80)*i), 40, 64, 64, null);
              } else if(iven[i] == 2){
                  g.drawImage(pickaxe, 8+buffer+((buffer+80)*i), 40, 64, 64, null);
              }else if(iven[i] == 3){
                  g.drawImage(drill, 8+buffer+((buffer+80)*i), 40, 64, 64, null);
              }else if(iven[i] == 4){
                  g.drawImage(jackhammer, 8+buffer+((buffer+80)*i), 40, 64, 64, null);
              }

            }
        }
        //draw coin counter
        g.setColor(myOvalColor);
        g.fillOval(25+((buffer+80)*4),30,180,80);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Baskerville old face", Font.BOLD, 25));
        g.drawString("Coins: "+Integer.toString(coinCount), 30+((buffer+80)*4), 80);

    }

}
