package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Iterator;


/**
 * The Class Shooter.
 */
public class Shooter extends Plant {

    /** The shooter timeline. */
    transient protected Timeline shooterTimeline;
    
    /** The lane. */
    protected int lane;

    /**
     * Instantiates a new shooter.
     *
     * @param x the x
     * @param y the y
     * @param path the path
     * @param hp the hp
     * @param width the width
     * @param height the height
     * @param row the row
     * @param col the col
     */
    public Shooter(int x, int y, String path, int hp, int width, int height,int row,int col){
        super(x,y,path,hp,width,height,row,col);
        this.lane = col;
    }

    /**
     * Attack.
     *
     * @param pane the pane
     */
    @Override
    public void attack(Pane pane){
        Timeline peaShooter = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                synchronized (GamePlayController.allZombies) {
                    Iterator<Zombie> i = GamePlayController.allZombies.iterator();
                    while (i.hasNext()) {
                        Zombie z = i.next();
                        if (z.getLane() == getShooterLane() && getX() <= z.getX()) {
                            int peaStartX = getX() + 50;
                            int peaStartY = getY() + 25;
                            Pea p = new Pea(peaStartX, peaStartY, getX() + 50, row);
                            p.makeImage(pane);
                            p.shootPea();
                            checkHp();
                        }
                    }
                }
            }
        }));
        peaShooter.setCycleCount(Timeline.INDEFINITE);
        peaShooter.play();
        this.shooterTimeline = peaShooter;
        GamePlayController.animationTimelines.add(peaShooter);
    }
    
    /**
     * Gets the shooter timeline.
     *
     * @return the shooter timeline
     */
    public Timeline getShooterTimeline()
    {
        return(this.shooterTimeline);
    }

    /**
     * Gets the shooter lane.
     *
     * @return the shooter lane
     */
    public int getShooterLane()
    {
        return(this.lane);
    }

    /**
     * Check hp.
     */
    public void checkHp()
    {
        if(getHp()<=0)
        {
            endAnimation(this.shooterTimeline);
        }
    }
}
