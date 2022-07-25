package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Iterator;

/**
 * The Class GamePlayController.
 */
public class GamePlayController {

    /** The game play controller progress. */
    private final GamePlayController_progress gamePlayController_progress = new GamePlayController_progress(this);
    
    /** The game play controller zombie. */
    private final GamePlayController_zombie gamePlayController_zombie = new GamePlayController_zombie(this);
    
    /** The game play controller animation. */
    private final GamePlayController_animation gamePlayController_animation = new GamePlayController_animation(this);
    
    /** The game play root. */
    @FXML
    private AnchorPane gamePlayRoot;
    
    /** The lawn image. */
    @FXML
    private ImageView lawnImage;

    /** The sun count label. */
    @FXML
    private Label sunCountLabel;

    /** The progress bar. */
    @FXML
    private ProgressBar progressBar;
    
    /** The level number. */
    @FXML
    private int levelNumber;
    
    /** The lawn grid. */
    @FXML
    private GridPane lawnGrid;
    
    /** The sun count. */
    private static int sunCount;
    
    /** The Constant LANE1. */
    public static final int LANE1=50;
    
    /** The Constant LANE2. */
    public static final int LANE2=150;
    
    /** The Constant LANE3. */
    public static final int LANE3=250;
    
    /** The Constant LANE4. */
    public static final int LANE4=350;
    
    /** The Constant LANE5. */
    public static final int LANE5=450;
    
    /** The game status. */
    static boolean gameStatus=false;
    
    /** The sun timeline. */
    static Timeline sunTimeline=null;
    
    /** The sp Z 1. */
    static Timeline spZ1=null;
    
    /** The sp Z 2. */
    static Timeline spZ2=null;
    
    /** The sun count display. */
    private static Label sunCountDisplay;
    
    /** The time elapsed. */
    static double timeElapsed;
    
    /** The l. */
    static Level l;
    
    /** The all zombies. */
    static List allZombies=null;
    
    /** The all plants. */
    static List allPlants=null;
    
    /** The all mowers. */
    static List allMowers=null;
    
    /** The d. */
    static DataTable d;
    
    /** The won game. */
    static int wonGame = 0;
    
    /** The num zombies killed. */
    static double numZombiesKilled = 0;
    
    /** The animation timelines. */
    static ArrayList<Timeline> animationTimelines=null;
    
    /** The theme. */
    static String theme = "day";
    
    /** The shovel. */
    private Shovel shovel;


