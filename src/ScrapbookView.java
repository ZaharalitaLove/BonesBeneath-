import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ScrapbookView {
    private MainController mainController;
    private ScrapbookController scrapbookController;
    private Image backgroundImage;
    private ImageIcon dinoIcon;
    private UserProfileModel currentUserProfile;

    public ScrapbookView(MainController mc, ScrapbookController sc) {
        mainController = mc;
        scrapbookController = sc;
    }

    public void displayScrapbook() {
        currentUserProfile = mainController.getUserProfile();
        // Create the scrapbook panel without a layout manager for absolute positioning
        JPanel scrapbook = new JPanel();
        scrapbook.setLayout(null);
        scrapbook.setOpaque(false);

        // Load the dinosaur image and set its position on the left
        updateBoneStage();
        int stage = currentUserProfile.getBones();
        String stagestr = Integer.toString(stage);
        dinoIcon = new ImageIcon("img/boneStages/" + stagestr + ".png");
        Image dinoImage = dinoIcon.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH);  // Scale the image
        ImageIcon scaledDinoLabelIcon = new ImageIcon(dinoImage);
        JLabel scaledDinoImageLabel = new JLabel(scaledDinoLabelIcon);
        scaledDinoImageLabel.setBounds(330, 250, 900, 500);
        scrapbook.add(scaledDinoImageLabel);
        // Create the back button and position it in the top-left corner
        ImageIcon backbuttonIcon = new ImageIcon("img/backButton.png");
        Image buttonImage = backbuttonIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon = new ImageIcon(buttonImage);
        JButton backButton = new JButton(scaledButtonIcon);
        backButton.setBounds(10, 10, 100, 50);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            try {
                mainController.showHub();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        scrapbook.add(backButton);

        // Create a panel for the background image
        backgroundImage = new ImageIcon("img/backgrounds/scrapbookBackground.png").getImage();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        // Add scrapbook panel on top of the background panel
        backgroundPanel.add(scrapbook);
        scrapbook.setBounds(0, 0, 1510, 900);
        // Set up the game frame
        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(backgroundPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void updateBoneStage() {
        UserProfileModel upm = mainController.getUserProfile();
        dinoIcon = new ImageIcon("img/boneStages/" +upm.getBones()+".png");
        // Logic to handle bone stage progress here
    }

    public ImageIcon getImage(){
        return dinoIcon;
    }
}