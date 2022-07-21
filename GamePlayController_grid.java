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
import java.util.*;

public class GamePlayController {

    private final GamePlayController_progress gamePlayController_progress = new GamePlayController_progress(this);
    private final GamePlayController_zombie gamePlayController_zombie = new GamePlayController_zombie(this);
    private final GamePlayController_animation gamePlayController_animation = new GamePlayController_animation(this);
    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView lawnImage;
    @FXML
    private ImageView peaShooterBuy;
    @FXML
    private ImageView repeaterBuy;
    @FXML
    private ImageView cherryBombBuy;
    @FXML
    private ImageView jalapenoBuy;
    @FXML
    private ImageView wallnutBuy;
    @FXML
    private ImageView sunBuy;
    @FXML
    private Label sunCountLabel;
    @FXML
    private ImageView GameMenuLoaderButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private int levelNumber;
    @FXML
    private GridPane lawn_grid;
    //public static ArrayList<Plant> allPlants;
    //public static ArrayList<LawnMower> allMowers;
    private static int sunCount;
    public static final int LANE1=50;
    public static final int LANE2=150;
    public static final int LANE3=250;
    public static final int LANE4=350;
    public static final int LANE5=450;
    public static boolean gameStatus=false;
    public static Timeline sunTimeline=null;
    public static Timeline spZ1=null;
    public static Timeline spZ2=null;
    private static Label sunCountDisplay;
    public static double timeElapsed;
    public static Level l;
    public static List allZombies=null;
    public static List allPlants=null;
    public static List allMowers=null;
    //public static ArrayList<Zombie> allZombies = new ArrayList<Zombie>();
    public static DataTable d;
    public static int wonGame = 0;
    public static double numZombiesKilled = 0;
    public static ArrayList<Timeline> animationTimelines=null;
    public static String theme = "day";
    private Shovel shovel;


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
        shovel.makeImage(GamePlayRoot);
        sunCountDisplay.setText(String.valueOf(sunCount));
        this.d=d;
        SidebarElement.getSideBarElements(levelNumber, GamePlayRoot);
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

    public void startAnimations(Random rand)
    {
        gamePlayController_animation.startAnimations(rand);
    }

    public void gameProgress()
    {
        gamePlayController_progress.gameProgress();
    }


    @FXML
    void loadGameMenu(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
        Parent gameMenu = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(gameMenu));
        GameMenuController controller = fxmlLoader.<GameMenuController>getController();
        controller.initData(GamePlayRoot, levelNumber,d,sunCount,allPlants, allZombies, allMowers, timeElapsed, l.getZombieList1(), l.getZombieList2());
        stage.show();
    }

    public static void removePlant(Plant p){
        p.img.setVisible(false);
        allPlants.remove(p);
    }

    public static void removeMower(LawnMower l){
        l.img.setVisible(false);
        allMowers.remove(l);
    }

    public static void updateSunCount(int val) {
        sunCount+=val;
        getSunCountLabel().setText(Integer.toString(sunCount));
    }

    public static Label getSunCountLabel() {
        return(sunCountDisplay);
    }

    public void fallingSuns(Random rand) {
        Timeline sunDropper = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int sunPosition = rand.nextInt(850);
                sunPosition += 100;
                Sun s = new Sun(sunPosition, 0, true);
                s.makeImage(GamePlayRoot);
                s.dropSun();
            }
        }));
        sunDropper.setCycleCount(Timeline.INDEFINITE);
        sunDropper.play();
        sunTimeline = sunDropper;
        animationTimelines.add(sunDropper);
    }

    public void zombieSpawner1(Random rand, double t){

        gamePlayController_zombie.zombieSpawner1(rand, t);
    }

    public void zombieSpawner2(Random rand, double t){

        gamePlayController_zombie.zombieSpawner2(rand, t);
    }

    public void endZombieSpawner1()
    {
        gamePlayController_zombie.endZombieSpawner1();
    }

    public void endZombieSpawner2()
    {
        gamePlayController_zombie.endZombieSpawner2();
    }

    @FXML
    void getGridPosition(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Integer colIndex = lawn_grid.getColumnIndex(source);
        Integer rowIndex = lawn_grid.getRowIndex(source);
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
                    SidebarElement.getElement(SidebarElement.getCardSelected()).setDisabledOn(GamePlayRoot);
                }
            }
            SidebarElement.setCardSelectedToNull();
        }

    }

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
                p.makeImage(lawn_grid);
                p.attack(GamePlayRoot);
                break;
            case 2:
                p=new PeaShooter(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawn_grid);
                p.attack(GamePlayRoot);
                break;
            case 3:
                p=new Wallnut(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawn_grid);
                p.attack(GamePlayRoot);
                break;
            case 4:
                p=new CherryBomb(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawn_grid);
                p.attack(GamePlayRoot);
                break;
            case 5:
                p=new Repeater(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawn_grid);
                p.attack(GamePlayRoot);
                break;
            case 6:
                p=new Jalapeno(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawn_grid);
                p.attack(GamePlayRoot);
                break;
            default:
                //System.out.println("No case match" + val);
        }

    }

    public void gameLost() throws IOException{

        gamePlayController_progress.gameLost();
    }
    public void gameWon() throws IOException{

        gamePlayController_progress.gameWon();
    }
    
    // Getter
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
    public AnchorPane getGamePlayRoot() {
        return GamePlayRoot;
    }

    public int getSpawnedZombies() {
        return gamePlayController_zombie.getSpawnedZombies();
    }

    public GridPane getLawn_grid() {
        return lawn_grid;
    }



}
