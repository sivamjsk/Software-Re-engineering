package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


/**
 * The Class Sun.
 */
public class Sun extends GameElements{
    

    
    /** The timeouttime. */
    private final int timeouttime;

    /**
     * Instantiates a new sun.
     *
     * @param x the x
     * @param y the y
     * @param fallingSun the falling sun
     */
    public Sun(int x, int y, boolean fallingSun)
    {
        super(x, y, "/assets/sun.png", 50, 50);

        if(fallingSun) timeouttime=14000;
        else timeouttime=5000;
        disappearAfterTime();
    }
    
    /**
     * Disappear after time.
     */
    public void disappearAfterTime(){
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(timeouttime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            img.setVisible(false);
            img.setDisable(true);
        });
        t.start();
    }

    /**
     * Make image.
     *
     * @param p the p
     */
    @Override
    public void makeImage(Pane p)
    {
        super.makeImage(p);
        this.img.setOnMouseClicked(e->{
            this.img.setVisible(false);
            this.img.setDisable(true);
            GamePlayController.updateSunCount(25);
        });

    }

    /**
     * Move sun.
     */
    public void moveSun()
    {
        if(getY()<=550)
        {
            setY(getY()+1);
        }
    }

    /**
     * Drop sun.
     */
    public void dropSun()
    {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(12),e -> moveSun()));
        animation.setCycleCount(550);
        animation.play();
        GamePlayController.animationTimelines.add(animation);
    }


}
