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
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

public class GamePlayController {

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
    @FXML
    private int levelNumber;
    @FXML
    private GridPane lawnGrid;
    /** The sun count. */
    private static int sunCount;
    
    /** The Constant LANE1. */
    static final int LANE1=50;
    
    /** The Constant LANE2. */
    static final int LANE2=150;
    
    /** The Constant LANE3. */
    static final int LANE3=250;
    
    /** The Constant LANE4. */
    static final int LANE4=350;
    
    /** The Constant LANE5. */
    static final int LANE5=450;
    
    /** The game status. */
    static boolean gameStatus;
    
    /** The sun timeline. */
    static Timeline sunTimeline;
    
    /** The sp Z 1. */
    static Timeline spZ1;
    
    /** The sp Z 2. */
    static Timeline spZ2;
    
    /** The sun count display. */
    private static Label sunCountDisplay;
    private static double timeElapsed;
    private static Level l;
    
    /** The all zombies. */
    static List allZombies;
    
    /** The all plants. */
    static List allPlants;
    
    /** The all mowers. */
    static List allMowers;
    
    /** The zombie list 1. */
    static ArrayList<Integer> zombieList1;
    
    /** The zombie list 2. */
    static ArrayList<Integer> zombieList2;
    
    /** The d. */
    private static DataTable d;
    
    /** The won game. */
    static int wonGame = 0;
    
    /** The spawned zombies. */
    private volatile int spawnedZombies = 0;
    
    /** The num zombies killed. */
    static double numZombiesKilled = 0;
    
    /** The animation timelines. */
    static ArrayList<Timeline> animationTimelines;
    
    /** The theme. */
    static String theme = "day";
    
    /** The shovel. */
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
        zombieList1 = d.getZombieList1();zombieList2 = d.getZombieList2();
        allPlants = d.getAllPlants();
        allZombies = d.getAllZombie();
        allMowers=d.getAllLawnMowers();
        sunCount=d.getSunCount();
        timeElapsed = d.getTimeElapsed();
        animationTimelines = new ArrayList<Timeline>();
        LevelMenuController.status = d.getStatus();
        startAnimations(rand);
        shovel=Shovel.getInstance();
        shovel.makeImage(gamePlayRoot);
        sunCountDisplay.setText(String.valueOf(sunCount));
        this.d=d;
        SidebarElement.getSideBarElements(levelNumber, gamePlayRoot);

