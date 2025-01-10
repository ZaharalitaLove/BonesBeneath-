import javax.swing.*;

public class Drill implements Item{

    private String type;
    private ImageIcon itemIcon;
    private int id;

    public Drill(){
        this.type = "Drill";
        this.itemIcon = new ImageIcon("/jackhammer.png");
        this.id = 3;
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

                middleWorldX = player.worldX + ((player.solidArea.x + player.solidArea.width) / 2);
                topWorldY = player.worldY + player.solidArea.y - 74;
                middle = middleWorldX / 64;
                top = topWorldY / 64;
                if (top > 0) {
                    player.mineTile(middle, top);
                }
                break;
            case 2:
                int middleWorldY = player.worldY + ((player.solidArea.y + player.solidArea.height) / 2);
                int rightWorldX = player.worldX + player.solidArea.x + player.solidArea.width + 10;
                middle = middleWorldY / 64;
                int right = rightWorldX / 64;
                player.mineTile(right, middle);

                middleWorldY = player.worldY + ((player.solidArea.y + player.solidArea.height) / 2);
                rightWorldX = player.worldX + player.solidArea.x + player.solidArea.width + 74;
                middle = middleWorldY / 64;
                right = rightWorldX / 64;
                if (right > 0 && right < 40) {
                    player.mineTile(right, middle);
                }
                break;
            case 3:
                middleWorldX = player.worldX + ((player.solidArea.x + player.solidArea.width) / 2);
                int bottomWorldY = player.worldY + player.solidArea.y + player.solidArea.height + 10;
                middle = middleWorldX / 64;
                int bottom = bottomWorldY / 64;
                player.mineTile(middle, bottom);

                middleWorldX = player.worldX + ((player.solidArea.x + player.solidArea.width) / 2);
                bottomWorldY = player.worldY + player.solidArea.y + player.solidArea.height + 74;
                middle = middleWorldX / 64;
                bottom = bottomWorldY / 64;
                if (bottom < 40) {
                    player.mineTile(middle, bottom);
                }
                break;
            case 4:
                middleWorldY = player.worldY + ((player.solidArea.y + player.solidArea.height) / 2);
                int leftWorldX = player.worldX + player.solidArea.x - 10;
                middle = middleWorldY / 64;
                int left = leftWorldX / 64;
                player.mineTile(left, middle);

                middleWorldY = player.worldY + ((player.solidArea.y + player.solidArea.height) / 2);
                leftWorldX = player.worldX + player.solidArea.x - 74;
                middle = middleWorldY / 64;
                left = leftWorldX / 64;
                if (left > 0 && left < 40) {
                    player.mineTile(left, middle);
                }
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
        return id;
    }

    @Override
    public String getImagePath() {
        return "img/drill.png";
    }
}