    /**
     * Initialize.
     *
     * @throws Exception the exception
     */
    public void initialize() throws Exception {
        l = null;
        Media wave = new Media(getClass().getResource("/assets/sounds/zombies_are_coming.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(wave);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(5));
        mediaPlayer.play();

        gameStatus = true;
        sunCountDisplay = sunCountLabel;
        allZombies = Collections.synchronizedList(new ArrayList<Zombie>());
        allPlants = Collections.synchronizedList(new ArrayList<Plant>());
        allMowers = Collections.synchronizedList(new ArrayList<LawnMower>());
    }

    /**
     * Inits the data.
     *
     * @param levelNumber the level number
     * @param d the d
     */
    @FXML
    public void initData(int levelNumber, DataTable d) {
        wonGame = 0;
        Random rand = new Random();
        this.levelNumber = levelNumber;
        Level l = new Level(levelNumber);
        this.l = l;
        GamePlayController_zombie.zombieList1 = d.getZombieList1();
        GamePlayController_zombie.zombieList2 = d.getZombieList2();
        allPlants = d.getAllPlants();
        allZombies = d.getAllZombie();
        allMowers=d.getAllLawnMowers();
        sunCount=d.getSunCount();
        timeElapsed = d.getTimeElapsed();
        animationTimelines = new ArrayList<Timeline>();
        LevelMenuController.status = d.getStatus();
        gamePlayController_animation.startAnimations(rand);
        shovel=Shovel.getInstance();
        shovel.makeImage(gamePlayRoot);
        sunCountDisplay.setText(String.valueOf(sunCount));
        this.d=d;
        SidebarElement.getSideBarElements(levelNumber, gamePlayRoot);
        gamePlayController_progress.gameProgress();
        if(LevelMenuController.status){
            fallingSuns(rand);
            gamePlayController_zombie.zombieSpawner1(rand, 15);
            gamePlayController_zombie.zombieSpawner2(rand, 30);}
        else{
            String lawn_path = getClass().getResource("/assets/lawn_night.png").toString();
            Image lawn = new Image(lawn_path, 1024, 600, false, false);
            lawnImage.setImage(lawn);
            gamePlayController_zombie.zombieSpawner1(rand, 25);
            gamePlayController_zombie.zombieSpawner2(rand, 40);}}

    /**
     * Start animations.
     *
     * @param rand the rand
     */
    public void startAnimations(Random rand)
    {
        gamePlayController_animation.startAnimations(rand);
    }

    /**
     * Game progress.
     */
    public void gameProgress()
    {
        gamePlayController_progress.gameProgress();
    }


    /**
     * Load game menu.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void loadGameMenu(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        Parent gameMenu = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(gameMenu));
        GameMenuController controller = fxmlLoader.<GameMenuController>getController();
        controller.initData(gamePlayRoot, levelNumber,d,sunCount,allPlants, allZombies, allMowers, timeElapsed, l.getZombieList1(), l.getZombieList2());
        stage.show();
    }

    /**
     * Update sun count.
     *
     * @param val the val
     */
    public static void updateSunCount(int val) {
        sunCount+=val;
        getSunCountLabel().setText(Integer.toString(sunCount));
    }

    /**
     * Gets the sun count label.
     *
     * @return the sun count label
     */
    public static Label getSunCountLabel() {
        return(sunCountDisplay);
    }

    /**
     * Falling suns.
     *
     * @param rand the rand
     */
    public void fallingSuns(Random rand) {
        Timeline sunDropper = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int sunPosition = rand.nextInt(850);
                sunPosition += 100;
                Sun s = new Sun(sunPosition, 0, true);
                s.makeImage(gamePlayRoot);
                s.dropSun();
            }
        }));
        sunDropper.setCycleCount(Timeline.INDEFINITE);
        sunDropper.play();
        sunTimeline = sunDropper;
        animationTimelines.add(sunDropper);
    }

    /**
     * Zombie spawner 1.
     *
     * @param rand the rand
     * @param t the t
     */
    public void zombieSpawner1(Random rand, double t){

        gamePlayController_zombie.zombieSpawner1(rand, t);
    }

    /**
     * Zombie spawner 2.
     *
     * @param rand the rand
     * @param t the t
     */
    public void zombieSpawner2(Random rand, double t){

        gamePlayController_zombie.zombieSpawner2(rand, t);
    }

    /**
     * End zombie spawner 1.
     */
    public void endZombieSpawner1()
    {
        gamePlayController_zombie.endZombieSpawner1();
    }

    /**
     * End zombie spawner 2.
     */
    public void endZombieSpawner2()
    {
        gamePlayController_zombie.endZombieSpawner2();
    }

    /**
     * Gets the grid position.
     *
     * @param event the event
     * @return the grid position
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void getGridPosition(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Integer colIndex = lawnGrid.getColumnIndex(source);
        Integer rowIndex = lawnGrid.getRowIndex(source);
        if (!shovel.isIsDisabled()) {
            shovel.disable();
            if (colIndex != null && rowIndex != null) {
                Media shove = new Media(getClass().getResource("/assets/sounds/plant.wav").toString());
                MediaPlayer mediaPlayer = new MediaPlayer(shove);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.play();
                synchronized (allPlants) {
                    Iterator<Plant> i = allPlants.iterator();
                    while (i.hasNext()) {
                        Plant p = i.next();
                        if (p.col == colIndex && p.row == rowIndex) {
                            p.img.setVisible(false);
                            p.img.setDisable(true);
                            allPlants.remove(p);
                            p.setHp(0);
                            ((Shooter)p).checkHp();
                            ((Sunflower)p).checkHp();
                            ((Wallnut)p).checkHp();
                            break;
                        }
                    }
                }
            }
        }
        if (SidebarElement.getCardSelected() != -1) {
            if (colIndex != null && rowIndex != null) {
                boolean flag = true;
                synchronized (allPlants) {
                    Iterator<Plant> i = allPlants.iterator();
                    while (i.hasNext()) {
                        Plant p = i.next();
                        if (p.col == colIndex && p.row == rowIndex) {
                            flag = false;
                        }
                    }
                }
                if (flag && (SidebarElement.getElement(SidebarElement.getCardSelected()).getCost() <= sunCount) ) {
                    placePlant(SidebarElement.getCardSelected(), (int) (source.getLayoutX() + source.getParent().getLayoutX()), (int) (source.getLayoutY() + source.getParent().getLayoutY()), colIndex, rowIndex);
                    updateSunCount((-1) * SidebarElement.getElement(SidebarElement.getCardSelected()).getCost());
                    SidebarElement.getElement(SidebarElement.getCardSelected()).setDisabledOn(gamePlayRoot);
                }
            }
            SidebarElement.setCardSelectedToNull();
        }

    }

    /**
     * Place plant.
     *
     * @param val the val
     * @param x the x
     * @param y the y
     * @param row the row
     * @param col the col
     */
    public void placePlant(int val, int x, int y,int row,int col) {
        Plant p;
        Media plant = new Media(getClass().getResource("/assets/sounds/plant.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(plant);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        switch (val) {
            case 1:
                p=new Sunflower(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                p.attack(gamePlayRoot);
                break;
            case 2:
                p=new PeaShooter(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                p.attack(gamePlayRoot);
                break;
            case 3:
                p=new Wallnut(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                p.attack(gamePlayRoot);
                break;
            case 4:
                p=new CherryBomb(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                p.attack(gamePlayRoot);
                break;
            case 5:
                p=new Repeater(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                p.attack(gamePlayRoot);
                break;
            case 6:
                p=new Jalapeno(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                p.attack(gamePlayRoot);
                break;
            default:
        }

    }

    /**
     * Game lost.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void gameLost() throws IOException{

        gamePlayController_progress.gameLost();
    }
    
    /**
     * Game won.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void gameWon() throws IOException{

        gamePlayController_progress.gameWon();
    }

    /**
     * Gets the progress bar.
     *
     * @return the progress bar
     */
    /* Getter*/
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * Gets the level number.
     *
     * @return the level number
     */
    public int getLevelNumber() {
        return levelNumber;
    }
    
    /**
     * Gets the game play root.
     *
     * @return the game play root
     */
    public AnchorPane getGamePlayRoot() {
        return gamePlayRoot;
    }

    /**
     * Gets the spawned zombies.
     *
     * @return the spawned zombies
     */
    public int getSpawnedZombies() {
        return gamePlayController_zombie.getSpawnedZombies();
    }

    /**
     * Gets the lawn grid.
     *
     * @return the lawn grid
     */
    public GridPane getLawn_grid() {
        return lawnGrid;
    }



}