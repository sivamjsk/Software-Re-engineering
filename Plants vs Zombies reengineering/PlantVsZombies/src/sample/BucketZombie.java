package sample;

import javafx.scene.layout.Pane;


/**
 * The Class BucketZombie.
 */
public class BucketZombie extends Zombie {
    
    /**
     * Instantiates a new bucket zombie.
     *
     * @param x the x
     * @param y the y
     * @param lane the lane
     */
    public BucketZombie(int x, int y, int lane) {
        super(25, 3, "/assets/bucketheadzombie.gif", x, y, 68, 118, lane);
        this.path = getClass().getResource("/assets/bucketheadzombie.gif").toString();
    }
}
