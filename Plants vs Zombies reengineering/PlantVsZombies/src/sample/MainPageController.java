package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The Class MainPageController.
 */
public class MainPageController implements Initializable {
    
    /** The main root. */
    @FXML
    private AnchorPane mainRoot;

    /** The start game. */
    @FXML
    private Button startGame;

    /** The start game 1. */
    @FXML
    private Button startGame1;

    /** The startgame. */
    @FXML
    private ImageView startgame;

    /** The exit game. */
    @FXML
    private ImageView exitGame;

    /** The select level. */
    @FXML
    private ImageView selectLevel;

    /** The load game. */
    @FXML
    private ImageView loadGame;

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
            //System.out.println("Could not save the progress :(");
        }
//        System.exit(0);
    }

    /**
     * Initialize.
     *
     * @param url the url
     * @param rb the rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //To unlock all the 5 levels uncomment this line
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
