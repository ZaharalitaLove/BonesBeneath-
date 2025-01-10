import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * HubView class creates the GUI for the hub
 */
public class ShopView {

    JButton toolsButton;
    JButton charactersButton;
    private JButton buyButton;
    private JPanel backgroundPanel;
    private MainController mainController;
    private ShopController shopController;
    private ShopModel shopModel;
    private JLabel selectedImage;
    private JLabel selectedPrice;


    public ShopView(MainController mainController,ShopController shopController) throws SQLException {
        this.mainController = mainController;
        this.shopController = shopController;
    }

    public void displayShopScreen() {
        //getting user info
        this.shopModel = shopController.getShopModel();
        UserProfileModel upm = mainController.getUserProfile();
        ImageIcon backgroundImage = new ImageIcon("img/backgrounds/shopBackground.png");
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());

        // NORTH PANEL
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);

        // BACK AND TITLE PANEL
        JPanel backnTitle = new JPanel(new BorderLayout());
        backnTitle.setOpaque(false);
        JButton backButton = HubView.scaleImageAsButton("img/backButton.png", 100, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainController.showHub();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        JLabel titleLabel = new JLabel(HubView.scaleImageAsIcon("img/shopTitle.png", 800, 70));
        titleLabel.setOpaque(false);
        titleLabel.setBorder(new EmptyBorder(50, 50, 50, 50));
        backnTitle.add(backButton, BorderLayout.WEST);
        backnTitle.add(titleLabel, BorderLayout.CENTER);
        northPanel.add(backnTitle, BorderLayout.NORTH);

        // COINS AND TOGGLE PANEL
        JPanel coinsnToggle = new JPanel(new GridLayout(1, 4));
        coinsnToggle.setBorder(new EmptyBorder(0, 150, 0, 125));
        coinsnToggle.setOpaque(false);
        JLabel user = new JLabel(upm.getUserName());
        user.setFont(new Font("Baskerville Old Face", Font.BOLD, 22));
        user.setOpaque(false);
        user.setBorder(new EmptyBorder(0, 80, 0, 0));
        JLabel coins = new JLabel("Coins: " + upm.getCoinTotal());
        coins.setFont(new Font("Baskerville Old Face", Font.BOLD, 22));
        coins.setOpaque(false);
        coins.setBorder(new EmptyBorder(0, 0, 0, 80));
        toolsButton = HubView.scaleImageAsButton("img/toolsButton.png", 300, 70);
        toolsButton.setContentAreaFilled(false);
        toolsButton.setBorderPainted(false);
        toolsButton.setFocusPainted(false);
        charactersButton = HubView.scaleImageAsButton("img/charactersButton.png", 300, 70);
        charactersButton.setContentAreaFilled(false);
        charactersButton.setBorderPainted(false);
        charactersButton.setFocusPainted(false);
        coinsnToggle.add(user);
        coinsnToggle.add(coins);
        coinsnToggle.add(toolsButton);
        coinsnToggle.add(charactersButton);
        northPanel.add(coinsnToggle, BorderLayout.CENTER);
        backgroundPanel.add(northPanel, BorderLayout.NORTH);

        // CENTER PANEL
        JPanel center = new JPanel(new GridLayout(1, 2));
        center.setOpaque(false);

        // SELECTED PANEL
        JPanel selected = new JPanel(new BorderLayout());
        selected.setOpaque(false);
        selectedImage = new JLabel("");
        selectedImage.setBorder(new EmptyBorder(0, 10, 10, 10));
        selectedImage.setHorizontalAlignment(SwingConstants.CENTER);
        selectedImage.setOpaque(false);
        selectedPrice = new JLabel("");
        selectedPrice.setFont(new Font("Baskerville Old Face", Font.BOLD, 40));
        selectedPrice.setBorder(new EmptyBorder(30, 10, 80, 10));
        selectedPrice.setHorizontalAlignment(SwingConstants.CENTER);
        selectedPrice.setOpaque(false);
        buyButton = HubView.scaleImageAsButton("img/buyButton.png", 300, 80);
        buyButton.setBorder(new EmptyBorder(0, 30, 0, 30));
        buyButton.setOpaque(false);
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imgPath = selectedImage.getName();
                LinkedHashMap<String,int[]> characters = shopController.getSpritesFromModel();

                for(String path: characters.keySet()) {
                    if((imgPath).contains(path)) {
                        int[] info = characters.get(path);
                        int price = info[3];
                        boolean unlocked = false;
                        try {
                            unlocked = shopController.unlockSprite(imgPath,path,price);
                            if(unlocked){
                                coins.setText("Coins: " + upm.getCoinTotal());}

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
                LinkedHashMap<String,int[]> items = shopController.getItemsFromModel();

                for(String itemName: items.keySet()) {
                    if(imgPath.contains(itemName.toLowerCase())) {
                        int[] info = items.get(itemName);
                        int price = info[1];
                        try {
                            boolean purchased = shopController.makePurchase(itemName, price);
                            if(purchased) {
                            coins.setText("Coins: " + upm.getCoinTotal());}

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            }
        });
        selected.add(selectedImage, BorderLayout.CENTER);
        selected.add(selectedPrice, BorderLayout.SOUTH);
        selected.add(buyButton, BorderLayout.NORTH);
        center.add(selected);

        // OPTIONS PANEL
        JPanel options = new JPanel(new GridLayout(2, 2));
        options.setBorder(new EmptyBorder(50, 0, 40, 120));
        options.setOpaque(false);

        toolsButton.addActionListener(e -> {
            showTools(options);
        });
        charactersButton.addActionListener(e -> {
            showCharacters(options);
        });

        center.add(options);
        backgroundPanel.add(center, BorderLayout.CENTER);

        JLabel south = new JLabel(" ");
        south.setOpaque(false);
        backgroundPanel.add(south, BorderLayout.SOUTH);

        JLabel east = new JLabel(" ");
        east.setOpaque(false);
        backgroundPanel.add(east, BorderLayout.EAST);

        JLabel west = new JLabel(" ");
        west.setOpaque(false);
        backgroundPanel.add(west, BorderLayout.WEST);

        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(backgroundPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JPanel createItemPanel(String imagePath, String priceText) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);
        JButton itemButton = HubView.scaleImageAsButton(imagePath, 170, 170);
        itemButton.setHorizontalAlignment(SwingConstants.CENTER);
        itemButton.setContentAreaFilled(false);
        itemButton.setBorderPainted(false);
        itemButton.setFocusPainted(false);
        JLabel itemLabel = new JLabel(priceText);
        itemLabel.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
        itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemLabel.setOpaque(false);

        // Add ActionListener to itemButton to update selectedImage and selectedPrice
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedImage.setIcon(HubView.scaleImageAsIcon(imagePath, 300, 350));
                selectedImage.setName(imagePath);
                selectedPrice.setText(priceText);
            }
        });

        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(itemLabel, BorderLayout.SOUTH);
        itemPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return itemPanel;
    }

    /** Gets information from shop model and creates GUI representation of tools in shop
     *   - JPanel displaying shop items
     * @return - updated JPanel displaying shop items
     */
    public JPanel createCharacterPanel(String imagePath, String priceText, String dataImagePath) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);
        JButton itemButton = HubView.scaleImageAsButton(imagePath, 170, 170);
        itemButton.setHorizontalAlignment(SwingConstants.CENTER);
        itemButton.setContentAreaFilled(false);
        itemButton.setBorderPainted(false);
        itemButton.setFocusPainted(false);
        JLabel itemLabel = new JLabel(priceText);
        itemLabel.setFont(new Font("Baskerville Old Face", Font.BOLD, 24));
        itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
        itemLabel.setOpaque(false);

        JLabel itemData = new JLabel(HubView.scaleImageAsIcon(dataImagePath, 110,123));
        itemData.setHorizontalAlignment(SwingConstants.CENTER);
        itemData.setOpaque(false);


        // Add ActionListener to itemButton to update selectedImage and selectedPrice
        itemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedImage.setIcon(HubView.scaleImageAsIcon(imagePath, 300, 320));
                selectedImage.setName(imagePath);
                selectedPrice.setText(priceText);
            }
        });

        itemPanel.add(itemButton, BorderLayout.CENTER);
        itemPanel.add(itemLabel, BorderLayout.SOUTH);
        itemPanel.add(itemData,BorderLayout.EAST);
        itemPanel.setBorder(new EmptyBorder(20, 10, 20, 0));
        return itemPanel;
    }

    public JPanel showTools(JPanel options) {
        options.removeAll(); // Clear previous options
        toolsButton.setEnabled(false);
        charactersButton.setEnabled(true);

        LinkedHashMap<String,int[]> items = shopModel.getItems();
        for(String itemName : items.keySet()) {
            String imgPath = "img/" + itemName.toLowerCase() + ".png";
            int[] info = items.get(itemName);
            String price = String.valueOf(info[1]);
            options.add(createItemPanel(imgPath, price));
        }
        options.revalidate();
        options.repaint();
        return options;
    }

    /** Gets information from shop model and creates GUI representation of character sprites in shop
     * @param options - JPanel displaying shop characters
     * @return - updated JPanel displaying shop characters
     */
    public JPanel showCharacters(JPanel options) {
        options.removeAll(); // Clear previous options
        charactersButton.setEnabled(false);
        toolsButton.setEnabled(true);

        LinkedHashMap<String,int[]> characters = shopModel.getCharacters();

        for(String path : characters.keySet()){
            int[] info = characters.get(path);
            String price = String.valueOf(info[3]);
            String[] parsedpath = path.split(".png");
            options.add(createCharacterPanel("img"+path, price,"img"+parsedpath[0]+"Data.png"));
        }

        options.revalidate();
        options.repaint();
        return options;
    }


        public void unlockCharacter (UserProfileModel upm, String imgPath, JLabel coins) throws SQLException {
            upm.addToCollectedSprites(upm.getCharacterID(imgPath));
            upm.setCharacter(new ImageIcon(imgPath));
            upm.addOrRemoveCoins(-Integer.parseInt(selectedPrice.getText()));
            coins.setText("Coins: " + upm.getCoinTotal());
            selectedPrice.setText("Free");
        }
    }
