import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

/**
 * Third MiniGame option for when the user comes across a bone in the game
 * User must press the colored buttons in the order that the game displays
 * and for enough time in order to win
 */
public class MiniGameThree {
    private JPanel miniGamePanel;
    private JFrame frame;
    private JButton[] colorButtons;
    private List<Integer> sequence;
    private int currentRound;
    private int playerIndex;
    private final int maxRounds = 5;
    private Runnable onComplete;

    /**
     * Constructor for the MiniGameThree
     * @param onComplete - taking in if the game was completed or not
     */
    public MiniGameThree(Runnable onComplete) {
        this.onComplete = onComplete;
        sequence = new ArrayList<>();
        currentRound = 0;
        playerIndex = 0;
        colorButtons = new JButton[4];
        createInstructionScreen();
    }

    /**
     * Creates the instruction screen with a start button
     */
    private void createInstructionScreen() {
        JFrame instructionFrame = new JFrame("Instructions");
        instructionFrame.setSize(400, 300);
        instructionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionFrame.setLayout(new BorderLayout());
        // Creating the text field for the instructions
        JTextArea instructions = new JTextArea("Congrats on finding a bone! Complete this MiniGame to successfully obtain it. "
                + "In this game you must memorize the pattern that the lights flash in and repeat the pattern as it gets slowly more complicated. "
                + "Press the start button to start.");
        instructions.setWrapStyleWord(true);
        instructions.setLineWrap(true);
        instructions.setEditable(false);
        instructions.setBackground(instructionFrame.getContentPane().getBackground());
        instructions.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Creating the start button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            instructionFrame.dispose();
            createGameFrame();
            init();
        });
        // Adding the instructions
        instructionFrame.add(instructions, BorderLayout.CENTER);
        instructionFrame.add(startButton, BorderLayout.SOUTH);
        instructionFrame.setLocationRelativeTo(null);
        instructionFrame.setVisible(true);
    }

    /**
     * Creates the main game frame and its components
     */
    private void createGameFrame() {
        frame = new JFrame("Memory Game");
        miniGamePanel = new JPanel();
        miniGamePanel.setLayout(new GridLayout(2, 2));
        // Creating the buttons
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new JButton();
            colorButtons[i].setBackground(getColor(i));
            colorButtons[i].setOpaque(true);
            colorButtons[i].setBorderPainted(false);
            final int index = i;
            colorButtons[i].addActionListener(e -> playerGuess(index));
            miniGamePanel.add(colorButtons[i]);
        }
        // Setting up the frame
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.add(miniGamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
    }

    /**
     * Initializing the game
     */
    public void init() {
        sequence.clear();
        currentRound = 0;
        playerIndex = 0;
        nextRound();
    }

    /**
     * Method handles going from one round to the next
     */
    private synchronized void nextRound() {
        currentRound++;
        playerIndex = 0;
        addRandomColor();
        showSequence();
    }

    /**
     * Adding a random color
     */
    private synchronized void addRandomColor() {
        Random rand = new Random();
        sequence.add(rand.nextInt(4));
    }

    /**
     * Method handles the sequence of the buttons flashing
     */
    private void showSequence() {
        new Thread(() -> {
            try {
                for (int index : sequence) {
                    flashButton(index);
                    Thread.sleep(1000);
                }
            } catch (ConcurrentModificationException e) {
                // Handle the exception silently
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Method that handles the flashing button that the user must memorize the order
     * @param index - taking in the index
     */
    private void flashButton(int index) {
        if (colorButtons[index] == null) {
            return;
        }
        Color originalColor = colorButtons[index].getBackground();
        colorButtons[index].setBackground(Color.WHITE);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        colorButtons[index].setBackground(originalColor);
    }

    /**
     * Method displays if the user won or lost the game
     * @param index - taking in the index
     */
    void playerGuess(int index) {
        if (index == sequence.get(playerIndex)) {
            playerIndex++;
            if (playerIndex == sequence.size()) {
                if (currentRound < maxRounds) {
                    nextRound();
                } else {
                    onComplete.run();
                    closeMiniGameScreen();
                }
            }
        } else {
            System.out.println("Sorry, the bone broke");
            onComplete.run();
            closeMiniGameScreen();
        }
    }

    /**
     * Closes the game
     */
    public void closeMiniGameScreen() {
        frame.dispose();
    }

    /**
     * Method gets and returns the color of the button
     * @param index - taking in the index
     * @return - returning the color of the button for the case
     */
    private Color getColor(int index) {
        switch (index) {
            case 0: return Color.RED;
            case 1: return Color.GREEN;
            case 2: return Color.BLUE;
            case 3: return Color.YELLOW;
            default: return Color.BLACK;
        }
    }
}