import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/** GUI class for profile setup screen
 */
public class ProfileSetUpView {
    private JPanel profileSetupPanel;
    private String errorMessage = "USERNAME IS TAKEN, PLEASE TRY AGAIN";
    private MainController mainController;
    private ProfileSetUpController profileSetUpController;
    private  JTextField usernameField;
    private  JPasswordField passwordField;
    private JButton createProfileButton;

    /** Constructor takes in main and profile setup controller instances for program and sets attributes accordingly.
     *
     * @param mc - main controller instance
     * @param pc -  profile setup controller instance
     */
    public ProfileSetUpView(MainController mc, ProfileSetUpController pc) {
        mainController = mc;
        profileSetUpController = pc;
    }

    /** Method handles the GUI and reception of user input into username and password text fields once create profile button
     *  is pressed. If the user did not enter input then the login button does not function. If the username
     *  is invalid/taken then the user will receive an error message. If the input is valid the information is saved
     *  and communicated to the profile setup controller.
     */
    public void displayAccountCreationScreen() {
        Image backgroundImage = new ImageIcon("img/backgrounds/profileSetUpViewBackground.png").getImage();
        profileSetupPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        profileSetupPanel.setLayout(null);
        usernameField = new JTextField("");
        usernameField.setFont(new Font("Dialog", Font.BOLD, 35));
        usernameField.setBounds(490, 305, 750, 70);
        passwordField = new JPasswordField("");
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 35));
        passwordField.setBounds(490, 450, 750, 70);
        ImageIcon createProfileButtonIcon = new ImageIcon("img/createAccountButton.png");
        Image createProfileButtonImage = createProfileButtonIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledcreateProfileButtonIcon = new ImageIcon(createProfileButtonImage);
        createProfileButton = new JButton(scaledcreateProfileButtonIcon);
        createProfileButton.setBounds(605, 600, 300, 100);
        createProfileButton.setContentAreaFilled(false);
        createProfileButton.setBorderPainted(false);
        createProfileButton.setFocusPainted(false);

        createProfileButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!username.isBlank() && !password.isBlank()) {
                try {
                    profileSetUpController.collectNewUserInfo(usernameField, passwordField);
                } catch (IOException | SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "An error occurred while setting up the profile: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please fill in both username and password fields.",
                        "Incomplete Information",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        ImageIcon backButtonIcon = new ImageIcon("img/backButton.png");
        Image backButtonImage = backButtonIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        ImageIcon scaledBackButtonIcon = new ImageIcon(backButtonImage);
        JButton backButton = new JButton(scaledBackButtonIcon);
        backButton.setBounds(10, 10, 100, 50);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            // Add action to go back to the previous screen
            mainController.displayMainView();
        });
        profileSetupPanel.add(backButton);
        profileSetupPanel.add(usernameField);
        profileSetupPanel.add(passwordField);
        profileSetupPanel.add(createProfileButton);
        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(profileSetupPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /** Displays results profile setup results. If setup was unsuccessful, error pop-up
     * with error message will be displayed. If setup was successful information is communicated to
     * main controller to start game.
     *
     * @param success - whether profile setup was successful or not
     */
    public void displayAccountCreationResult(boolean success) throws IOException, SQLException {
        if (!success) {
            showFailOptionPane();
        } else {
            profileSetupPanel.setVisible(false);
            mainController.showHub();
        }
    }

    public JPanel getProfileSetupPanel() {
        return profileSetupPanel;
    }


    private void showFailOptionPane() {
        JPanel failedPanel = new JPanel(new BorderLayout());
        ImageIcon backgroundImage = new ImageIcon("img/createAccountPopUp.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        ImageIcon scaledBackgroundImage = new ImageIcon(backgroundImage.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
        backgroundLabel.setIcon(scaledBackgroundImage);
        backgroundLabel.setBorder(null);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.setPreferredSize(new Dimension(599, 399));
        JButton tryAgainButton = new JButton(HubView.scaleImageAsIcon("img/tryAgainButton.png",350,100));
        tryAgainButton.setBorder(new EmptyBorder(0,0,50,0));
        backgroundLabel.add(tryAgainButton, BorderLayout.SOUTH);
        failedPanel.add(backgroundLabel, BorderLayout.CENTER);
        tryAgainButton.setOpaque(false);
        tryAgainButton.setContentAreaFilled(false);
        tryAgainButton.setBorderPainted(false);
        failedPanel.setOpaque(true);
        JOptionPane optionPane = new JOptionPane(failedPanel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JDialog dialog = optionPane.createDialog("Create Account Failed");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        tryAgainButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public JTextField getUsernameField() {
        return usernameField;
    }
    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public JButton getCreateProfileButton() {
        return createProfileButton;
    }

}
