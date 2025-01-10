import javax.swing.*;
import java.sql.SQLException;

public class HubController {
    private HubView hubView;
    private MainController mainController;


    public HubController(MainController mc) {
        this.mainController = mc;
        hubView = new HubView(mainController, this);
    }

    public void setCharacterInfo(String characterInfo) {
        hubView.setCharacterInfo(characterInfo);
    }

    public void setGold(int gold) {
        hubView.setGold(gold);
    }

    public void setStats(String stats) {
        hubView.setStats(stats);
    }

    public void displayHubView() throws SQLException {
        hubView.displayHubScreen();
    }

    public JPanel getHubViewPanel(){
        return hubView.getHubPanel();
    }
    public HubView getHubView() {
        return hubView;
    }
}
