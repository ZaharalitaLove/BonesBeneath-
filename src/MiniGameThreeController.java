/**
 * Controller for the MiniGameThree class
 */
public class MiniGameThreeController {
    private MiniGameThree miniGameView;

    /**
     * Constructor for the MiniGameThreeController
     * @param miniGameView - taking in the GUI view
     */
    public MiniGameThreeController(MiniGameThree miniGameView) {
        this.miniGameView = miniGameView;
    }

    /**
     * Displaying the MiniGame screen (now handled by the constructor)
     */
    public void startMiniGameThree() {
        miniGameView.init();
    }
}