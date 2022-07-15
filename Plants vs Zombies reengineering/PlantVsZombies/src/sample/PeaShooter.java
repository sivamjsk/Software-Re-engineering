package sample;



/**
 * The Class PeaShooter.
 */
public class PeaShooter extends Shooter{

/**
 * Instantiates a new pea shooter.
 *
 * @param x the x
 * @param y the y
 * @param row the row
 * @param col the col
 */
    public PeaShooter(int x, int y,int row,int col) {
        super(x, y, "/assets/peashooter.gif", 100,60,62,row,col);
        this.path=getClass().getResource("/assets/peashooter.gif").toString();
    }

}
