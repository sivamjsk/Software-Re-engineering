package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;


/**
 * The Class LoadGameController.
 */
public class LoadGameController {

    /** The load game root. */
    @FXML
    private AnchorPane loadGameRoot;

    /** The game state list. */
    @FXML
    private ListView gameStateList;


    /** The delete progress. */
    @FXML
    private Button deleteProgress;
    
    /** The d. */
    ObservableList<DataTable> d= FXCollections.observableArrayList(Main.getDatabase().getDatabaseFiles());


/**
     * Initialize.
     */
    @FXML
    public void initialize()
    {
        deleteProgress.setStyle("-fx-background-color: #fcd432");
        gameStateList.setItems(d);
    }

    /**
     * Load prev menu.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void loadPrevMenu(MouseEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        loadGameRoot.getChildren().setAll(pane);
    }


    /**
     * Handle mouse click.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void handleMouseClick(MouseEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        DataTable d= (DataTable) gameStateList.getSelectionModel().getSelectedItem();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(d.getLevelNumber(),d);
        loadGameRoot.getChildren().setAll(pane);

    }

    /**
     * Delete all progress.
     *
     * @param event the event
     * @throws Exception the exception
     */
    @FXML
    void deleteAllProgress(MouseEvent event) throws Exception{
        File file = new File("database.txt");
        if(file.delete()){
            gameStateList.getItems().clear();
        }

    }
}