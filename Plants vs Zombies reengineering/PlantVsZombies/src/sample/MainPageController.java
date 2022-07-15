package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Class MainPageController.
 */
public class MainPageController implements Initializable {
    
    /** The main root. */
    @FXML
    private AnchorPane mainRoot;


    /**
     * Exit game.
     *
     * @param event the event
     */
    @FXML
    void exitGame(MouseEvent event) {
        try{
            Main.serialize();
        }
        catch(IOException e){
        }
        System.exit(0);
    }

    /**
     * Initialize.
     *
     * @param url the url
     * @param rb the rb
     */
    /** To unlock all the 5 levels uncomment this line*/
    @Override
    public void initialize(URL url, ResourceBundle rb){

        Main.getDatabase().setMaxLevel(5);
    }

    /**
     * Show almanac.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void showAlmanac(MouseEvent event) throws Exception {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("Almanac.fxml"));
        mainRoot.getChildren().setAll(pane);
    }

    /**
     * Show level menu.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void showLevelMenu(MouseEvent event) throws Exception {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("LevelMenu.fxml"));
        mainRoot.getChildren().setAll(pane);
    }

    /**
     * Show saved games.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void showSavedGames(MouseEvent event) throws Exception{
        AnchorPane pane= FXMLLoader.load(getClass().getResource("loadGame.fxml"));
        mainRoot.getChildren().setAll(pane);
    }

    /**
     * Start game.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void startGame(MouseEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(Main.getDatabase().getMaxLevel(),new DataTable(Main.getDatabase().getMaxLevel()));
        mainRoot.getChildren().setAll(pane);
    }

}
