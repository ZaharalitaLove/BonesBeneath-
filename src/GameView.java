import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * an extention of Jpanel that displays the game, this class has the main display function which,
 * calls all other objects that have display functions.
 */
public class GameView extends JPanel{
    private int screenWidth = 1024;
    private int screenHeight = 800;
    private GameController gc;
    private GameUI ui;
    private GamePlayer player;
    private Entity enemy;
    private TileManager tileManager;

    public GameView( KeyBoardHandler keyH) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        // listener to rescale the players camera on window resize
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                // reset the varibles in player that influence the scope of the camera
                screenWidth = getWidth();
                screenHeight = getHeight();
                int oldcentx = player.getCenterX();
                int oldcenty = player.getCenterY();
                player.setScreenX((screenWidth/2)-48);
                player.setScreenY((screenHeight/2)-48);
                player.setCenterX(player.getScreenX());
                player.setCenterY(player.getScreenY());
                player.setCameraboundX(player.getScreenX());
                player.setCameraboundY(player.getScreenY());
                player.setCenterWorldX(player.getCenterWorldX() + ( player.getCenterX() - oldcentx));
                player.setCenterWorldY(player.getCenterWorldY() + ( player.getCenterY() - oldcenty));
                //bring camera back to game world bounds if the resize loads area outside the level
                if(player.getCenterWorldX() < player.getCameraboundX()) {
                    player.setCenterWorldX(player.getCameraboundX());
                }else if(player.getCenterWorldX() > (3744-player.getCameraboundX())) {
                    player.setCenterWorldX(3744-player.getCameraboundX());
                }
                if(player.getCenterWorldY() < player.getCameraboundY()) {
                    player.setCenterWorldY(player.getCameraboundY());
                }else if(player.getCenterWorldY() > (3744-player.getCameraboundY())) {
                    player.setCenterWorldY(3744-player.getCameraboundY());
                }
                repaint();
                ui.changedWindow(screenWidth);



            }
        });
    }

    public void setEnemy(Entity enemy){
        this.enemy = enemy;
    }

    public void setUi(GameUI ui){
        this.ui = ui;
    }
    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }


    public void setPlayer(GamePlayer gp){
        player = gp;
    }

    public void setTileManager(TileManager tileManager){
        this.tileManager = tileManager;
    }


    // the master paint compenet that calls all others, draw/paintComponent functions
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.drawEntity(g2);
        if(enemy.isEnemyAlive()){
        enemy.drawEntity(g2);}
        ui.draw(g2);

        g2.dispose();
    }

}



