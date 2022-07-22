package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;


/**
 * The Class EndGameController.
 */
public class EndGameController {

    /** The end game. */
    @FXML
    private AnchorPane endGame;

    /** The zombies ate your brains. */
    @FXML
    private ImageView zombiesAteYourBrains;

    /** The you ate zombies brains. */
    @FXML
    private ImageView youAteZombiesBrains;

    /** The plant image. */
    @FXML
    private ImageView plantImage;

    /** The plant name. */
    @FXML
    private Label plantName;

    /** The you won. */
    @FXML
    private ImageView youWon;

    /** The next level button. */
    @FXML
    private ImageView nextLevelButton;

    /** The main menu button. */
    @FXML
    private Button mainMenuButton;

    /** The l. */
    private int l;
    
    /** The game win. */
    private boolean gameWin;

    /**
     * Go to main menu.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void goToMainMenu(MouseEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        endGame.getChildren().setAll(pane);
    }

    /**
     * Go to next level.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void goToNextLevel(MouseEvent event) throws IOException{
        AnchorPane pane= FXMLLoader.load(getClass().getResource("LevelMenu.fxml"));
        endGame.getChildren().setAll(pane);
    }

    /**
     * Initialize.
     */
    @FXML
    public void initialize(){
        zombiesAteYourBrains.setVisible(false);
        youWon.setVisible(false);
        nextLevelButton.setVisible(false);
        nextLevelButton.setDisable(true);
        plantImage.setVisible(false);
        youAteZombiesBrains.setVisible(false);
        plantName.setVisible(false);
    }
    
    /**
     * Inits the data.
     *
     * @param levelNumber the level number
     * @param gameWin the game win
     * @param d the d
     */
    @FXML
    public void initData(int levelNumber, boolean gameWin,DataTable d){
        Main.getDatabase().removeData(d);
        if (gameWin==false){ zombiesAteYourBrains.setVisible(true); }
        else{
            if(levelNumber==5){ youAteZombiesBrains.setVisible(true); }
            else{
                Main.getDatabase().setMaxLevel(levelNumber+1);
                plantName.setVisible(true);
                plantImage.setVisible(true);
                youWon.setVisible(true);
                nextLevelButton.setVisible(true);
                nextLevelButton.setDisable(false);
                if(levelNumber==1){
                    plantName.setText("Walnut");
                    plantImage.setImage(new Image(getClass().getResource("/assets/L2.png").toString()));
                }
                else if(levelNumber==2){
                    plantName.setText("Cherry Bomb");
                    plantImage.setImage(new Image(getClass().getResource("/assets/L3.png").toString()));
                }
                else if(levelNumber==3){
                    plantName.setText("Repeater");
                    plantImage.setImage(new Image(getClass().getResource("/assets/L4.png").toString()));
                }
                else if(levelNumber==4){
                    plantName.setText("Jalapeno");
                    plantImage.setImage(new Image(getClass().getResource("/assets/L5.png").toString()));
                }
            } } }

}
