package sample;

import javafx.scene.layout.Pane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * The Class DataTable.
 */
public class DataTable implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID=42L;
    
    /** The id. */
    private static int id=0;
    
    /** The game id. */
    private int gameId;
    
    /** The sun count. */
    private int sunCount;
    
    /** The all plants. */
    private List<Plant> allPlants;
    
    /** The all zombie. */
    private List<Zombie> allZombie;
    
    /** The zombie list 1. */
    private ArrayList<Integer> zombieList1;
    
    /** The zombie list 2. */
    private ArrayList<Integer> zombieList2;
    
    /** The time elapsed. */
    private double timeElapsed;
    
    /** The status. */
    private boolean status = LevelMenuController.status;
    
    /** The all lawn mowers. */
    private List<LawnMower> allLawnMowers;
    
    /** The level number. */
    private int levelNumber;
    
    /** The saving time stamp. */
    private String savingTimeStamp;
    
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

/**
 * Instantiates a new data table.
 *
 * @param levelNumber the level number
 */
//    private String filename;
    public DataTable(int levelNumber){
        id++;
        this.gameId=id;
        this.sunCount=50;
        this.zombieList1 = new ArrayList<Integer>();
        this.zombieList2 = new ArrayList<Integer>();
        this.allZombie = Collections.synchronizedList(new ArrayList<Zombie>());
        this.allPlants = Collections.synchronizedList(new ArrayList<Plant>());
        this.allLawnMowers = Collections.synchronizedList(new ArrayList<LawnMower>());
        allLawnMowers.add(new LawnMower(249,LANE1+20,0));
        allLawnMowers.add(new LawnMower(249,LANE2+20,1));
        allLawnMowers.add(new LawnMower(249,LANE3+20,2));
        allLawnMowers.add(new LawnMower(243,LANE4+20,3));
        allLawnMowers.add(new LawnMower(236,LANE5+20,4));
        Level l = new Level(levelNumber);
        zombieList1 = l.getZombieList1();
        zombieList2 = l.getZombieList2();
        status = LevelMenuController.status;

    }

    /**
     * Update.
     *
     * @param levelNumber the level number
     * @param sunCount the sun count
     * @param allPlants the all plants
     * @param allZombie the all zombie
     * @param allLawnMowers the all lawn mowers
     * @param timeElapsed the time elapsed
     * @param zombieList1 the zombie list 1
     * @param zombieList2 the zombie list 2
     * @param status the status
     */
    public void update(int levelNumber, int sunCount, List<Plant> allPlants, List<Zombie> allZombie, List<LawnMower> allLawnMowers, double timeElapsed, ArrayList<Integer> zombieList1, ArrayList<Integer> zombieList2, boolean status) {
        this.sunCount = sunCount;
        this.allPlants = allPlants;
        this.allZombie = allZombie;
        this.allLawnMowers = allLawnMowers;
        this.levelNumber = levelNumber;
        this.timeElapsed = timeElapsed;
        this.zombieList1 = zombieList1;
        this.zombieList2 = zombieList2;
        this.status = status;
    }

    /**
     * Save game.
     */
    public void saveGame(){
        Main.getDatabase().getDatabaseFiles().add(this);
        try{
            Main.serialize();
        }
        catch (IOException e){
            //System.out.println("Cant close stream");
        }
    }

    /**
     * Gets the all lawn mowers.
     *
     * @return the all lawn mowers
     */
    public List<LawnMower> getAllLawnMowers() {
        return allLawnMowers;
    }

    /**
     * Gets the all plants.
     *
     * @return the all plants
     */
    public List<Plant> getAllPlants() {
        return allPlants;
    }

    /**
     * Gets the all zombie.
     *
     * @return the all zombie
     */
    public List<Zombie> getAllZombie() {
        return allZombie;
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
     * Gets the saving time stamp.
     *
     * @return the saving time stamp
     */
    public String getSavingTimeStamp() {
        return savingTimeStamp;
    }

    /**
     * Gets the sun count.
     *
     * @return the sun count
     */
    public int getSunCount() {
        return sunCount;
    }

    /**
     * Gets the time elapsed.
     *
     * @return the time elapsed
     */
    public double getTimeElapsed() {
        //System.out.println(this.timeElapsed);
        return this.timeElapsed;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString()
    {
        return ("Level: "+String.valueOf(levelNumber)+"                                                             Game"+String.valueOf(gameId));
    }
    
    /**
     * Gets the zombie list 1.
     *
     * @return the zombie list 1
     */
    public ArrayList<Integer> getZombieList1()
    {
        return this.zombieList1;
    }
    
    /**
     * Gets the zombie list 2.
     *
     * @return the zombie list 2
     */
    public ArrayList<Integer> getZombieList2()
    {
        return this.zombieList2;
    }
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    public boolean getStatus() {return this.status;}
    
}
