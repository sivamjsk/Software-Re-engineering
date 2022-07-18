package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.Iterator;


/**
 * The Class Pea.
 */
public class Pea extends GameElements{
    
    /** The lane. */
    private int lane;
    
    /** The plant position. */
    private int plantPosition;
    
    /** The pea animation. */
    transient private Timeline peaAnimation;
    
    /** The pea ID. */
    private static int peaID=0;
    
    /** The flag. */
    private boolean flag;
    
    /** The thispea. */
    private int thispea;
    
    /**
     * Instantiates a new pea.
     *
     * @param x the x
     * @param y the y
     * @param plantPosition the plant position
     * @param lane the lane
     */
    public Pea(int x, int y, int plantPosition, int lane) {
        super(x, y, "/assets/pea.png",20,20);
//        super.makeImage();
        this.path = getClass().getResource("/assets/pea.png").toString();
        this.plantPosition = plantPosition;
        this.lane = lane;
        thispea = peaID++;
        this.flag = false;
    }

    /**
     * Move pea.
     */
    public void movePea(){
        if(x<=1050)
        {
            setX(getX()+1);
        }
        if(this.plantPosition>getX())
        {
            img.setVisible(false);
        }
        else
        {
            img.setVisible(true);
        }
        checkZombieCollision();
    }

    /**
     * Shoot pea.
     */
    public void shootPea(){
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e -> movePea()));
        animation.setCycleCount(1050);
        animation.play();
        this.peaAnimation = animation;
        GamePlayController.animationTimelines.add(animation);
    }

    /**
     * Check zombie collision.
     */
    public void checkZombieCollision()
    {
        synchronized (GamePlayController.allZombies) {
            Iterator<Zombie> i = GamePlayController.allZombies.iterator();
            while (i.hasNext()) {
                Zombie z = i.next();
                if(z.getLane() == lane && !flag && Math.abs(z.getX()-getX())<=3 )
                {
                    this.flag = true;
                    z.setHp(z.getHp()-1);
                    img.setVisible(false);
                    img.setDisable(true);
                    peaAnimation.stop();
                    Media splat = new Media(getClass().getResource("/assets/sounds/splat3.wav").toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(splat);
                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.play();
                }
            }
        }
    }

}
