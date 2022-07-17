package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class GameMenuController.
 */
public class GameMenuController {
    
    /** The Game play root. */
    @FXML
    private AnchorPane GamePlayRoot;

    /** The Game menu root. */
    @FXML
    private AnchorPane GameMenuRoot;

    /** The return to main menu button. */
    @FXML
    private ImageView returnToMainMenuButton;

    /** The save game button. */
    @FXML
    private ImageView saveGameButton;

    /** The restart game button. */
    @FXML
    private ImageView restartGameButton;

    /** The Game menu message. */
    @FXML
    private Label GameMenuMessage;

    /** The level number. */
    @FXML
    private int levelNumber;
    
    /** The data. */
    private DataTable data;
    
    /** The all plants. */
    public static List<Plant> allPlants=null;
    
    /** The all mowers. */
    private static List<LawnMower> allMowers;
    
    /** The sun count. */
    private static int sunCount;
    
    /** The all zombies. */
    private static List<Zombie> allZombies;
    
    /** The time. */
    private static double time;
    
    /** The zombie list 1. */
    private static ArrayList<Integer> zombieList1;
    
    /** The zombie list 2. */
    private static ArrayList<Integer> zombieList2;

    /**
     * Inits the data.
     *
     * @param gamePlayRoot the game play root
     * @param levelNumber the level number
     * @param d the d
     * @param sCount the s count
     * @param allPlant the all plant
     * @param allZombie the all zombie
     * @param allLawnMowers the all lawn mowers
     * @param timeElapsed the time elapsed
     * @param zL1 the z L 1
     * @param zL2 the z L 2
     */
    @FXML
    public void initData(AnchorPane gamePlayRoot, int levelNumber, DataTable d, int sCount, List<Plant> allPlant, List<Zombie> allZombie, List<LawnMower> allLawnMowers, double timeElapsed, ArrayList<Integer> zL1, ArrayList<Integer> zL2){
        this.GamePlayRoot=gamePlayRoot;
        this.levelNumber=levelNumber;
        this.data=d;
        sunCount=sCount;
        allPlants=allPlant;
        allZombies=allZombie;
        allMowers=allLawnMowers;
        time=timeElapsed;
        zombieList1 = zL1;
        zombieList2 = zL2;
    }

    /**
     * Restart game.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void restartGame(MouseEvent event) throws IOException {
        GamePlayController.gameStatus = false;
        GamePlayController.endAnimations();
        //System.out.println("restart called");
        Stage stage = (Stage) restartGameButton.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane game=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        data=new DataTable(levelNumber);
        controller.initData(levelNumber,data);

        GamePlayRoot.getChildren().setAll(game);
    }

    /**
     * Save game.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void saveGame(MouseEvent event) throws IOException {
        GamePlayController.gameStatus = false;
        GameMenuMessage.setText("Game Saved!");
        data.update(levelNumber,sunCount, allPlants,allZombies,allMowers, time, zombieList1, zombieList2, LevelMenuController.status);
        Main.getDatabase().removeData(data);
        data.saveGame();
        Main.getDatabase().setMaxLevel(levelNumber);

    }

    /**
     * Show main menu.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void showMainMenu(MouseEvent event) throws IOException {
        GamePlayController.gameStatus = false;
        GamePlayController.endAnimations();
        AnchorPane pane= FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        GamePlayRoot.getChildren().setAll(pane);
        Stage stage = (Stage) restartGameButton.getScene().getWindow();
        stage.close();

    }

}

