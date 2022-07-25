package sample;

import java.util.Iterator;
import java.util.Random;


/**
 * The Class GamePlayController_animation.
 */
public class GamePlayController_animation {
    
    /** The game play controller. */
    private final GamePlayController gamePlayController;

    /**
     * Instantiates a new game play controller animation.
     *
     * @param gamePlayController the game play controller
     */
    public GamePlayController_animation(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    /**
     * Start animations.
     *
     * @param rand the rand
     */
    public void startAnimations(Random rand) {
        synchronized (GamePlayController.allPlants) {
            Iterator<Plant> i = GamePlayController.allPlants.iterator();
            while (i.hasNext()) {
                Plant p = i.next();
                p.makeImage(gamePlayController.getLawn_grid());
                p.attack(gamePlayController.getGamePlayRoot());
            }
        }
        synchronized (GamePlayController.allMowers) {
            Iterator<LawnMower> i = GamePlayController.allMowers.iterator();
            while (i.hasNext()) {
                LawnMower l = i.next();
                l.makeImage(gamePlayController.getGamePlayRoot());
                l.checkZombie();
            }
        }
        synchronized (GamePlayController.allZombies) {
            Iterator<Zombie> i = GamePlayController.allZombies.iterator();
            while (i.hasNext()) {
                Zombie z = i.next();
                z.makeImage(gamePlayController.getGamePlayRoot());
                z.moveZombie();
            }
        }
        GamePlayController.numZombiesKilled = GamePlayController.l.getTotalZombies() * GamePlayController.timeElapsed;
        gamePlayController.getProgressBar().setProgress(GamePlayController.timeElapsed);
    }

    /**
     * End animations.
     */
    public static void endAnimations() {
        for (int i = 0; i < GamePlayController.animationTimelines.size(); i++) {
            GamePlayController.animationTimelines.get(i).stop();
        }
    }
}