        gameProgress();
        if(LevelMenuController.status){
            fallingSuns(rand);
            zombieSpawner1(rand, 15);
            zombieSpawner2(rand, 30);
        }
        else
        {
            String lawnPath = getClass().getResource("/assets/lawn_night.png").toString();
            Image lawn = new Image(lawnPath, 1024, 600, false, false);
            lawnImage.setImage(lawn);
            zombieSpawner1(rand, 25);
            zombieSpawner2(rand, 40);}}

    public void startAnimations(Random rand)
    {
        synchronized (allPlants) {
            Iterator<Plant> i = allPlants.iterator();
            while (i.hasNext()) {
                Plant p = i.next();
                p.makeImage(lawnGrid);
            }
        }
        synchronized (allMowers) {
            Iterator<LawnMower> i = allMowers.iterator();
            while (i.hasNext()) {
                LawnMower l = i.next();
                l.makeImage(gamePlayRoot);
                l.checkZombie();
            }
        }
        synchronized (allZombies)
        {
            Iterator<Zombie> i = allZombies.iterator();
            while(i.hasNext())
            {
                Zombie z = i.next();
                z.makeImage(gamePlayRoot);
                z.moveZombie();
            }
        }
        numZombiesKilled = l.getTotalZombies()*timeElapsed;
        progressBar.setProgress(timeElapsed);
    }

    public void gameProgress()
    {
        Timeline gameStatus = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    timeElapsed = ( numZombiesKilled / l.getTotalZombies());
                    progressBar.setProgress(timeElapsed);
                    if (wonGame == (-1)) {
                        numZombiesKilled = 0;
                        gameLost();
                    } else if (wonGame == 0 && allZombies.size() == 0 && l.getTotalZombies() == spawnedZombies) {
                        numZombiesKilled = 0;
                        gameWon();
                    }
                    else if(progressBar.getProgress()>=1)
                    {
                        spZ1.stop();
                        spZ2.stop();
                        gameWon();
                    }
                    endAnimations();
                }
                catch (IOException e) {
                	e.getMessage();
                }
            }
        }));
        gameStatus.setCycleCount(Timeline.INDEFINITE);
        gameStatus.play();
        animationTimelines.add(gameStatus);
    }


    public synchronized void updateSpawnedZombies()
    {
        this.spawnedZombies+=1;
    }

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

    public static void removePlant(Plant p){
        p.img.setVisible(false);
        allPlants.remove(p);
    }
    public static void removeZombie(Zombie z){
        z.img.setVisible(false);
        allZombies.remove(z);
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
                s.makeImage(gamePlayRoot);
                s.dropSun();
            }
        }));
        sunDropper.setCycleCount(Timeline.INDEFINITE);
        sunDropper.play();
        sunTimeline = sunDropper;
        animationTimelines.add(sunDropper);
    }
    public void ZombieSpawner(Random rand, ArrayList<Integer> zombieList){
        int lane;
        int laneNumber = rand.nextInt(5);
        if(laneNumber==0) { 
        	lane = LANE1;}
        else if(laneNumber==1) { 
        	lane = LANE2; }
        else if(laneNumber==2) {
        	lane = LANE3;}
        else if(laneNumber==3) { 
        	lane = LANE4;}
        else { 
        	lane = LANE5;}
        try {
            if(zombieList.get(0)==0) {
                Level.spawnNormalZombie(GamePlayRoot, lane, laneNumber);
                zombieList.remove(0);
                updateSpawnedZombies(); }
            else if(zombieList.get(0)==1) {
                Level.spawnConeZombie(GamePlayRoot, lane, laneNumber);
                zombieList.remove(0);
                updateSpawnedZombies();  }
            else if(zombieList.get(0)==2)  {
                Level.spawnBucketZombie(GamePlayRoot, lane, laneNumber);
                zombieList.remove(0);
                updateSpawnedZombies();  } }
        catch(IndexOutOfBoundsException e) {
            if(zombieList==zombieList1){ endZombieSpawner1(); }
            else { endZombieSpawner2();}
        }
 }
    public void zombieSpawner1(Random rand, double t){
        Timeline spawnZombie1 = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            ZombieSpawner(rand, zombieList1);
        }));

        spawnZombie1.setCycleCount(Timeline.INDEFINITE);
        spawnZombie1.play();
        spZ1 = spawnZombie1;
        animationTimelines.add(spawnZombie1);
    }

    public void zombieSpawner2(Random rand, double t){
        Timeline spawnZombie2 = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            ZombieSpawner(rand, zombieList2);
        }));

        spawnZombie2.setCycleCount(Timeline.INDEFINITE);
        spawnZombie2.play();
        spZ2 = spawnZombie2;
        animationTimelines.add(spawnZombie2);
    }

    public void endZombieSpawner1()
    {
        spZ1.stop();
    }

    public void endZombieSpawner2()
    {
        spZ2.stop();
    }

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
                p.makeImage(lawnGrid);
                break;
            case 2:
                p=new PeaShooter(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                break;
            case 3:
                p=new Wallnut(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                break;
            case 4:
                p=new CherryBomb(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                break;
            case 5:
                p=new Repeater(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);

                break;
            case 6:
                p=new Jalapeno(x, y,row,col);
                allPlants.add(p);
                p.makeImage(lawnGrid);
                break;
            default:
        }

    }

    public static void endAnimations()
    {
        for(int i = 0; i<animationTimelines.size(); i++)
        {
            animationTimelines.get(i).stop();
        }
    }
    public void gameLost() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndGame.fxml"));
        AnchorPane pane=fxmlLoader.load();
        EndGameController controller = fxmlLoader.<EndGameController>getController();
        controller.initData(levelNumber, false,d);
        gamePlayRoot.getChildren().setAll(pane);

    }
    public void gameWon() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EndGame.fxml"));
        AnchorPane pane=fxmlLoader.load();
        EndGameController controller = fxmlLoader.<EndGameController>getController();
        controller.initData(levelNumber, true,d);
        gamePlayRoot.getChildren().setAll(pane);

    }
}
