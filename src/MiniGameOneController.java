import javax.swing.*;
import java.awt.*;

/**
 * Class controls the MiniGame by recording the time, and the buttons pressed
 */
public class MiniGameOneController {
    JFrame frame;
    private MiniGameOne miniGame;

    /**
     * Constructor that initializes the JFrame
     */
    public MiniGameOneController() {
        frame = new JFrame("Mini Game - Collect the Bone");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
    }
    // Start the mini-game and show instructions
    public void startMiniGame(MiniGameResultListener listener) {
        SwingUtilities.invokeLater(() -> {
            showInstructions(listener);
        });
    }

    /**
     * Method displays the instruction screen and starts the game when the start button is pressed
     * @param listener - taking in the action listener
     */
    private void showInstructions(MiniGameResultListener listener) {
        // Clear any previous content in the frame
        frame.getContentPane().removeAll();
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BorderLayout());
        // Instruction text that is displayed before the game begins
        JLabel instructionsLabel = new JLabel("<html><center>Congratulations, you found a bone!<br>" +
                "Complete this Mini Game in order to successfully obtain the bone.<br>" +
                "When you press the start button, a screen will appear with 16 randomly ordered buttons.<br>" +
                "Click the buttons in order from 1 to 16 as fast as you can.</center></html>", SwingConstants.CENTER);
        instructionsPanel.add(instructionsLabel, BorderLayout.CENTER);
        // Start button
        JButton startButton = new JButton("Start Mini Game");
        startButton.addActionListener(e -> {
            startMiniGamePlay(listener);
        });
        instructionsPanel.add(startButton, BorderLayout.SOUTH);
        frame.add(instructionsPanel);
        frame.setVisible(true);
    }

    /**
     * Method starts the actual MiniGame and the timer
     * @param listener - taking in the action listener
     */
    private void startMiniGamePlay(MiniGameResultListener listener) {
        // Clear the instructions screen
        frame.getContentPane().removeAll();
        // Initialize the mini-game
        miniGame = new MiniGameOne();
        miniGame.setButtonColors(255, 235, 249);
        // Start the game and timer
        miniGame.startGame();
        frame.add(miniGame.getGamePanel());
        miniGame.setCompletionListener(won -> {
            listener.onMiniGameComplete(won);
            frame.dispose();
        });
        frame.revalidate();
        frame.repaint();
    }
}