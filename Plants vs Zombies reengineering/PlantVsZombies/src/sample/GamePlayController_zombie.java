package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class GamePlayController_zombie {
    private final GamePlayController gamePlayController;
    public static ArrayList<Integer> zombieList1 = null;
    public static ArrayList<Integer> zombieList2 = null;
    volatile int spawnedZombies = 0;

    public GamePlayController_zombie(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    public synchronized void updateSpawnedZombies() {
        this.spawnedZombies += 1;
    }

    /*
    public static void removeZombie(Zombie z) {
        z.img.setVisible(false);
        GamePlayController.allZombies.remove(z);
    }
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

    public void zombieSpawner1(Random rand, double t) {
        Timeline spawnZombie1 = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            ZombieSpawner(rand, zombieList1);
        }));

        spawnZombie1.setCycleCount(Timeline.INDEFINITE);
        spawnZombie1.play();
        GamePlayController.spZ1 = spawnZombie1;
        GamePlayController.animationTimelines.add(spawnZombie1);
    }

    public void zombieSpawner2(Random rand, double t) {
        Timeline spawnZombie2 = new Timeline(new KeyFrame(Duration.seconds(t), event -> {
            ZombieSpawner(rand, zombieList2);
        }));

        spawnZombie2.setCycleCount(Timeline.INDEFINITE);
        spawnZombie2.play();
        GamePlayController.spZ2 = spawnZombie2;
        GamePlayController.animationTimelines.add(spawnZombie2);
    }

    public void endZombieSpawner1() {
        GamePlayController.spZ1.stop();
    }

    public void endZombieSpawner2() {
        GamePlayController.spZ2.stop();
    }

    public int getSpawnedZombies() {
        return spawnedZombies;
    }
}