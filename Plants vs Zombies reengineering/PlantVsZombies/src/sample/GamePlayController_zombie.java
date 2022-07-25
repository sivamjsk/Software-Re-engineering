package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


/**
 * The Class GamePlayController_zombie.
 */
public class GamePlayController_zombie {
    
    /** The game play controller. */
    private final GamePlayController gamePlayController;
    
    /** The zombie list 1. */
    static ArrayList<Integer> zombieList1 = null;
    
    /** The zombie list 2. */
    static ArrayList<Integer> zombieList2 = null;
    
    /** The spawned zombies. */
    volatile int spawnedZombies = 0;

    /**
     * Instantiates a new game play controller zombie.
     *
     * @param gamePlayController the game play controller
     */
    public GamePlayController_zombie(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    /**
     * Update spawned zombies.
     */
    public synchronized void updateSpawnedZombies() {
        this.spawnedZombies += 1;
    }

    /**
     * Zombie spawner.
     *
     * @param rand the rand
     * @param zombieList the zombie list
     */
    public void ZombieSpawner(Random rand, ArrayList<Integer> zombieList) {
        int lane;
        int laneNumber = rand.nextInt(5);
        if (laneNumber == 0) {
            lane = GamePlayController.LANE1;
        } else if (laneNumber == 1) {
            lane = GamePlayController.LANE2;
        } else if (laneNumber == 2) {
            lane = GamePlayController.LANE3;
        } else if (laneNumber == 3) {
            lane = GamePlayController.LANE4;
        } else {
            lane = GamePlayController.LANE5;
        }
        try {
            if (zombieList.get(0) == 0) {
                Level.spawnNormalZombie(gamePlayController.getGamePlayRoot(), lane, laneNumber);
                zombieList.remove(0);
                updateSpawnedZombies();
            } else if (zombieList.get(0) == 1) {
                Level.spawnConeZombie(gamePlayController.getGamePlayRoot(), lane, laneNumber);
                zombieList.remove(0);
                updateSpawnedZombies();
            } else if (zombieList.get(0) == 2) {
                Level.spawnBucketZombie(gamePlayController.getGamePlayRoot(), lane, laneNumber);
                zombieList.remove(0);
                updateSpawnedZombies();
            }
        } catch (IndexOutOfBoundsException e) {
            if (zombieList == zombieList1) {
                endZombieSpawner1();
            } else {
                endZombieSpawner2();
            }
        }
    }

    /**
     * Zombie spawner 1.
     *
     * @param rand the rand
     * @param t the t
     */
    public void zombieSpawner1(Random rand, double t) {
        Timeline spawnZombie1 = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            ZombieSpawner(rand, zombieList1);
        }));

        spawnZombie1.setCycleCount(Timeline.INDEFINITE);
        spawnZombie1.play();
        GamePlayController.spZ1 = spawnZombie1;
        GamePlayController.animationTimelines.add(spawnZombie1);
    }

    /**
     * Zombie spawner 2.
     *
     * @param rand the rand
     * @param t the t
     */
    public void zombieSpawner2(Random rand, double t) {
        Timeline spawnZombie2 = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            ZombieSpawner(rand, zombieList2);
        }));

        spawnZombie2.setCycleCount(Timeline.INDEFINITE);
        spawnZombie2.play();
        GamePlayController.spZ2 = spawnZombie2;
        GamePlayController.animationTimelines.add(spawnZombie2);
    }

    /**
     * End zombie spawner 1.
     */
    public void endZombieSpawner1() {
        GamePlayController.spZ1.stop();
    }

    /**
     * End zombie spawner 2.
     */
    public void endZombieSpawner2() {
        GamePlayController.spZ2.stop();
    }

    /**
     * Gets the spawned zombies.
     *
     * @return the spawned zombies
     */
    public int getSpawnedZombies() {
        return spawnedZombies;
    }
}