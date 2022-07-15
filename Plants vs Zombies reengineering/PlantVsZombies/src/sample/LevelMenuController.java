package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


/**
 * The Class LevelMenuController.
 */
public class LevelMenuController extends GlowImage {

    /** The night mode. */
    @FXML
    public ImageView nightMode;

    /** The day mode. */
    @FXML
    public ImageView dayMode;

    /** The status. */
    public static boolean status = true;

    /** The level root. */
    @FXML
    private AnchorPane levelRoot;

    /** The level 1 button. */
    @FXML
    private ImageView level1button;

    /** The level 2 button. */
    @FXML
    private ImageView level2button;

    /** The level 3 button. */
    @FXML
    private ImageView level3button;

    /** The level 4 button. */
    @FXML
    private ImageView level4button;

    /** The level 5 button. */
    @FXML
    private ImageView level5button;

    /** The lock 2. */
    @FXML
    private ImageView lock2;

    /** The lock 3. */
    @FXML
    private ImageView lock3;

    /** The lock 4. */
    @FXML
    private ImageView lock4;

    /** The lock 5. */
    @FXML
    private ImageView lock5;

    /** The backbutton. */
    @FXML
    private ImageView backbutton;
    
    /** The night theme. */
    @FXML
    private ImageView nightTheme;

    /**
     * Initialize.
     */
    public void initialize(){
//        nightTheme=new ImageView(new Image("file:src/sample/assets/menu_dark_mode.png"));
//        levelRoot.getChildren().add(nightTheme);
//        nightTheme.setDisable(true);
        if(status==true)
        {
            nightTheme.setVisible(false);
            dayMode.setVisible(true);
            dayMode.setDisable(false);
            nightMode.setVisible(false);
            nightMode.setDisable(true);
        }
        else
        {
            nightTheme.setVisible(true);
            nightMode.setVisible(true);
            nightMode.setDisable(false);
            dayMode.setVisible(false);
            dayMode.setDisable(true);
            GamePlayController.theme = "night";
        }
        int l=Main.getDatabase().getMaxLevel();
        level2button.setDisable(true);
        level3button.setDisable(true);
        level4button.setDisable(true);
        level5button.setDisable(true);
        if(l>=2){
            lock2.setVisible(false);
            lock2.setDisable(true);
            level2button.setDisable(false);
        }
        if(l>=3){
            lock3.setVisible(false);
            lock3.setDisable(true);
            level3button.setDisable(false);
        }
        if(l>=4){
            lock4.setVisible(false);
            lock4.setDisable(true);
            level4button.setDisable(false);
        }
        if(l>=5){
            lock5.setVisible(false);
            lock5.setDisable(true);
            level5button.setDisable(false);
        }

    }

    /**
     * Launch level 1.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void launchLevel1(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(1, new DataTable(1));
        levelRoot.getChildren().setAll(pane);

    }

    /**
     * Launch level 2.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void launchLevel2(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(2, new DataTable(2));
        levelRoot.getChildren().setAll(pane);

    }

    /**
     * Launch level 3.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void launchLevel3(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(3, new DataTable(3));
        levelRoot.getChildren().setAll(pane);

    }

    /**
     * Launch level 4.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void launchLevel4(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(4, new DataTable(4));
        levelRoot.getChildren().setAll(pane);

    }

    /**
     * Launch level 5.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void launchLevel5(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GamePlay.fxml"));
        AnchorPane pane=fxmlLoader.load();
        GamePlayController controller = fxmlLoader.<GamePlayController>getController();
        controller.initData(5, new DataTable(5));
        levelRoot.getChildren().setAll(pane);
    }

    /**
     * Load prev menu.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void loadPrevMenu(MouseEvent event) throws IOException{
        AnchorPane pane= FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        levelRoot.getChildren().setAll(pane);
    }

    /**
     * Change game theme.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void changeGameTheme(MouseEvent event) throws IOException{
        if(dayMode.isVisible()==false)
        {
            nightMode.setVisible(false);
            nightMode.setDisable(true);
            dayMode.setVisible(true);
            dayMode.setDisable(false);
            status = true;
            nightTheme.setVisible(false);
        }
        else if(nightMode.isVisible()==false)
        {
            dayMode.setVisible(false);
            dayMode.setDisable(true);
            nightMode.setVisible(true);
            nightMode.setDisable(false);
            status = false;
            nightTheme.setVisible(true);
        }
    }

    /**
     * Gets the day mode.
     *
     * @return the day mode
     */
    public static boolean getDayMode()
    {
        return(status);
    }

}