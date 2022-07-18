package sample;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * The Class Level.
 */
public class Level {
    
    /** The level number. */
    private int levelNumber;
    
    /** The num normal zombie. */
    private int numNormalZombie;
    
    /** The num cone zombie. */
    private int numConeZombie;
    
    /** The num bucket zombie. */
    private int numBucketZombie;
    
    /** The total zombies. */
    private int totalZombies;
    
    /** The available plants. */
    private ArrayList<Plant> availablePlants;
    
    /** The available zombies. */
    private ArrayList<Integer> availableZombies;
    
    /** The zombie list 1. */
    private ArrayList<Integer> zombieList1;
    
    /** The zombie list 2. */
    private ArrayList<Integer> zombieList2;
    
    /**
     * Instantiates a new level.
     *
     * @param n the n
     */
    public Level(int n){
        this.levelNumber=n;
        this.availablePlants = new ArrayList<Plant>();
        this.availableZombies = new ArrayList<Integer>();
        this.zombieList1 = new ArrayList<Integer>();
        this.zombieList2 = new ArrayList<Integer>();
        if (n ==1){
            this.totalZombies = 10;
            this.numNormalZombie = 10;
            this.numConeZombie = 0;
            this.numBucketZombie = 0;
        }
        if (n == 2) {
            this.totalZombies = 15;
            this.numNormalZombie =10;
            this.numConeZombie =5;
            this.numBucketZombie =0;
        }
        if (n ==3){
            this.totalZombies = 25;
            this.numNormalZombie = 10;
            this.numConeZombie = 8;
            this.numBucketZombie = 4;
        }
        
        for(int i = 0; i<this.numNormalZombie; i++){
            this.availableZombies.add(0);}
        for(int j = 0; j<this.numConeZombie; j++) {
            this.availableZombies.add(1);}
        for(int k = 0; k<this.numBucketZombie; k++){
            this.availableZombies.add(2);}
        Collections.shuffle(availableZombies);
        for(int m = 0; m<availableZombies.size(); m++) {
            if(m%2==0)
            {
                this.zombieList1.add(availableZombies.get(m));
            }
            else {
                this.zombieList2.add(availableZombies.get(m)); }
        }
    }

    /**
     * Gets the zombie list 1.
     *
     * @return the zombie list 1
     */
    public ArrayList<Integer> getZombieList1()
    {
        return(this.zombieList1);
    }

    /**
     * Gets the zombie list 2.
     *
     * @return the zombie list 2
     */
    public ArrayList<Integer> getZombieList2()
    {
        return(this.zombieList2);
    }

    /**
     * Spawn normal zombie.
     *
     * @param pane the pane
     * @param lane the lane
     * @param laneNumber the lane number
     */
    public static void spawnNormalZombie(Pane pane, int lane, int laneNumber)
    {
        NormalZombie z = new NormalZombie(1024, lane, laneNumber);
        z.makeImage(pane);
        GamePlayController.allZombies.add(z);
        z.moveZombie();
    }

    /**
     * Spawn cone zombie.
     *
     * @param pane the pane
     * @param lane the lane
     * @param laneNumber the lane number
     */
    public static void spawnConeZombie(Pane pane, int lane, int laneNumber)
    {
        ConeZombie z = new ConeZombie(1024, lane, laneNumber);
        z.makeImage(pane);
        GamePlayController.allZombies.add(z);
        z.moveZombie();
    }

    /**
     * Spawn bucket zombie.
     *
     * @param pane the pane
     * @param lane the lane
     * @param laneNumber the lane number
     */
    public static void spawnBucketZombie(Pane pane, int lane, int laneNumber)
    {
        BucketZombie z = new BucketZombie(1024, lane, laneNumber);
        z.makeImage(pane);
        GamePlayController.allZombies.add(z);
        z.moveZombie();
    }

    /**
     * Gets the total zombies.
     *
     * @return the total zombies
     */
    public int getTotalZombies()
    {
        return(this.totalZombies);
    }

}
