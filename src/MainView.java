
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

/** GUI class for opening screens
 */

public class MainView {
    private JFrame gameFrame;
    private JPanel backgroundPanel;
    private JPanel optionPanel;
    private MainController mainController;
    private JButton playButton;
    private JButton loginButton;
    private JButton signUpButton;

    private Image backgroundImage;

    /** Constructor takes in the program's main controller instance and
     * instantiates the game frame
     * @param mc - main controller instance
     */
    public MainView(MainController mc) {
        mainController = mc;
        gameFrame = new JFrame();
    }

    /** Method handles the GUI and reception of user interaction with play button
     *  for the first opening screen.
     *
     */
    public void displayOpeningScreen() {
        backgroundImage = new ImageIcon("img/backgrounds/mainViewBackground.png").getImage();
        // Play Button
        ImageIcon playButtonIcon = new ImageIcon("img/play.png");
        Image playButtonImage = playButtonIcon.getImage().getScaledInstance(345, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledPlayButtonIcon = new ImageIcon(playButtonImage);
        playButton = new JButton(scaledPlayButtonIcon);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);
        // Background Image
        JPanel playButtonPanel = new JPanel(new GridBagLayout());
        playButtonPanel.setOpaque(false);
        playButtonPanel.add(playButton);

        // Background Image
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(playButtonPanel, BorderLayout.CENTER);
        // Change contentpane  when play button is clicked
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.getContentPane().removeAll();
                onPlayButtonClick();
            }
        });

        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(backgroundPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        WindowListener windowListener = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                gameFrame.dispose();
            }
            //closing database connection when window closed
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    mainController.closeDatabse();
                    System.exit(0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        };
        gameFrame.addWindowListener(windowListener);
        gameFrame.setVisible(true);
    }

    /** Method handles the GUI and reception of user interaction with the login/signup buttons
     * for the second opening screen. Saves information and communicates back to the main controller
     * to display appropriate screen.
     *
     */
    private void onPlayButtonClick() {
        Image backgroundImage = new ImageIcon("img/backgrounds/mainViewBackground2.png").getImage();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        optionPanel = new JPanel(new GridLayout(1, 2));
        optionPanel.setOpaque(false);
        ImageIcon loginButtonIcon = new ImageIcon("img/loginButton.png");
        Image loginButtonImage = loginButtonIcon.getImage().getScaledInstance(430, 220, Image.SCALE_SMOOTH);
        ImageIcon scaledLoginButtonIcon = new ImageIcon(loginButtonImage);
        loginButton = new JButton(scaledLoginButtonIcon);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new EmptyBorder(170, 180, 0, 0));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optionPanel.setVisible(false);
                mainController.requestLogin();
            }
        });
        ImageIcon signupButtonIcon = new ImageIcon("img/createAccountButton.png");
        Image signupButtonImage = signupButtonIcon.getImage().getScaledInstance(430, 220, Image.SCALE_SMOOTH);
        ImageIcon scaledSignupButtonIcon = new ImageIcon(signupButtonImage);
        signUpButton = new JButton(scaledSignupButtonIcon);
        signUpButton.setContentAreaFilled(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(new EmptyBorder(170, 0, 0, 280));
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    optionPanel.setVisible(false);
                    mainController.requestProfileSetup();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        optionPanel.add(loginButton);
        optionPanel.add(signUpButton);
        backgroundPanel.add(optionPanel, BorderLayout.CENTER);


        JFrame gameFrame = mainController.getGameFrame();
        gameFrame.getContentPane().removeAll();
        gameFrame.add(backgroundPanel);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        gameFrame.setSize(1510, 900);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JFrame getFrame() {
        return gameFrame;
    }
    public JPanel getBackgroundPanel() {
        return backgroundPanel;
    }
    public JPanel getOptionPanel() {
        return optionPanel;
    }
    public JButton getPlayButton() {
        return playButton;
    }
    public JButton getLoginButton() {
        return loginButton;
    }
    public JButton getSignUpButton() {
        return signUpButton;
    }
}