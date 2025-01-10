import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * HubView class creates the GUI for the hub
 */
public class HubView {
    private int gold = 150;
    private boolean inventoryOpen;
    private String stats = "Level: 4";
    private String characterInfo;
    private Image backgroundImage;
    private MainController mainController;
    private HubController hubController;
    private JPanel backgroundPanel;
    private JPanel buttons;
    private UserProfileModel currentUserProfile;
    private ArrayList<Item> inventory;
    private int[] iven = new int[4];


    public HubView(MainController mc, HubController hc) {
        mainController = mc;
        hubController = hc;

    }

    /**
     * Method that sets and stores the character information
     * @param characterInfo - taking in the information
     */
    public void setCharacterInfo(String characterInfo) {
        this.characterInfo = characterInfo;
    }

    /**
     * Method sets the amount of gold
     * @param gold
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Method sets the stats of the player
     * Can include level, progress bar, etc.
     * @param stats - taking in the stats
     */
    public void setStats(String stats) {
        this.stats = stats;
    }

    public void displayHubScreen() throws SQLException {
        ShopController shopController = mainController.getShopController();
        ShopModel shopModel = shopController.getShopModel();
        currentUserProfile = mainController.getUserProfile();
        shopModel.setItems();
        shopModel.setCharacters();
        shopModel.setItemsBought();
        shopModel.setCharactersUnlocked();
        ArrayList<Item> inventory = currentUserProfile.getInventory();
        backgroundImage = new ImageIcon("img/backgrounds/hubBackground.png").getImage();

        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        // NORTH PANEL
        JPanel northPanel = new JPanel(new BorderLayout());

        // Inventory panel
        JPanel inventoryPanel = new JPanel(new GridLayout(2,4));
        //TODO toggle inventoryPanel visibility on the screen to JButton backpack Action Listener

        for (int i = 0; i < iven.length; i++) {
            JLabel itemLabel;
            if (i < inventory.size() && inventory.get(i) != null) {
                Item item = inventory.get(i);
                ImageIcon itemIcon = new ImageIcon(item.getImagePath()); // Assuming Item class has getImagePath() method
                Image itemImg = itemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                itemLabel = new JLabel(new ImageIcon(itemImg));
            } else {
                // Placeholder for empty inventory slots
                itemLabel = new JLabel();
            }
            itemLabel.setBorder(new EmptyBorder(40, 0, 0, 0));
            inventoryPanel.add(itemLabel);
            itemLabel.setOpaque(false);
        }

        inventoryPanel.setOpaque(false);
        inventoryPanel.setVisible(false);
        inventoryPanel.setBorder(new EmptyBorder(0,80,0,880));
        northPanel.add(inventoryPanel, BorderLayout.CENTER);
        // Backpack
        ImageIcon backpackIcon = new ImageIcon("img/backpack.png");
        Image backpackImg = backpackIcon.getImage();
        Image scaledBackpackImg = backpackImg.getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        JButton backpack = new JButton(new ImageIcon(scaledBackpackImg));
        backpack.setContentAreaFilled(false);
        backpack.setBorderPainted(false);
        backpack.setFocusPainted(false);
        backpack.addActionListener(e -> {
            if (inventoryOpen) {
                backgroundImage = new ImageIcon("img/backgrounds/hubBackground.png").getImage();
            } else {
                backgroundImage = new ImageIcon("img/backgrounds/inventoryBackground.png").getImage();
            }
            inventoryOpen = !inventoryOpen;
            inventoryPanel.setVisible(inventoryOpen);
            backgroundPanel.repaint();
        });

        northPanel.add(backpack, BorderLayout.WEST);
        northPanel.setOpaque(false);
        backgroundPanel.add(northPanel, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        centerPanel.setOpaque(false);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // Left Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);

        // Character Panel
        JPanel characterPanel = new JPanel(new GridLayout(1,2));
        characterPanel.setBorder(new EmptyBorder(0,0,0,150));
        characterPanel.setOpaque(false);
        JLabel character = new JLabel(scaleImageAsIcon("img" + currentUserProfile.getCharacter(),260,320));
        characterPanel.add(character);

        // Info Panel
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        JLabel username = new JLabel(currentUserProfile.getUserName());
        username.setFont(new Font("Baskerville Old Face", Font.BOLD, 35));
        username.setBorder(new EmptyBorder(50,0,0,0));
        JLabel coins = new JLabel("Coins: " + currentUserProfile.getCoinTotal());
        coins.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
        JLabel bonestage = new JLabel("Bone Stage: " + currentUserProfile.getBones());
        bonestage.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
        bonestage.setBorder(new EmptyBorder(0,0,135,0));
        infoPanel.add(username, BorderLayout.NORTH);
        infoPanel.add(coins, BorderLayout.CENTER);
        infoPanel.add(bonestage, BorderLayout.SOUTH);
        characterPanel.add(infoPanel);
        leftPanel.add(characterPanel, BorderLayout.SOUTH);
        centerPanel.add(leftPanel);

        // Right Panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        ImageIcon playIcon = new ImageIcon("img/playButton1.png");
        Image playImg = playIcon.getImage();
        Image scaledplayImg = playImg.getScaledInstance(250, 80, Image.SCALE_SMOOTH);
        JButton playButton = new JButton(new ImageIcon(scaledplayImg));
        playButton.setBorder(new EmptyBorder(150,0,0,0));
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);

        ImageIcon shopIcon = new ImageIcon("img/shopButton.png");
        Image shopImg = shopIcon.getImage();
        Image scaledshopImg = shopImg.getScaledInstance(250, 80, Image.SCALE_SMOOTH);
        JButton shopButton = new JButton(new ImageIcon(scaledshopImg));
        shopButton.setContentAreaFilled(false);
        shopButton.setBorderPainted(false);
        shopButton.setFocusPainted(false);

        ImageIcon scrapbookIcon = new ImageIcon("img/scrapbookButton.png");
        Image scrapbookImg = scrapbookIcon.getImage();
        Image scaledscrapbookImg = scrapbookImg.getScaledInstance(250, 80, Image.SCALE_SMOOTH);
        JButton scrapbookButton = new JButton(new ImageIcon(scaledscrapbookImg));
        scrapbookButton.setBorder(new EmptyBorder(0,0,150,0));
        scrapbookButton.setContentAreaFilled(false);
        scrapbookButton.setBorderPainted(false);
        scrapbookButton.setFocusPainted(false);
        scrapbookButton.addActionListener(e -> {
            mainController.showScrapbook();
        });

        shopButton.addActionListener(e -> {
            try {
                mainController.displayShop();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        playButton.addActionListener(e -> {
            try {
                mainController.initiateGame();
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
            showControlsScreen();
        });


        rightPanel.add(playButton, BorderLayout.NORTH);
        rightPanel.add(shopButton, BorderLayout.CENTER);
        rightPanel.add(scrapbookButton, BorderLayout.SOUTH);
        centerPanel.add(rightPanel);
        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(backgroundPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Method to disable the shop and game buttons in the hub
     */
    public void disableHubButtons() {
        Component[] components = buttons.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                component.setEnabled(false);
            }
        }
    }

    public static ImageIcon scaleImageAsIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public static JButton scaleImageAsButton(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JButton(new ImageIcon(scaledImg));
    }

    public JPanel getHubPanel() {
        return backgroundPanel;
    }

    public static JButton createImageButton(String imagePath, int width, int height, int paddingBottom) {
        ImageIcon buttonIcon = new ImageIcon(imagePath);
        Image buttonImage = buttonIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledButtonIcon = new ImageIcon(buttonImage);


        JButton button = new JButton(scaledButtonIcon);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 0, paddingBottom, 0));

        return button;
    }

    public void showControlsScreen() {
        // Create the main frame
        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();

        // Background image panel for controls screen
        Image controlsBackgroundImage = new ImageIcon("img/backgrounds/controlsBackground.png").getImage();
        JPanel controlsBackgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(controlsBackgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        controlsBackgroundPanel.setLayout(new BorderLayout());

        // Back button (top left corner)
        JButton backButton = createImageButton("img/backButton.png", 100, 50, 80);
        backButton.addActionListener(e -> {
            try {
                displayHubScreen();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);
        controlsBackgroundPanel.add(backButtonPanel, BorderLayout.WEST);

        // Play button (top right corner)
        JButton playButton = createImageButton("img/playButton.png", 150, 140, 80);
        playButton.addActionListener(e -> {
            try {
                controlsBackgroundPanel.setVisible(false);
                mainController.initiateGame();
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel playButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        playButtonPanel.setOpaque(false);
        playButtonPanel.add(playButton);
        controlsBackgroundPanel.add(playButtonPanel, BorderLayout.EAST);

        // Add controls panel to game frame
        gameFrame.add(controlsBackgroundPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}