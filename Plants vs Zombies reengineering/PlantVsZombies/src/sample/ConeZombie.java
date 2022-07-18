package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Iterator;


/**
 * The Class ConeZombie.
 */
public class ConeZombie extends Zombie {
    
    /**
     * Instantiates a new cone zombie.
     *
     * @param x the x
     * @param y the y
     * @param lane the lane
     */
    public ConeZombie(int x, int y, int lane) {
        super(14, 1, "/assets/coneheadzombie.gif", x, y, 133, 122, lane);
        this.path = getClass().getResource("/assets/coneheadzombie.gif").toString();
    }
    
    /**
     * Eat plant.
     */
    @Override
    public void eatPlant()
    {
        int n=50;
        innereatPlant(n);
    }
}
