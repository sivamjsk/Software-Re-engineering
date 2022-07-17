package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


/**
 * The Class Sunflower.
 */
public class Sunflower extends Plant{
    
    /** The sun producer. */
    transient private Timeline sunProducer;
    
    /**
     * Instantiates a new sunflower.
     *
     * @param x the x
     * @param y the y
     * @param row the row
     * @param col the col
     */
    public Sunflower(int x, int y,int row,int col){
        super(x,y,"/assets/sunflower.gif",100,73,74,row,col);
        this.path = getClass().getResource("/assets/sunflower.gif").toString();
    }

    /**
     * Attack.
     *
     * @param pane the pane
     */
    @Override
    public void attack(Pane pane){
        produceSun(pane);
    }

    /**
     * Produce sun.
     *
     * @param pane the pane
     */
    public void produceSun(Pane pane){
        Timeline stopGlow = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Glow glow=new Glow();
                img.setEffect(glow);
                glow.setLevel(0.0);
            }
        }));

        Timeline sunProducer = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkHp();
                if(getHp()>0)
                {
                    Sun s = new Sun(getX()+20, getY()+40, false);
                    s.makeImage(pane);
                }
                stopGlow.play();
            }
        }));
        Timeline startGlow = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Glow glow=new Glow();
                img.setEffect(glow);
                glow.setLevel(0.5);
                sunProducer.play();
            }
        }));
        startGlow.setCycleCount(Timeline.INDEFINITE);
        startGlow.play();
        this.sunProducer = sunProducer;
        GamePlayController.animationTimelines.add(sunProducer);
        GamePlayController.animationTimelines.add(startGlow);
        GamePlayController.animationTimelines.add(stopGlow);
    }
    
    /**
     * Gets the timeline.
     *
     * @return the timeline
     */
    public Timeline getTimeline()
    {
        return this.sunProducer;
    }

    /**
     * Check hp.
     */
    public void checkHp()
    {
        if(getHp()<=0)
        {
            endAnimation(getTimeline());
        }
    }
}
