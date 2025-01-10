import javax.swing.*;

/**
 * Item class that can be implemented by the Gems class, Pickaxe class, Jackhammer class, and Shovel Class
 */
public interface Item {

    public abstract void useItem(GamePlayer player, int direction);

    public abstract void setItem(String name, ImageIcon itemIcon);
    public String getType();
    public int getID();
    public String getImagePath();


}