import javax.swing.*;

/**
 * Jackhammer class that can be called throughout the game
 */
public class Jackhammer implements Item {
    private String type;
    private ImageIcon itemIcon;
    private int id = 4;
    /**
     * Constructor for the jackhammer
     */
    public Jackhammer() {
        this.type = "Jackhammer";
        this.itemIcon = new ImageIcon("/jackhammer.png");
    }

    @Override
    public void useItem(GamePlayer player, int direction) {
        switch (direction) {
            case 1:
                int middleWorldX = player.worldX + ((player.solidArea.x + player.solidArea.width) / 2);
                int topWorldY = player.worldY + player.solidArea.y - 10;
                int middle = middleWorldX / 64;
                int top = topWorldY / 64;
                player.mineTile(middle, top);
                break;
            case 2:
                int middleWorldY = player.worldY + ((player.solidArea.y + player.solidArea.height) / 2);
                int rightWorldX = player.worldX + player.solidArea.x + player.solidArea.width + 10;
                middle = middleWorldY / 64;
                int right = rightWorldX / 64;
                player.mineTile(right, middle);
                break;
            case 3:
                middleWorldX = player.worldX + ((player.solidArea.x + player.solidArea.width) / 2);
                int bottomWorldY = player.worldY + player.solidArea.y + player.solidArea.height + 10;
                middle = middleWorldX / 64;
                int bottom = bottomWorldY / 64;
                player.mineTile(middle, bottom);
                break;
            case 4:
                middleWorldY = player.worldY + ((player.solidArea.y + player.solidArea.height) / 2);
                int leftWorldX = player.worldX + player.solidArea.x - 10;
                middle = middleWorldY / 64;
                int left = leftWorldX / 64;
                player.mineTile(left, middle);
                break;
            default:
                // code block
        }
    }

    @Override
    public void setItem(String name, ImageIcon itemIcon) {

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public String getImagePath() {
        return "img/jacklhammer.png";
    }
}