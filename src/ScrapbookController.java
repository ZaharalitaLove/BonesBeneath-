public class ScrapbookController {
    private MainController mainController;
    private ScrapbookView scrapbookView;
    public ScrapbookController(MainController mc) {
        this.mainController = mc;
        scrapbookView = new ScrapbookView(mainController, this);
    }
    public void setBoneStage(){

    }
    public void displayScrapbookView() {
        scrapbookView.displayScrapbook();
    }

}
