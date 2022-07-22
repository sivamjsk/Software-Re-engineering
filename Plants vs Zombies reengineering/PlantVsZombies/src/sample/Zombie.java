package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.Serializable;
import java.util.Iterator;


/**
 * The Class Zombie.
 */
public abstract class Zombie extends GameElements {

    /** The hp. */
    protected int hp;
    
    /** The x. */
    protected int x;
    
    /** The y. */
    protected int y;
    
    /** The attack power. */
    protected int attackPower;
    
    /** The lane. */
    protected int lane;
    
    /** The dx. */
    protected int dx;
    
    /** The zombie animation. */
    transient protected Timeline zombieAnimation;
    
    /** The reached plant. */
    protected boolean reachedPlant = false;
    
    /** The is eating. */
    protected boolean isEating = false;
    
    /** The chomping. */
    protected transient Timeline chomping;

    /**
     * Instantiates a new zombie.
     *
     * @param hp the hp
     * @param ap the ap
     * @param p the p
     * @param x the x
     * @param y the y
     * @param width the width
     * @param height the height
     * @param lane the lane
     */
    public Zombie(int hp, int ap, String p, int x, int y, int width, int height, int lane) {
        super(x, y, p, width, height);
        this.hp = hp;
        this.attackPower = ap;
        this.lane = lane;
        this.dx = -1;
        this.chomping = new Timeline();
    }

    /**
     * Gets the hp.
     *
     * @return the hp
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Gets the lane.
     *
     * @return the lane
     */
    public int getLane() {
        return (this.lane);
    }

    /**
     * Sets the hp.
     *
     * @param hp the new hp
     */
    public void setHp(int hp) {
        this.hp = hp;
        if(hp<=0){
            GamePlayController.numZombiesKilled+=1;
            this.img.setVisible(false);
            this.img.setDisable(true);
            this.zombieAnimation.stop();
            if(this.chomping!=null)
            {
                this.chomping.stop();
            }
            for(int i = 0; i<GamePlayController.allZombies.size(); i++)
            {
                if(this==GamePlayController.allZombies.get(i))
                {
                    GamePlayController.allZombies.remove(i);
                    Media yuck = new Media(getClass().getResource("/assets/sounds/yuck.wav").toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(yuck);
                    mediaPlayer.setAutoPlay(true);
                    mediaPlayer.play();
                    //System.out.println("removed");
                    break;
                }
            }
        }
        if (hp<=7) {
            img.setImage(new Image(getClass().getResource("/assets/normalzombie.gif").toString(), (double) 68,(double) 118,false,false));
            this.width=68;
            this.height=118;
        }
    }

    /**
     * Roast zombie.
     */
    public void roastZombie(){
        img.setImage(new Image(getClass().getResource("/assets/burntZombie.gif").toString(), (double) 68,(double) 118,false,false));
        this.dx=0;
        this.hp = 0;
        this.chomping.stop();
        GamePlayController.numZombiesKilled+=1;
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            	e.getMessage();
            }
            img.setVisible(false);
            img.setDisable(true);
        });
        t.start();

    }

    /**
     * Sets the lane.
     *
     * @param lane the new lane
     */
    public void setLane(int lane) {
        this.y = lane;
    }

    /**
     * Check reached house.
     */
    public void checkReachedHouse() {
        if (img.getX() <= 220) {
            GamePlayController.wonGame = -1;
            Media brainz = new Media(getClass().getResource("/assets/sounds/brainz.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(brainz);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        }
    }

    /**
     * Move zombie.
     */
    public void moveZombie() {
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(70), e -> zombieWalk()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        this.zombieAnimation = animation;
        GamePlayController.animationTimelines.add(animation);
    }

    /**
     * Zombie walk.
     */
    public void zombieWalk()
    {
        if(getX()>220 && this.hp>0)
        {
            setX(getX()+this.dx);
            try{
                eatPlant();
            }
            catch(java.util.ConcurrentModificationException e)
            {
                //System.out.println("killed plant");
            }
            checkReachedHouse();
        }
    }

    /**
     * Gets the zombie animation.
     *
     * @return the zombie animation
     */
    public Timeline getZombieAnimation()
    {
        return this.zombieAnimation;
    }

    /**
     * Chomp plant.
     */
    public void chompPlant()
    {
        Media chomp = new Media(getClass().getResource("/assets/sounds/chomp.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(chomp);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(1));
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();
    }


    public void IfApproachingPlant(Plant p){

        if(reachedPlant==false)
        {
            reachedPlant = true;
            isEating = true;
        }
        if(isEating)
        {
            Timeline chomp = new Timeline(new KeyFrame(Duration.millis(1000), e -> chompPlant()));
            chomp.setCycleCount(1000);
            chomp.play();
            this.dx = 0;
            this.chomping = chomp;
            GamePlayController.animationTimelines.add(chomp);
            isEating = false;
        }

        this.dx = 0;
        p.setHp(p.getHp()-this.attackPower);
        if(p.getHp()<=0)
        {
            p.setHp(0);
            GamePlayController.allPlants.remove(p);
            p.img.setVisible(false);
            p.img.setDisable(true);
            this.dx = -1;
            this.reachedPlant = false;
            this.chomping.stop();
        }
    }

    public void IfChompingStop()
    {
        if (this.chomping != null)
        {
            this.chomping.stop();
        }
    }

    public void IteratePlant(int n) {
        int foundPlant=0;
        synchronized (GamePlayController.allPlants) {
            Iterator<Plant> i = GamePlayController.allPlants.iterator();
            while (i.hasNext()) {
                Plant p = i.next();
                if (p.row != getLane()) {
                    this.dx = -1;
                    continue;
                }
                if ((p.row == getLane()) && (Math.abs(p.getX() - img.getX()) <= n)) {
                    foundPlant = 1;
                    IfApproachingPlant(p);
                }
                else if ((p.row == getLane()) && (Math.abs(p.getX() - img.getX()) > n)) {
                    this.dx = -1;
                    this.reachedPlant = false;
                    IfChompingStop();
                }
            }
            if (foundPlant == 0) {
                this.dx = -1;
                IfChompingStop();
                this.reachedPlant = false;
            }
        }
    }


    /**
     * Eat plant.
     */
    public void eatPlant()
    {
        int n=25;
        IteratePlant(n);

    }
}