import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Controller for the MiniGameTwo class
 */
public class MiniGameTwoController {
    private MiniGameTwo miniGameView;
    private long startTime;
    private boolean goSignalShown;

    /**
     * Constructor for the MiniGameTwo controller
     * @param miniGameView - taking in the MiniGameView
     */
    public MiniGameTwoController(MiniGameTwo miniGameView) {
        this.miniGameView = miniGameView;
    }

    /**
     * Method starts the MiniGame
     */
    public void startMiniGame() {
        showInstructions();
    }

    /**
     * Method shows the instructions for the game and starts the game after the start
     * button is pressed and the user understands how to play
     */
    private void showInstructions() {
        JFrame instructionFrame = new JFrame("Instructions");
        instructionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instructionFrame.setSize(600, 400);
        instructionFrame.setLocationRelativeTo(null);
        JPanel instructionPanel = new JPanel(new BorderLayout());
        JLabel instructionsLabel = new JLabel("<html><center>Congratulations on finding a bone!<br>" +
                "Complete this MiniGame in order to successfully obtain it.<br>" +
                "You must press down on the spacebar key as fast as you can after the 'GO!' screen appears.<br>" +
                "Press Start to begin.</center></html>", SwingConstants.CENTER);
        instructionPanel.add(instructionsLabel, BorderLayout.CENTER);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            instructionFrame.dispose();
            startGame();
        });
        instructionPanel.add(startButton, BorderLayout.SOUTH);
        instructionFrame.add(instructionPanel);
        instructionFrame.setVisible(true);
    }

    /**
     * Method starts the MiniGame
     */
    void startGame() {
        miniGameView.displayMiniGameScreen();
        miniGameView.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (goSignalShown && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    long reactionTime = System.currentTimeMillis() - startTime;
                    miniGameView.showReactionTime(reactionTime, () -> {
                        // Pass the result to the bone() method here
                        if (miniGameView.isGameWon()) {
                            System.out.println("Successfully collected the bone!");
                        } else {
                            System.out.println("Sorry, the bone broke.");
                        }
                    });
                }
            }
        });
        /*
         * Setting the random delay until the game starts
         */
        new Thread(() -> {
            try {
                Random rand = new Random();
                int delay = rand.nextInt(3000) + 1000;
                Thread.sleep(delay);
                goSignalShown = true;
                miniGameView.displayGoSignal();
                startTime = System.currentTimeMillis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}