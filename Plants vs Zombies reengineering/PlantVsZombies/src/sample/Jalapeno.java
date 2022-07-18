package sample;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * The Class Jalapeno.
 */
public class Jalapeno extends Plant {
    
    /** The roasted zombies. */
    private ArrayList<Zombie> roastedZombies;
    
    /** The fire views. */
    transient private ImageView[] fireViews;

    /**
     * Instantiates a new jalapeno.
     *
     * @param x the x
     * @param y the y
     * @param row the row
     * @param col the col
     */
    public Jalapeno(int x, int y,int row,int col) {
        super(x, y, "/assets/jalapeno.gif", 4,100,100,row,col);
        this.path=getClass().getResource("/assets/jalapeno.gif").toString();
        //System.out.println("Placed plant");
        fireViews=new ImageView[9];;
    }
    
    /**
     * Make image.
     *
     * @param lawn the lawn
     */
    @Override
    public void makeImage(GridPane lawn){
        super.makeImage(lawn);
        for(int i=0;i<9;i++){
            fireViews[i]=new ImageView(new Image(getClass().getResource("/assets/jalapenoFire.gif").toString(),(double) 100, (double) 100, false,false));
            fireViews[i].setDisable(true);
            fireViews[i].setVisible(false);
            lawn.add(fireViews[i],i,this.row,1,1);
            this.roastedZombies = new ArrayList<Zombie>();
        }
    }

    /**
     * Attack.
     *
     * @param pane the pane
     */
    @Override
    public void attack(Pane pane) {
        Thread t = new Thread(() -> {
            Thread.sleep(1650);
            img.setVisible(false);
            img.setDisable(true);
            Media explode = new Media(getClass().getResource("/assets/sounds/jalapeno.wav").toString());
            MediaPlayer mediaPlayer = new MediaPlayer(explode);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
            for(int i=0;i<9;i++){
                fireViews[i].setVisible(true);
            }
            synchronized (GamePlayController.allZombies)
            {
                Iterator<Zombie> i = GamePlayController.allZombies.iterator();
                while(i.hasNext()) {
                    Zombie x = i.next();
                    if(row == x.getLane()){
                        x.roastZombie(); }
                }
            }
            for(int j = 0; j<GamePlayController.allPlants.size(); j++)
            {
                if(this==GamePlayController.allPlants.get(j))
                {
                    GamePlayController.allPlants.remove(j);
                }
            }
            for(int k = 0; k<this.roastedZombies.size(); k++)
            {
                for(int m = 0; m<GamePlayController.allZombies.size(); m++)
                {
                    if(roastedZombies.get(k)==GamePlayController.allZombies.get(m))
                    {
                        GamePlayController.allZombies.remove(m);
                    }
                }
            }
            removefire();
        });
        t.start();
    }

   /**
    * Removefire.
    */
   public void removefire(){
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.getMessage();
            }
            for(int i=0;i<9;i++) {
                fireViews[i].setVisible(false);
                fireViews[i].setDisable(true);
            }
        });
        t.start();
        this.setHp(0);
    }

}
