import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Second MiniGame option for when a player discovers a bone
 * Reaction time game:
 * Player has to press the space bar as fast as they can when the screen displays the screen "GO"
 * If they complete it in a fast enough time, they win the game
 */
public class MiniGameTwo {
    public JPanel miniGamePanel;
    public JFrame frame;
    private JLabel instructionLabel;
    private JLabel resultLabel;
    private Image backgroundImage;
    private boolean gameWon;

    /**
     * Constructor for the second MiniGame
     */
    public MiniGameTwo() {
        try {
            backgroundImage = ImageIO.read(new File("img/backgrounds/minigameBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Creating the panel that the game is displayed on
        frame = new JFrame("Quick Reaction Test");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        miniGamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        miniGamePanel.setLayout(new BorderLayout());
        instructionLabel = new JLabel("Wait for 'GO!'...", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 48));
        instructionLabel.setForeground(Color.WHITE);
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        resultLabel.setForeground(Color.WHITE);
        miniGamePanel.add(instructionLabel, BorderLayout.CENTER);
        miniGamePanel.add(resultLabel, BorderLayout.SOUTH);
        frame.add(miniGamePanel);
    }

    /**
     * Method displays the MiniGame screen and sets up the first window
     * @return - returning true
     */
    public void displayMiniGameScreen() {
        frame.setVisible(true);
    }

    /**
     * Displays GO and prompts the user to react
     */
    public void displayGoSignal() {
        instructionLabel.setText("GO!");
        miniGamePanel.revalidate();
        miniGamePanel.repaint();
    }

    /**
     * Method shows the reaction time and the result if they won or lost
     * @param reactionTime - taking in the reaction time
     */
    public void showReactionTime(long reactionTime, Runnable onComplete) {
        frame.dispose();
        gameWon = reactionTime < 450;
        onComplete.run(); // Notify the controller of the result
    }

    /**
     * Method returns if the game was won or not
     * @return - returning if the game was won or not
     */
    public boolean isGameWon() {
        return gameWon;
    }
}