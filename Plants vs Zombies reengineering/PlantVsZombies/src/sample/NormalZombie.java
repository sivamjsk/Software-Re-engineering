package sample;

import javafx.scene.layout.Pane;


/**
 * The Class NormalZombie.
 */
public class NormalZombie extends Zombie{
    
    /**
     * Instantiates a new normal zombie.
     *
     * @param x the x
     * @param y the y
     * @param lane the lane
     */
    public NormalZombie(int x, int y, int lane) {
        super(7, 1, "/assets/normalzombie.gif", x, y, 68, 118, lane);
        this.path=getClass().getResource("/assets/normalzombie.gif").toString();
    }
}
