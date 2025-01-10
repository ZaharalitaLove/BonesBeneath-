import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class displays a MiniGame in which the user must complete it in a certain amount of time in order
 * to either win or lose the game
 * This will determine whether the user is able to collect a bone or not
 * User must click buttons labeled with the numbers 1-16 fast enough to win
 */
public class MiniGameOne {
    private JPanel gamePanel;
    List<JButton> buttons;
    int currentNumber = 1;
    private MiniGameResultListener resultListener;
    Timer timer;
    private long startTime;

    /**
     * Constructor that initializes the MiniGame
     */
    public MiniGameOne() {
        initializeGame();
    }

    /**
     * Method initializes the MiniGame
     */
    private void initializeGame() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(4, 4, 5, 5));
        // Creating the buttons 1-16
        buttons = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(new ButtonClickListener());
            buttons.add(button);
        }
        // Randomly shuffle the buttons and add them to the panel
        Collections.shuffle(buttons);
        for (JButton button : buttons) {
            gamePanel.add(button);
        }
        // Initialize the timer for 13000 milliseconds (13 seconds) - must complete in under 13 seconds
        timer = new Timer(13000, new TimerListener());
        timer.setRepeats(false);
    }

    /**
     * Method returns the game panel that is then added to the frame
     * @return - returning the game panel
     */
    public JPanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Method sets a listener that gets notified when the game is complete
     * @param listener
     */
    public void setCompletionListener(MiniGameResultListener listener) {
        this.resultListener = listener;
    }

    /**
     * Method sets teh colors of the buttons
     * @param r - number of red color
     * @param g - number of green color
     * @param b - number of blue color
     */
    public void setButtonColors(int r, int g, int b) {
        Color bgColor = new Color(r, g, b);
        for (JButton button : buttons) {
            button.setBackground(bgColor);
            button.setOpaque(true);
        }
    }

    /**
     * Method starts the game and the timer
     */
    public void startGame() {
        startTime = System.currentTimeMillis();
        timer.start();
    }

    /**
     * Method adds a listener for when the buttons are clicked
     */
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            int clickedNumber = Integer.parseInt(clickedButton.getText());
            if (clickedNumber == currentNumber) {
                clickedButton.setEnabled(false);
                currentNumber++;
            }
            if (currentNumber > 16) {
                // Game complete
                timer.stop();
                long elapsedTime = System.currentTimeMillis() - startTime;
                boolean won = elapsedTime <= 13000;
                if (resultListener != null) {
                    resultListener.onMiniGameComplete(won);
                }
            }
        }
    }

    /**
     * Method handles the timer listener and the time limit
     */
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }
}