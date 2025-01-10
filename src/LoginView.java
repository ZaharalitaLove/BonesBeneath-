import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

/** GUI class for login screen
 */
public class LoginView {
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JFrame errorFrame;
    private String errorMessage = "LOGIN FAILED : The username or password is incorrect.";
    private MainController mainController;
    private LoginController loginController;

    /**
     * Constructor takes in main and login controller instances for program and sets attributes accordingly.
     *
     * @param mc - main controller instance
     * @param lc - login controller instance
     */
    public LoginView(MainController mc, LoginController lc, HubController hc) {
        mainController = mc;
        loginController = lc;
    }


    /**
     * Method handles the GUI and reception of user input into username and password text fields once login button
     * is pressed. If the user did not enter input then the login button does not function. If the username
     * or password is invalid then the user will receive an error message. If the input is valid the information is saved
     * and communicated to the login controller.
     */
    public void displayLoginScreen() {
        Image backgroundImage = new ImageIcon("img/backgrounds/loginViewBackground.png").getImage();
        loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        loginPanel.setLayout(null);

        usernameField = new JTextField("");
        usernameField.setBounds(490, 305, 750, 70);
        usernameField.setFont(new Font("Dialog", Font.BOLD, 35));
        passwordField = new JPasswordField("");
        passwordField.setBounds(490, 450, 750, 70);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 35));

        ImageIcon loginButtonIcon = new ImageIcon("img/loginButton.png");
        Image loginButtonImage = loginButtonIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledLoginButtonIcon = new ImageIcon(loginButtonImage);
        loginButton = new JButton(scaledLoginButtonIcon);
        loginButton.setBounds(605, 600, 300, 100);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                try {
                    loginController.collectUserCredentials(usernameField, passwordField);

                } catch (IOException | SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter both username and password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the back button
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

        // Add components to the login panel
        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(backButton);

        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(loginPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Displays results login results. If login was unsuccessful, error pop-up
     * with error message will be displayed. If login was successful information is communicated to
     * main controller to start game.
     *
     * @param success - whether login was successful or not
     */
    public void displayLoginResults(boolean success) throws SQLException {
        if (!success) {
            showFailOptionPane();
        } else {
            // Instantiate the HubController and call displayHubView
            loginPanel.setVisible(false);
            mainController.showHub();
        }
    }

    private void showFailOptionPane() {
        JPanel failedPanel = new JPanel(new BorderLayout());
        ImageIcon backgroundImage = new ImageIcon("img/loginPopUp.png");
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
        JDialog dialog = optionPane.createDialog("Login Failed");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        tryAgainButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
    public JTextField getUsernameField() {
        return usernameField;
    }
    public JPasswordField getPasswordField() {
        return passwordField;
    }
    public JButton getLoginButton() {
        return loginButton;
    }

    public JFrame getErrorFrame() {
        return errorFrame;
    }

}
